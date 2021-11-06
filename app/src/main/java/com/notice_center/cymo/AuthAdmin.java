package com.notice_center.cymo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthAdmin extends AppCompatActivity {
    private EditText email,password1;
    private Button signInButton;
    private TextView signupTv,stlogin,fPass;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_admin);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.userEmail);
        password1=findViewById(R.id.userPassword);
        signInButton=findViewById(R.id.login);
        progressDialog=new ProgressDialog(this);
        signupTv=findViewById(R.id.signupTV);
        stlogin=findViewById(R.id.stLog);
        fPass = findViewById(R.id.ForgetPass);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthAdmin.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        stlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthAdmin.this, StudentAuth.class);
                startActivity(intent);
                finish();
            }
        });

        fPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthAdmin.this,ForgetPassword.class);
                startActivity(intent);
            }
        });


    }
            private void Login(){
                String email1 = email.getText().toString();
                String password=password1.getText().toString();

                if(TextUtils.isEmpty(email1)){
                    email.setError("Enter your email");
                    return;}
                else if(TextUtils.isEmpty(password)){
                    password1.setError("Enter your password");
                    return;}

                progressDialog.setMessage("Please Wait");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                mAuth.signInWithEmailAndPassword(email1,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(AuthAdmin.this, "Login Successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AuthAdmin.this, AdminDashboard.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(AuthAdmin.this, "Login Failed", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                });
            }
}