package model;

import java.util.Calendar;

public class Bids {

    private String auctionID;
    private double value = 0;
    private String responderID;
    private long bidTime;
    Calendar now = Calendar.getInstance();

    public Bids(String postID, double value, String responderID) {
        this.auctionID = postID;
        this.value = value;
        this.responderID = responderID;
        this.bidTime = now.getTimeInMillis();
    }

    public long getBidTime() {
        return bidTime;
    }

    public void setAuctionID(String id) {
        this.auctionID = id;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setResponderID(String id) {
        this.responderID = id;
    }

    public String getPostID() {
        return this.auctionID;
    }

    public double getValue() {
        return this.value;
    }

    public String getResponderID() {
        return this.responderID;
    }

}
