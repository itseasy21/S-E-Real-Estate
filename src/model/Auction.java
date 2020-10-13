package model;

import config.SaleStatus;

import java.util.ArrayList;
import java.util.Calendar;

public class Auction extends SalesMedium {

    private int auctionCounter = 0;

    private String id;
    private Calendar calendar;
    private long lastBidTime;
    private final int minIncrease = 1000;
    private double highestBid;
    private String highestBidder;

    public Auction(String auctionDate, Property property) {
        super(property, auctionDate, SaleStatus.PENDING);
        this.id = "AUC" + (++auctionCounter);
        calendar = Calendar.getInstance();
        lastBidTime = calendar.getTimeInMillis();
    }

    public double getHighestBid() {
        return highestBid;
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

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    @Override
    public void handleBids(Bids bid){
        if(bid != null){
            if(bid.getValue() > 0
                    && bid.getPostID().equals(this.getId())
                    && bid.getValue() >= (this.highestBid + this.minIncrease)
                    && this.getSaleStatus().equals(SaleStatus.ONGOING)){
                if(has30SecsPassed(getLastBidTime())) {
                    this.setSaleStatus(SaleStatus.COMPLETED);
                }else{
                    this.setSaleStatus(SaleStatus.ONGOING);
                    bidsList.add(bid);
                    setLastBidTime(bid.getBidTime());
                    setHighestBid(bid.getValue());
                    setHighestBidder(bid.getResponderID());
                }
            }


        }
    }

}
