package com.example.tez.trackuserlocation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin_home extends AppCompatActivity {

    DatabaseReference databaseReference;
    List<Attendance_Data> list;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home_layout);
        list=new ArrayList<>();
        listView=findViewById(R.id.list_view_xml);
        databaseReference= FirebaseDatabase.getInstance().getReference("Attendance");
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot attendanceDataShot:dataSnapshot.getChildren()) {
                    Attendance_Data attendance_data = attendanceDataShot.getValue(Attendance_Data.class);
                    list.add(attendance_data);
                }
                Attendance_List adapter=new Attendance_List(Admin_home.this,list);
               // ArrayAdapter<String> adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
