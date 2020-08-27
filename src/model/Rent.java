package model;

public class Rent {

    /*property_id,vendor id,employee id,min reserve price*/
    int pid;
    int eid;
    double price;
    String status;
    double min_price;

    public Rent(int id,int pid,double min_price, double price)
    {
        this.eid=id;
        this.pid=pid;
        this.price=price;
        this.min_price=min_price;
        this.status="Available";
    }

    public void changeStatus() throws PropertyException {
        setStatus("Rented");
    }

    public int getPropertyId() { return this.pid; }

    public int getEmployeeId() { return this.eid; }

    public double getMinPrice() { return this.min_price; }

    public double getPrice() { return this.price; }

    public String getStatus() { return this.status; }

    public void setStatus(String status) throws PropertyException {
        if(status.equalsIgnoreCase("Available")||status.equalsIgnoreCase("Rented")) {
            this.status = status;
        } else{
            throw new PropertyException("Invalid rental property status.");
        }
    }


    public void setPrice(Double price) throws PropertyException {
        if(price>min_price) {
            this.price = price;
        } else{
            throw new PropertyException("Invalid rental property status.");
        }
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

    public static void main(String args[]) throws PropertyException {

        Rent r = new Rent(2,3,300.00,500.00);
        System.out.println(r.getPropertyId());
        System.out.println(r.showDetails());

        r.setPrice(600.0);
        r.changeStatus();
        System.out.println(r.getStatus());
    }

}


