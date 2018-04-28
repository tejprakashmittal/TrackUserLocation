package com.example.tez.trackuserlocation;

public class faculty_registration_data {
    String faculty_name="";
    String faculty_id="";
    String faculty_dept="";
    String faculty_type="";
    String faculty_email="";
    String faculty_mob="";
    String faculty_username="";
    String faculty_pass="";
    String fuser_type="";

    public faculty_registration_data(){}

    public faculty_registration_data(String faculty_name, String faculty_id, String faculty_dept, String faculty_type, String faculty_email, String faculty_mob, String faculty_username, String faculty_pass,String fuser_type) {
        this.faculty_name = faculty_name;
        this.faculty_id = faculty_id;
        this.faculty_dept = faculty_dept;
        this.faculty_type = faculty_type;
        this.faculty_email = faculty_email;
        this.faculty_mob = faculty_mob;
        this.faculty_username = faculty_username;
        this.faculty_pass = faculty_pass;
        this.fuser_type=fuser_type;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public String getFaculty_dept() {
        return faculty_dept;
    }

    public String getFaculty_type() {
        return faculty_type;
    }

    public String getFaculty_email() {
        return faculty_email;
    }

    public String getFaculty_mob() {
        return faculty_mob;
    }

    public String getFaculty_username() {
        return faculty_username;
    }

    public String getFaculty_pass() {
        return faculty_pass;
    }

    public String getFuser_type() {
        return fuser_type;
    }
}
