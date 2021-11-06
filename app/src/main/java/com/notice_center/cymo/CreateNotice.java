package com.notice_center.cymo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNotice extends AppCompatActivity implements View.OnClickListener{

    Button create_notice;
    EditText title_et,desc_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notice);

        create_notice=findViewById(R.id.generatePDF);
        title_et=findViewById(R.id.title_et);
        desc_et=findViewById(R.id.desc_et);

        create_notice.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.generatePDF) {
            if (!title_et.getText().toString().isEmpty() && !desc_et.getText().toString().isEmpty())
                generatePDF(title_et.getText().toString(), desc_et.getText().toString());
        }
    }

   public void generatePDF(String title,String desc){
        try {
            String path = getExternalFilesDir(null) + "/PDF";
            File file = new File(path);
            if (!file.exists()){
                file.mkdirs();
            }

            File pdf_file = new File(file, "/MYPDF" + getCurrentTime() + "_" + getTodayDate() + ".pdf");
            if (!pdf_file.exists()){
                pdf_file.createNewFile();
            }

            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream(pdf_file));
            document.open();
            Paragraph title_para = new Paragraph();
            title_para.add(title);
            title_para.setSpacingAfter(10.0f);
            title_para.setAlignment(Element.ALIGN_CENTER);
            document.add(title_para);
            Paragraph desc_para = new Paragraph();
            desc_para.add(desc);
            document.add(desc_para);
            document.close();

            Toast.makeText(this,"Notice created successfully! \nLocation" + pdf_file.getAbsolutePath(),Toast.LENGTH_LONG).show();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
   }

    private String getCurrentTime(){
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private String getTodayDate(){
        return new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
    }

}