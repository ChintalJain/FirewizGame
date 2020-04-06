package com.example.firewiz.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class UserDetails {
    private String userName,email,password,fullName,gender,mobileNumber="";
    private String dateOfBirth;
    private int age;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        //this.age=
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean validateUserName()
    {
        return(Pattern.matches("\\S{6,15}",this.userName));
    }
    public boolean validateEmail()
    {
        return(Pattern.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",this.email));
    }
    public boolean validatePassword()
    {
        boolean ans=Pattern.matches("[a-z|A-Z|0-9|@#$%&]{6,10}",this.password);
        if(ans)
        {
            // for atleast one special character & atleast one digit
            ans=!(Pattern.matches("[a-z|A-Z|0-9]{6,10}",this.password)||Pattern.matches("[a-z|A-Z|@#$%&]{6,10}",this.password)||Pattern.matches("[0-9|@#$%&]{6,10}",this.password));
        }
        return(ans);
    }
    public boolean validateFullName()
    {
        return(Pattern.matches("[a-z|A-Z]+\\s[a-z|A-Z]+",this.fullName));
    }
    public boolean validateDateOfBirth()
    {
        if((Pattern.matches("[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}",this.dateOfBirth)))
        {
            String dob[]=this.dateOfBirth.split("/");

            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = df.format(c);
            String cdate[]=formattedDate.split("/");
            if(Integer.parseInt(dob[2])<Integer.parseInt(cdate[2]) && Integer.parseInt(dob[0])>0 && Integer.parseInt(dob[0])<32 && Integer.parseInt(dob[1])>0 && Integer.parseInt(dob[1])<13)
            {
                this.age=Integer.parseInt(cdate[2])-Integer.parseInt(dob[2]);
                return true;
            }
        }
        return false;
    }
    public boolean validateMobileNumber()
    {
        return(Pattern.matches("\\d{10}",this.mobileNumber));
    }

}
