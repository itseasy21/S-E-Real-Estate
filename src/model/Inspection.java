package model;
import java.util.Date;

public class Inspection {

    private int id, propertyId, cid, eid;
    private String status;
    private Date date;

    public Inspection(int id, int propertyId, int cid, int eid, Date date) {
        this.id = id;
        this.propertyId = propertyId;
        this.cid = cid;
        this.eid = eid;
        this.date = date;
        this.status="Scheduled";
    }

    public void cancellInspection() throws PropertyException {
        setStatus("cancelled");
    }


    public int getId() { return this.id; }

    public int getpId() { return this.propertyId; }

    public int getcId() { return this.cid; }

    public int geteId() { return this.eid; }

    public Date getDate() { return this.date; }

    public String getStatus() { return this.status; }

    public void setDate(Date date) { date = this.date; }

    public void setStatus(String status) throws PropertyException {
        if(status.equalsIgnoreCase("scheduled")||status.equalsIgnoreCase("Completed")||status.equalsIgnoreCase("Cancelled")) {
            this.status = status;
        } else{
            throw new PropertyException("Invalid Inspection status.");
        }
    }

    public String showDetails(){
        String printDetails = "";

        printDetails += "Inspection ID:\t"+getId();
        printDetails += "\nProperty ID:\t"+getpId();
        printDetails += "\nCustomer ID:\t"+getcId();
        printDetails += "\nEmployee ID:\t"+geteId();
        printDetails += "\nDate and Time:\t"+getDate();
        printDetails += "\nStatus:\t"+getStatus();

        return printDetails;

    }

    /*public static void main(String args[]) throws PropertyException {

       Inspection i = new Inspection(1,123,1,1,new Date());
       System.out.println(i.getId());
       System.out.println(i.getDate());
       System.out.println(i.showDetails());
       i.cancellInspection();
       System.out.println(i.getStatus());
    }*/
}