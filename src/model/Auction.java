package model;

import java.util.ArrayList;
import java.util.Calendar;

public class Auction {

    private int auctionCounter = 0;

    private String id;
    private String auctionDate;
    private Calendar calendar;
    private long lastBidTime;
    private Property property;
    private boolean auctionStatus;
    private final int minIncrease = 1000;
    private double highestBid;
    protected ArrayList<Bids> bidsList = new ArrayList<Bids>();

    public Auction(String auctionDate, Property property) {
        this.id = "AUC" + (++auctionCounter);
        this.auctionDate = auctionDate;
        this.property = property;
        calendar = Calendar.getInstance();
        lastBidTime = calendar.getTimeInMillis();
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

    public void handleBids(Bids bid){
        if(bid != null){
            if(bid.getValue() > 0
                    && bid.getPostID().equals(this.getId())
                    && bid.getValue() >= (this.highestBid + this.minIncrease)
                    && !has30SecsPassed(getLastBidTime())){
                bidsList.add(bid);
                setLastBidTime(bid.getBidTime());
            }
        }
    }

}
