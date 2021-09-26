package com.example.wikings.Malithi;

public class PersonDetails {

    String firstname,lastname,mobileNo,email,password;

    public PersonDetails(String firstname,String lastname, String mobileNo, String email,String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobileNo = mobileNo;
        this.email = email;
        this.password = password;

    }

    public PersonDetails(String lastname, String mobileNo, String email, String password) {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
