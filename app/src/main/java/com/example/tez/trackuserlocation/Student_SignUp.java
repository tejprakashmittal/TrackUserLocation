package com.example.tez.trackuserlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Student_SignUp extends AppCompatActivity {
    private EditText edtName,edtUsername,edtDob,edtPass,edtEmail,edtRollno,edtAddress,edtMob;
    private Spinner spnBranch,spnYear;
    private Button btnSubmit;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_sign_layout);
        databaseReference= FirebaseDatabase.getInstance().getReference("Signup_Data");
        edtName=findViewById(R.id.xml_signup_name);
        edtUsername=findViewById(R.id.xml_signup_username);
        edtDob=findViewById(R.id.xml_signup_dob);
        edtPass=findViewById(R.id.xml_signup_pass);
        edtEmail=findViewById(R.id.xml_signup_email);
        edtMob=findViewById(R.id.xml_signup_mobile);
        edtRollno=findViewById(R.id.xml_signup_rollno);
        edtAddress=findViewById(R.id.xml_signup_Address);
        spnBranch=findViewById(R.id.xml_signup_branch);
        spnYear=findViewById(R.id.xml_signup_year);
        edtMob=findViewById(R.id.xml_signup_mobile);
        btnSubmit=findViewById(R.id.xml_signup_btnsubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mystrname=edtName.getText().toString().trim();
                String mystrusername=edtUsername.getText().toString().trim();
                String mystrDob=edtDob.getText().toString().trim();
                String mystrEmail=edtEmail.getText().toString().trim();
                String mystrrollnumber=edtRollno.getText().toString().trim();
                String mystrmob=edtMob.getText().toString().trim();
                String mystrAddress=edtAddress.getText().toString().trim();
                String mystrBranch=spnBranch.getSelectedItem().toString().trim();
                String mystrYear=spnYear.getSelectedItem().toString().trim();
                String myusertype="Student".trim();
                String mystrpassword=edtPass.getText().toString().trim();

                if(!(TextUtils.isEmpty(mystrname)||TextUtils.isEmpty(mystrusername)||TextUtils.isEmpty(mystrrollnumber)||TextUtils.isEmpty(myusertype)||TextUtils.isEmpty(mystrpassword)||TextUtils.isEmpty(mystrEmail)||TextUtils.isEmpty(mystrDob)||TextUtils.isEmpty(mystrAddress)||mystrBranch.equals("Select Branch")||mystrYear.equals("Select Year")))
                {
                    String id=databaseReference.push().getKey();
                    Student_Signup_Data signData=new Student_Signup_Data(mystrname,mystrusername,mystrmob,mystrDob,mystrpassword,mystrEmail,mystrBranch,mystrYear,mystrrollnumber,mystrAddress,myusertype);
                    databaseReference.child(id).setValue(signData);
                    Toast.makeText(getApplicationContext(),"Successfully Registered", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Student_login.class));
                }
                else
                    Toast.makeText(getApplicationContext(),"Please fill complete details",Toast.LENGTH_LONG).show();
            }
        });

    }
}
