package com.notice_center.cymo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StudentAuth extends AppCompatActivity {

    TextView student,admin_login;
    Spinner spinner;
    Button enter;
    List<String> semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_auth);

        student = findViewById(R.id.studentSpi);
        spinner = findViewById(R.id.spinnerSemStu);
        enter = findViewById(R.id.stenter);
        admin_login = findViewById(R.id.backpage);

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
                    student.setText(parent.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner.getSelectedItem().equals("First")){
                    Intent intent = new Intent(StudentAuth.this,sdashboardone.class);
                    startActivity(intent);
                }
                else if(spinner.getSelectedItem().equals("Second")){
                    Intent intent = new Intent(StudentAuth.this,sdashboardtwo.class);
                    startActivity(intent);
                }
                else if(spinner.getSelectedItem().equals("Third")){
                    Intent intent = new Intent(StudentAuth.this,sdashboardthree.class);
                    startActivity(intent);
                }
                else if(spinner.getSelectedItem().equals("Fourth")){
                    Intent intent = new Intent(StudentAuth.this,sdashboardfour.class);
                    startActivity(intent);
                }
                else if(spinner.getSelectedItem().equals("Fifth")){
                    Intent intent = new Intent(StudentAuth.this,sdashboardfive.class);
                    startActivity(intent);
                }
                else if (spinner.getSelectedItem().equals("Sixth")){
                    Intent intent = new Intent(StudentAuth.this,sdashboardsix.class);
                    startActivity(intent);
                }
            }
        });

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentAuth.this,AuthAdmin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}