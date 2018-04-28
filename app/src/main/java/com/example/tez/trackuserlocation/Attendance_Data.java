package com.example.tez.trackuserlocation;

public class Attendance_Data {
    String rollno="";
    String name="";
    String status="";

    public Attendance_Data(){}

    public Attendance_Data(String rollno, String name, String status) {
        this.rollno = rollno;
        this.name = name;
        this.status = status;
    }

    public String getRollno() {
        return rollno;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
