package model;

import config.EmployeeType;

public class Property {
    private int propertyId;
    private String propertyName;
    private PropertyType propertyType;
    private PropertyCategory propertyCategory;
    private String propertyAddress;
    private double minPrice;
    private String suburb;
    private int  bedroomCount;
    private int bathroomCount;
    private int parkingCount;
    private double sellingPrice;
    private double rentalPrice;
    private String employeeId;
    private EmployeeType empRole;
    private boolean auction;
    private boolean Availability;
    private String customerId;


    public Property( String propertyName, PropertyType propertyType, String propertyAddress, double minPrice, String suburb, int bedroomCount, int bathroomCount, int parkingCount, double pricing, PropertyCategory propertyCategory) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.propertyAddress = propertyAddress;
        this.minPrice = minPrice;
        this.suburb = suburb;
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;
        this.parkingCount = parkingCount;
        this.propertyCategory = propertyCategory;
        this.empRole = empRole;
        this.auction = false;

        if (propertyType == PropertyType.Rent) {
            this.rentalPrice = pricing;
            this.sellingPrice = 0;
        } else {
            this.rentalPrice = 0;
            this.sellingPrice = pricing;
        }

    }

    public Property(int propertyId, String propertyName, PropertyType propertyType, String propertyAddress, double minPrice, String suburb, int bedroomCount, int bathroomCount, int parkingCount, double pricing, PropertyCategory propertyCategory) {
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.propertyAddress = propertyAddress;
        this.minPrice = minPrice;
        this.suburb = suburb;
        this.bedroomCount = bedroomCount;
        this.bathroomCount = bathroomCount;
        this.parkingCount = parkingCount;
        this.propertyCategory = propertyCategory;
        this.auction = false;
        this.empRole = empRole;
        if (propertyType == PropertyType.Rent) {
            this.rentalPrice = pricing;
            this.sellingPrice = 0;
        } else {
            this.rentalPrice = 0;
            this.sellingPrice = pricing;
        }

    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isAvailability() {
        return Availability;
    }

    public void setAvailability(boolean availability) {
        Availability = availability;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    public boolean isEmployeeAssigned(){
        return this.employeeId != null;
    }

    public EmployeeType getEmpRole() {
        return empRole;
    }

    public void setEmpRole(EmployeeType empRole) {
        this.empRole = empRole;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isPropertyTypeRental(){
        return propertyType == PropertyType.Rent;
    }
    public boolean isPropertyTypeSale(){
        return propertyType == PropertyType.Sale;
    }

    public boolean getAuctionStatus() {
        return auction;
    }

    public void setAuctionStatus(boolean auction) {
        this.auction = auction;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }
    public double getPropertyPrice(){
        if(this.sellingPrice >0){
            return sellingPrice;
        }
    return rentalPrice;
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

    public PropertyCategory getPropertyCategory() {
        return propertyCategory;
    }

    public void setPropertyCategory(PropertyCategory propertyCategory) {
        this.propertyCategory = propertyCategory;
    }

    @Override
    public String toString() {
        return "-----------------------Property Details------------------------" + '\n' +'\n'+
                "propertyId=" + getPropertyId() + '\n'+
                "propertyName=" + getPropertyName() + '\n' +
                "propertyType=" +propertyType + '\n' +
                "propertyAddress=" + getPropertyAddress() + '\n' +
                "Employee Assigned = "+isEmployeeAssigned()+'\n' +
                "minPrice=" + getMinPrice() + '\n'+
                "suburb=" + getSuburb() + '\n' +
                "propertyCategory="+ getPropertyCategory()+ '\n' +
                "bedroomCount=" + getBedroomCount() + '\n' +
                "bathroomCount=" + getBathroomCount() + '\n' +
                "parkingCount=" + getParkingCount() + '\n' +
                "sellingPrice=" + getSellingPrice() + '\n' +
                "rentalPrice=" + getRentalPrice()+ '\n';

    }
}
