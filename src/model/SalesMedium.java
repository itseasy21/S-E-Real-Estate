package model;

import config.SaleStatus;

import java.util.ArrayList;

public abstract class SalesMedium {

    protected Property property;
    protected SaleStatus saleStatus;
    protected String contractDate;
    protected ArrayList<Bids> bidsList = new ArrayList<Bids>();

    public SalesMedium(Property property, String date, SaleStatus saleStatus){
        this.property = property;
        this.saleStatus = saleStatus;
        this.contractDate = date;
    }

    public abstract void handleBids(Bids bid);

    public String getContractDate() {
        return this.contractDate;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public SaleStatus getSaleStatus() {
        return this.saleStatus;
    }

    public Property getProperty() {
        return this.property;
    }

}
