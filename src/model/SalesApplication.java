package model;

import config.ApplicationStatus;

public class SalesApplication extends Application{

    String saleType= ""; //Auction, Negotiation

    public SalesApplication(String empID, String custID, Property thisProperty, double applicationPrice, String saletype) {
        super(empID, custID, thisProperty, applicationPrice);
        this.saleType = saletype;
    }

    @Override
    public String getDetails() {
        return "Application ID:\t" + this.getId() + "\n" +
                "Property Name:\t" + this.getProperty().getPropertyName() + "\n" +
                "Property Address:\t" + this.getProperty().getPropertyAddress() + "\n" +
                "Listed Price:\t$" + this.getProperty().getSellingPrice() + "\n" +
                "Applied Price:\t$" + this.getApplicationPrice() + "\n" +
                "Sale Application Type:\t$" + this.getSaleType() + "\n" +
                "Status:\t" + this.getStatus().toString() + "\n";
    }

    public String getSaleType() {
        return this.saleType;
    }

    @Override
    public void completeApplication() {
        this.setStatus(ApplicationStatus.APPROVED);
    }
}
