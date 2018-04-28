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

public class faculty_registration extends AppCompatActivity {

    DatabaseReference facultyDatabaseReference;
    EditText faculty_name,faculty_id,faculty_username,faculty_email,faculty_mob,faculty_pass;
    Spinner faculty_type,faculty_department;
    Button faculty_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_registration_layout);
         faculty_name=findViewById(R.id.faculty_xml_name);
         faculty_id=findViewById(R.id.faculty_xml_id);
        faculty_username=findViewById(R.id.faculty_xml_username);
        faculty_email=findViewById(R.id.faculty_xml_email);
        faculty_mob=findViewById(R.id.faculty_xml_mobile);
        faculty_pass=findViewById(R.id.faculty_xml_pass);
        faculty_type=findViewById(R.id.faculty_xml_type);
        faculty_department=findViewById(R.id.faculty_xml_department);
        faculty_submit=findViewById(R.id.faculty_xml_submit);

        facultyDatabaseReference= FirebaseDatabase.getInstance().getReference("Faculty_Registration");

        faculty_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname=faculty_name.getText().toString().trim();
                String fid=faculty_id.getText().toString().trim();
                String fusername=faculty_username.getText().toString().trim();
                String femail=faculty_email.getText().toString().trim();
                String fmob=faculty_mob.getText().toString().trim();
                String fpass=faculty_pass.getText().toString().trim();
                String ftype=faculty_type.getSelectedItem().toString().trim();
                String fdept=faculty_department.getSelectedItem().toString().trim();
                String mfusertype="Faculty".trim();

                if(!(TextUtils.isEmpty(fname)||TextUtils.isEmpty(fid)||TextUtils.isEmpty(fusername)||TextUtils.isEmpty(femail)||TextUtils.isEmpty(fmob)||TextUtils.isEmpty(fpass)||TextUtils.isEmpty(ftype)||TextUtils.isEmpty(fdept)||TextUtils.isEmpty(mfusertype)))
                {
                    String id=facultyDatabaseReference.push().getKey();
                    faculty_registration_data facultyRegistrationData=new faculty_registration_data(fname,fid,fdept,ftype,femail,fmob,fusername,fpass,mfusertype);
                    facultyDatabaseReference.child(id).setValue(facultyRegistrationData);
                    Toast.makeText(getApplicationContext(),"Successfully Registered", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(faculty_registration.this,admin_dashboard.class));
                }
                else
                    Toast.makeText(faculty_registration.this,"Please fill complete details",Toast.LENGTH_LONG).show();
            }
        });
    }
}
