package com.example.tez.trackuserlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_login extends AppCompatActivity {
    private DatabaseReference databaseReference1,databaseReference2;
    private EditText edtUsername,edtPassword;
    private ImageButton btnSubmit;
    Button btnSignUp;
    private Spinner spnType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login_layout);
         edtUsername=findViewById(R.id.xml_login_username);
        edtPassword=findViewById(R.id.xml_login_password);
        btnSubmit=findViewById(R.id.xml_login_submit);
        spnType=findViewById(R.id.xml_login_spin_type);
        btnSignUp=findViewById(R.id.xml_login_newuser);
        databaseReference1= FirebaseDatabase.getInstance().getReference("Signup_Data");
        databaseReference2= FirebaseDatabase.getInstance().getReference("Faculty_Registration");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Student_SignUp.class));
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username=edtUsername.getText().toString().trim();
                final String usertype=spnType.getSelectedItem().toString().trim();
                final String userpass=edtPassword.getText().toString().trim();
                if(!(TextUtils.isEmpty(username)||TextUtils.isEmpty(userpass)||usertype.equals("Select"))) {
                    if (username.equals("admin") && usertype.equals("Admin") && userpass.equals("1234")) {
                        startActivity(new Intent(getApplicationContext(), admin_dashboard.class));
                        Toast.makeText(Student_login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                    }
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    Student_Signup_Data signData = ds.getValue(Student_Signup_Data.class);

                                    if ((username.equals(signData.getUsername())) && (usertype.equals(signData.getUser_type())) && (userpass.equals(signData.getPassword()))) {
                                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                        Bundle extras = new Bundle();
                                        extras.putString("EXTRA_RollNo", signData.getRollnumber().trim());
                                        extras.putString("EXTRA_Name", signData.getName().trim());
                                        intent.putExtras(extras);
                                        startActivity(intent);
                                        Toast.makeText(Student_login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        databaseReference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    faculty_registration_data FacultyData = dsp.getValue(faculty_registration_data.class);
                                    if ((username.equals(FacultyData.getFaculty_username())) && (usertype.equals(FacultyData.getFuser_type())) && (userpass.equals(FacultyData.getFaculty_pass()))) {
                                        startActivity(new Intent(Student_login.this, Admin_home.class));
                                        Toast.makeText(Student_login.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                    else
                                        Toast.makeText(Student_login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                }
                else
                    Toast.makeText(Student_login.this, "Please fill complete details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
