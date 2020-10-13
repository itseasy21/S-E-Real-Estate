package model;

import config.ApplicationStatus;
import config.PropertyState;

import java.util.Scanner;

public class RentalApplication extends Application{

    public RentalApplication(String empID, String custID, Property thisProperty, double applicationPrice) {
        super(empID, custID, thisProperty, applicationPrice);
    }

    @Override
    public String getDetails() {
        return "Application ID:\t" + this.getId() + "\n" +
                "Property Name:\t" + this.getProperty().getPropertyName() + "\n" +
                "Property Address:\t" + this.getProperty().getPropertyAddress() + "\n" +
                "Weekly Rent:\t$" + this.getProperty().getRentalPrice() + "\n" +
                "Applied Rent:\t$" + this.getApplicationPrice() + "\n" +
                "Status:\t" + this.getStatus().toString() + "\n";
    }

    public void completeApplication(){
        this.setStatus(ApplicationStatus.APPROVED);
        property.setAvailability(PropertyState.RENTED);
    }
}


