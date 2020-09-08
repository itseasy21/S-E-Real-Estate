package model;

import config.CustomerType;

import java.util.ArrayList;
import java.util.Date;

public class Customer extends User{

    // Counter to keep track of event post
    static int postCounter = 0;

    String nationality;
    double income;
    CustomerType type;
    protected ArrayList<String> interestedSuburbs= new ArrayList<String>();

    public Customer(String email, String password, String name, String address, String phoneNo, Date dob, String gender, String profilePic, String nationality, double income) {
        super(++postCounter, email, password, name, address, phoneNo, dob, gender, profilePic);
        this.nationality = nationality;
        this.income = income;
    }

    public void updateUser(String email, String password, String name, String address, String phoneNo, Date dob, String gender, String profilePic, String nationality, double income){
        updateUser(email, password, name, address, phoneNo, dob, gender, profilePic);
        this.nationality = nationality;
        this.income = income;
    }

    public String getNationality() {
        return this.nationality;
    }

    public double getIncome() {
        return this.income;
    }

    public String showDetails(){
        String printDetails = super.showDetails();
        printDetails += "\nNationality:\t"+getNationality();
        printDetails += "\nIncome:\t"+getIncome();

        return printDetails;
    }
}
