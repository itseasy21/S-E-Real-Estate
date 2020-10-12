package model;

import config.ApplicationStatus;

public abstract class Application {

    int applicationCounter = 0;

    String id, empID, custID;
    Property property;
    double applicationPrice;

    ApplicationStatus status = ApplicationStatus.PENDING;

    public Application(String empID, String custID, Property thisProperty, double applicationPrice){
        id = "APP" + (++applicationCounter);
        this.empID = empID;
        this.custID = custID;
        this.property = thisProperty;
        this.applicationPrice = applicationPrice;
    }

    public String getId() {
        return this.id;
    }

    public ApplicationStatus getStatus() {
        return this.status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getCustID() {
        return this.custID;
    }

    public double getApplicationPrice() {
        return this.applicationPrice;
    }

    public Property getProperty() {
        return this.property;
    }

    public String getEmpID() {
        return this.empID;
    }

    public abstract String getDetails();
    public abstract void completeApplication();
}
