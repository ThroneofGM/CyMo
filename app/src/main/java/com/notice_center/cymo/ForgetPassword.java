package com.notice_center.cymo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    EditText umail;
    Button reset;
    String usermail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        umail = findViewById(R.id.userResEmail);
        reset = findViewById(R.id.Reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usermail = umail.getText().toString();

                if(usermail.isEmpty()){
                    Toast.makeText(ForgetPassword.this, "Please provide email", Toast.LENGTH_SHORT).show();
                }
                else{
                    forgetPassword();
                }

            }
        });
    }

    public void forgetPassword(){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(usermail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ForgetPassword.this,"Check your email",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgetPassword.this,AuthAdmin.class));
                        }
                        else{
                            Toast.makeText(ForgetPassword.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}