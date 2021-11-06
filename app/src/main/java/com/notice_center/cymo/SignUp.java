package com.notice_center.cymo;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    private EditText email,password1,password2;
    private Button signupButton;
    private TextView signintv;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.userEmail);
        password1=findViewById(R.id.userPassword);
        password2=findViewById(R.id.userPassword2);
        signupButton=findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);
        signintv=findViewById(R.id.signinTV);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        signintv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, AuthAdmin.class);
                startActivity(intent);
                finish();
            }
        });


    }
    private void Register(){
        String email1 = email.getText().toString();
        String password=password1.getText().toString();
        String pword=password2.getText().toString();

        if(TextUtils.isEmpty(email1)){
            email.setError("Enter your email");
            return;}
        else if(TextUtils.isEmpty(password)){
            password1.setError("Enter your password");
            return;}
        else if(TextUtils.isEmpty(pword)){
            password2.setError("Confirm your password");
            return;}
        else if(!password.equals(pword)){
            password2.setError("Password mismatch");
            return;}
        else if(password.length()<4){
            password1.setError("Length should be > 4");
            return;}
        else if(!isValidEmail(email1)){
            email.setError("Invalid Email");
            return;}
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        mAuth.createUserWithEmailAndPassword(email1,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Successfully registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUp.this, AdminDashboard.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
    @org.jetbrains.annotations.NotNull
    private Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
