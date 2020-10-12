package model;

import config.AuctionStatus;

import java.util.ArrayList;
import java.util.Calendar;

public class Auction {

    private int auctionCounter = 0;

    private String id;
    private String auctionDate;
    private Calendar calendar;
    private long lastBidTime;
    private Property property;
    private AuctionStatus auctionStatus;
    private final int minIncrease = 1000;
    private double highestBid;
    private String highestBidder;
    protected ArrayList<Bids> bidsList = new ArrayList<Bids>();

    public Auction(String auctionDate, Property property) {
        this.id = "AUC" + (++auctionCounter);
        this.auctionDate = auctionDate;
        this.property = property;
        calendar = Calendar.getInstance();
        lastBidTime = calendar.getTimeInMillis();
        auctionStatus = AuctionStatus.PENDING;
    }

    public Property getProperty() {
        return property;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public String getAuctionDate() {
        return auctionDate;
    }

    public String getId() {
        return id;
    }

    public long getLastBidTime() {
        return lastBidTime;
    }

    public void setLastBidTime(long lastBidTime) {
        this.lastBidTime = lastBidTime;
    }

    // Reference: https://stackoverflow.com/a/43176723
    static boolean has30SecsPassed(Long time) {
        Long numberOfMilliSec = Calendar.getInstance().getTimeInMillis() - time;
        double numberOfSecondsPassed = numberOfMilliSec / 1000.0;
        return numberOfSecondsPassed >= 30;
    }

    public int getMinIncrease() {
        return minIncrease;
    }

    public void setAuctionStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

    public AuctionStatus getAuctionStatus() {
        return auctionStatus;
    }

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void handleBids(Bids bid){
        if(bid != null){
            if(bid.getValue() > 0
                    && bid.getPostID().equals(this.getId())
                    && bid.getValue() >= (this.highestBid + this.minIncrease)
                    && this.getAuctionStatus().equals(AuctionStatus.ONGOING)){
                if(has30SecsPassed(getLastBidTime())) {
                    this.setAuctionStatus(AuctionStatus.COMPLETED);
                }else{
                    this.setAuctionStatus(AuctionStatus.ONGOING);
                    bidsList.add(bid);
                    setLastBidTime(bid.getBidTime());
                    setHighestBid(bid.getValue());
                    setHighestBidder(bid.getResponderID());
                }
            }


        }
    }

}
