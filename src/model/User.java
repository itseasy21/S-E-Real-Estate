package model;

public abstract class User {

//  Common Variables to All User
    protected String email, password, name, address, gender, phoneNo, id;
    protected String dob;

    public User(String id, String email, String password, String name, String address, String phoneNo, String dob, String gender){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.gender = gender;
    }

    void updateUser(String email, String password, String name, String address, String phoneNo, String dob, String gender){
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.gender = gender;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDob() {
        return this.dob;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

    public String getGender() {
        return this.gender;
    }

    public String getPassword() {
        return this.password;
    }

    public String showDetails(){
        String printDetails = "";

        printDetails += "ID:\t"+getId();
        printDetails += "\nName:\t"+getName();
        printDetails += "\nEmail:\t"+getEmail();
        printDetails += "\nGender:\t"+getGender();
        printDetails += "\nDOB:\t"+getDob();
        printDetails += "\nPhone No:\t"+getPhoneNo();

        return printDetails;

    }

}
