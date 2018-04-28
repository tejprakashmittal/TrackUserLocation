package com.example.tez.trackuserlocation;

public class Student_Signup_Data {
    private String name="";
    private String username="";
    private String mobilenumber="";
    private String dob="";
    private String password="";
    private String email="";
    private String branch="";
    private String year="";
    private String rollnumber="";
    private String address="";
    private String user_type="";

    Student_Signup_Data(){}

    Student_Signup_Data(String name, String username, String mobilenumber, String dob, String password, String email, String branch, String year, String rollnumber, String address,String user_type) {
        this.name = name;
        this.username = username;
        this.mobilenumber = mobilenumber;
        this.dob = dob;
        this.password = password;
        this.email = email;
        this.branch = branch;
        this.year = year;
        this.rollnumber = rollnumber;
        this.address = address;
        this.user_type=user_type;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public String getDob() {
        return dob;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBranch() {
        return branch;
    }

    public String getYear() {
        return year;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public String getAddress() {
        return address;
    }
    public String getUser_type(){return user_type;}
}
