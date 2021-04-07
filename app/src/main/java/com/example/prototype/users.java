package com.example.prototype;

public class users {
    public String email;
    public String fname;
    public String lname;
    public users(){

    }
    public users(String email,String fname,String lname){
        this.email = email;
        this.fname = fname;
        this.lname = lname;
    }
    public void setEmail(String e){
        email = e;
    }
    public String getEmail(){
        return email;
    }
    public void setFname(String f){
        fname = f;
    }
    public String getFname(){
        return fname;
    }
    public void setLname(String l){
        lname = l;
    }
    public String getLname(){
        return lname;
    }

}
