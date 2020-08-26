package model;

public class Auction {
    private String auctionDate;
    private String bidderId;
    private double bidPrice;
    private double depositPrice;
    private boolean auctionStatus;

    public Auction(String auctionDate, String bidderId, double bidPrice, double depositPrice, boolean auctionStatus) {
        this.auctionDate = auctionDate;
        this.bidderId = bidderId;
        this.bidPrice = bidPrice;
        this.depositPrice = depositPrice;
        this.auctionStatus = auctionStatus;
    }
}
