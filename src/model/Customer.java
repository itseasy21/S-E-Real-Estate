package model;

import java.util.ArrayList;
import java.util.Date;

public class Customer extends User{

    // Counter to keep track of event post
    static int postCounter = 0;

    String nationality;
    double income;
    protected ArrayList<String> interestedSuburbs= new ArrayList<String>();

    public Customer(String email, String password, String name, String address, String phoneNo, Date dob, String gender, String profilePic, String nationality, double income) {
        super(++postCounter, email, password, name, address, phoneNo, dob, gender, profilePic);
        this.nationality = nationality;
        this.income = income;
    }

    public void updateUser(String email, String password, String name, String address, String phoneNo, Date dob, String gender, String profilePic, String nationality, double income) throws UserException {
        updateUser(email, password, name, address, phoneNo, dob, gender, profilePic);
        setNationality(nationality);
        setIncome(income);
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

    public void setNationality(String nationality) throws UserException {
        if(nationality.isEmpty()){
            throw new UserException("Nationality is required, and should be full name of country.");
        }else{
            this.nationality = nationality;
        }
    }

    public void setIncome(double income) throws UserException {
        if(income <= 0){
            throw new UserException("Income Cannot be 0 or less!");
        }else{
            this.income = income;
        }
    }
}
