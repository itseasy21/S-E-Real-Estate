package model;

import java.util.Scanner;

public class Rent {

    /*property_id,vendor id,employee id,min reserve price*/
    String id, eid;
    int cid,pid;
    double price;
    String status;
    double min_price;
    String landlordId;

    public Rent(String id,String eid,int pid, int cid,String landlordId,double min_price, double price)
    {
        this.id=id;
        this.eid=eid;
        this.pid=pid;
        this.price=price;
        this.cid=cid;
        this.landlordId=landlordId;
        this.min_price=min_price;
        this.status="Available";
    }

    public void createRent(Property p,Customer c) throws PropertyException {
        int propertyid=p.getPropertyId();
        if(p.isPropertyTypeRental()==true) {
            String id="R" +1;
            setId(id);
            setpid(propertyid);
            setEid(String.valueOf(p.getEmployeeId()));
            setMinPrice(p.getMinPrice());
          //  setLandlordIdid();
            setStatus("Available");
        }
        else{
            System.out.println("property is not listed for rent");

        }
    }

    public void changeStatus() throws PropertyException {
        setStatus("Rented");
    }

    public void setPrice(Double price) throws PropertyException {
        if(price>this.min_price) {
            this.price = price;
        } else{
            throw new PropertyException("Invalid rental property status.");
        }
    }

    public String getId() { return this.id; }

    public int getPropertyId() { return this.pid; }

    public String getEmployeeId() { return this.eid; }

    public String getLandlordId() { return this.landlordId; }

    public double getMinPrice() { return this.min_price; }

    public double getPrice() { return this.price; }

    public String getStatus() { return this.status; }

    public void setId(String id) { this.id = id; }

    public void setpid(int propertyI) { this.pid = propertyI; }

    public void setEid(String eid) { this.eid = eid; }

    public void setCid(int cid) { this.cid = cid; }

    public void setLandlordIdid(String landlordId) { this.landlordId = landlordId; }

    public void setStatus(String status) throws PropertyException {
        if(status.equalsIgnoreCase("Available")||status.equalsIgnoreCase("Rented")) {
            this.status = status;
        } else{
            throw new PropertyException("Invalid rental property status.");
        }
    }

    public void setMinPrice(Double minprice){
         this.min_price=minprice;

    }



    public String showDetails(){
        String printDetails = "";

        printDetails += "\nProperty ID:\t"+getPropertyId();
        printDetails += "\nEmployee ID:\t"+getEmployeeId();
        printDetails += "\nMinimum Price:\t"+getMinPrice();
        printDetails += "\nPrice:\t"+getPrice();
        printDetails += "\nStatus:\t"+getStatus();

        return printDetails;

    }

  /*  public static void main(String args[]) throws PropertyException {

        Rent r = new Rent(1,2,3,300.00,500.00);
        System.out.println(r.getPropertyId());
        System.out.println(r.showDetails());

        r.setPrice(600.0);
        r.changeStatus();
        System.out.println(r.getStatus());
    }
*/
}


