package com.notice_center.cymo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class UploadFile extends AppCompatActivity{
    Button selectFile,upload,createNotice;
    TextView title,Sem;
    EditText notice;
    Spinner spinner;
    Uri filepath;

    StorageReference storage;
    DatabaseReference databaseReference;
    List<String> semester;
    int maxId=0;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        createNotice=findViewById(R.id.pdfCreate);
        selectFile=findViewById(R.id.browse);
        upload=findViewById(R.id.uploadFile);
        notice=findViewById(R.id.notice);
        spinner=findViewById(R.id.spinSem);
        Sem=findViewById(R.id.Sem);
        title=findViewById(R.id.fileTitle);


        semester=new ArrayList<>();
        semester.add(0,"Select Semester");
        semester.add("First");
        semester.add("Second");
        semester.add("Third");
        semester.add("Fourth");
        semester.add("Fifth");
        semester.add("Sixth");

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,semester);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select Semester")){}
                else{
                    Sem.setText(parent.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        storage = FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Bsc CS");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                maxId=(int) (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        createNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadFile.this,CreateNotice.class);
                startActivity(intent);
            }
        });


        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(UploadFile.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                   selectPDF();
                }
                else
                    ActivityCompat.requestPermissions(UploadFile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filepath!=null){
                    uploadPDF(filepath);
                }
                else
                    Toast.makeText(UploadFile.this,"Please Select a PDF",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadPDF(Uri filepath) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.show();

        String fileName = System.currentTimeMillis() + "";
        String datee = DateFormat.getDateInstance().format(new Date());
        StorageReference storageReference = storage.child("Notices/"+fileName+".pdf");
        storageReference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                    fileinfo obj = new fileinfo(notice.getText().toString(),uri.toString(),datee,spinner.getSelectedItem().toString());
                                databaseReference.child(databaseReference.push().getKey().valueOf(maxId+1)).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            progressDialog.dismiss();
                                            
                                            Toast.makeText(UploadFile.this,"File Successfully Uploaded",Toast.LENGTH_SHORT).show();

                                            notice.setText("");}
                                        else
                                            Toast.makeText(UploadFile.this,"File Not Uploaded",Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(UploadFile.this,"File Not Uploaded",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {

                int currentProgress = (int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            selectPDF();
        }
        else
            Toast.makeText(UploadFile.this, "Please Provide Permission", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void selectPDF() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,86);

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        if (requestCode==86 && resultCode == RESULT_OK && data!=null)
        {
            filepath=data.getData();
           // notice.setText("A File is Selected:" + data.getData().getLastPathSegment() );
            
        }
        else
            Toast.makeText(UploadFile.this,"Please Select a File",Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
    }

}