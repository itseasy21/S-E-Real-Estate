package model;

import config.ApplicationStatus;

import java.util.Scanner;

public class RentalApplication extends Application{

    public RentalApplication(String empID, String custID, Property thisProperty, double applicationPrice) {
        super(empID, custID, thisProperty, applicationPrice);
    }

    public void completeApplication(){
        this.setStatus(ApplicationStatus.APPROVED);
//        property.setStatus("Rented");
//        property.setAvailable(false);
    }
}


