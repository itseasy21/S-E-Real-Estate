package model;

import config.CustomerType;

import java.util.ArrayList;
import java.util.Date;

public class Customer extends User{

    // Counter to keep track of event post
    static int customerCounter = 0;

    String nationality;
    double income;
    CustomerType type;
    protected ArrayList<String> interestedSuburbs= new ArrayList<String>();

    public Customer(String email, String password, String name, String address, String phoneNo, String dob, String gender, String nationality, double income, CustomerType type) {
        super("CUS" + (++customerCounter), email, password, name, address, phoneNo, dob, gender);
        this.nationality = nationality;
        this.income = income;
        this.type = type;
    }

    public Customer(String id, String email, String password, String name, String address, String phoneNo, String dob, String gender, String nationality, double income, CustomerType type) {
        super(id, email, password, name, address, phoneNo, dob, gender);
        ++customerCounter;
        this.nationality = nationality;
        this.income = income;
        this.type = type;
    }

    public void updateUser(String email, String password, String name, String address, String phoneNo, String dob, String gender, String nationality, double income, CustomerType type){
        updateUser(email, password, name, address, phoneNo, dob, gender);
        this.nationality = nationality;
        this.income = income;
        this.type = type;
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

    public CustomerType getType() {
        return this.type;
    }
}
