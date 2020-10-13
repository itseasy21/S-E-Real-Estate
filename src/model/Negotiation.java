package model;

import config.SaleStatus;

public class Negotiation extends SalesMedium {

    private int negoCounter = 0;
    private String id;
    private double minPrice;
    private double currentPrice;
    private final String bidderID;

    public Negotiation(double minPrice, double bidPrice, String customerID, Property property, String date) {
        super(property, date, SaleStatus.ONGOING);
        this.id = "NEG" + (++negoCounter);
        this.minPrice = minPrice;
        this.currentPrice = bidPrice;
        this.bidderID = customerID;
    }

    public String getId() {
        return this.id;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public String getBidderID() {
        return bidderID;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    @Override
    public void handleBids(Bids bid) {
        //TODO
    }
}
