package model;

import java.util.Date;

public abstract class User {

//  Common Variables to All User
    protected String email, password, name, address, profilePic, gender;
    protected int id, phoneNo;
    protected Date dob;

    public User(int id, String email, String password, String name, String address, int phoneNo, Date dob, String gender, String profilePic){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.gender = gender;
        this.profilePic = profilePic;
    }

    void updateUser(String email, String password, String name, String address, int phoneNo, Date dob, String gender, String profilePic){
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.gender = gender;
        this.profilePic = profilePic;
    }




}
