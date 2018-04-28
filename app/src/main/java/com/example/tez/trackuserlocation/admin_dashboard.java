package com.example.tez.trackuserlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class admin_dashboard extends AppCompatActivity {
    ImageButton btn_faculty_reg,btn_student_attendance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);
        btn_faculty_reg=findViewById(R.id.btn_faculty_signup);
        btn_student_attendance=findViewById(R.id.btn_student_status);

        btn_student_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_dashboard.this,Admin_home.class));
            }
        });

        btn_faculty_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_dashboard.this,faculty_registration.class));
            }
        });

    }
    public void click1(View v){
        Toast.makeText(this, "Comming Soon", Toast.LENGTH_SHORT).show();
    }
}
