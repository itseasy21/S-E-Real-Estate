package model;

public class Property {
    private int propertyId;
    private String propertyName;
    private int propertyType;
    private String propertyAddress;
    private double minPrice;
    private String suburb;
    private int  bedroomCount;
    private int bathroomCount;
    private int parkingCount;
    private double sellingPrice;
    private double rentalPrice;
    public static final int RENT = 1;
    public static final int  SALE = 2;

    public Property(int propertyId, String propertyName, int propertyType, String propertyAddress, double minPrice, String suburb, int bedroomCount, int bathroomCount, int parkingCount, double pricing) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.propertyAddress = propertyAddress;
        this.minPrice = minPrice;
        this.suburb = suburb;
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;
        this.parkingCount = parkingCount;
        if (propertyType == RENT) {
            this.rentalPrice = pricing;
            this.sellingPrice = 0;
        } else {
            this.rentalPrice = 0;
            this.sellingPrice = pricing;
        }

    }


    public int getPropertyId() {
        return propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isPropertyTypeRental(){
        return propertyType == RENT;
    }
    public boolean isPropertyTypeSale(){
        return propertyType == SALE;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public int getBedroomCount() {
        return bedroomCount;
    }

    public void setBedroomCount(int bedroomCount) {
        this.bedroomCount = bedroomCount;
    }

    public int getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCount(int bathroomCount) {
        this.bathroomCount = bathroomCount;
    }

    public int getParkingCount() {
        return parkingCount;
    }

    public void setParkingCount(int parkingCount) {
        this.parkingCount = parkingCount;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) throws PropertyException {
        if(isPropertyTypeSale()) {
            this.sellingPrice = sellingPrice;
        } else{
            throw new PropertyException("Cannot set Selling price for this Property type");
        }
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) throws PropertyException {
        if(isPropertyTypeRental()) {
            this.rentalPrice = rentalPrice;
        } else {
            throw new PropertyException("Cannot set Rental price for this Property type");
        }
    }



    @Override
    public String toString() {
        return "Property{" +
                "propertyId=" + propertyId +
                ", propertyName='" + propertyName + '\'' +
                ", propertyType RENTAL ='" +isPropertyTypeRental() + '\'' +
                ", propertyAddress='" + propertyAddress + '\'' +
                ", minPrice=" + minPrice +
                ", suburb='" + suburb + '\'' +
                ", bedroomCount=" + bedroomCount +
                ", bathroomCount=" + bathroomCount +
                ", parkingCount=" + parkingCount +
                ", sellingPrice=" + sellingPrice +
                ", rentalPrice=" + rentalPrice +
                '}';
    }
}
