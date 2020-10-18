package model;

import config.SaleStatus;

public class Negotiation extends SalesMedium {

    private int negoCounter = 0;
    private String id;
    private double minPrice;
    private double currentPrice;
    private String bidderID;

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

    public void setBidderID(String bidderID) {
        this.bidderID = bidderID;
    }

    @Override
    public void handleBids(Bids bid) {
        if(bid != null){
            if(bid.getValue() > 0
                    && bid.getPostID().equals(this.getId())
                    && bid.getValue() >= (this.currentPrice)
                    && this.getSaleStatus().equals(SaleStatus.ONGOING)
                    && bid.getResponderID().equals(this.getBidderID())) {

                bidsList.add(bid);

            }

        }
    }
}
