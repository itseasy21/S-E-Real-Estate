package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Inspection {

    static int inspectionCounter = 0;

    private int propertyId;
    private String cid;
    private String eid;
    private String id,status;
    private String date,time;
    String[] timeslots1=new String[4];
    String[] Emptytimeslot=new String[1];
    String[] dates=new String[6];
    private String getdatesl,gettimesl;

    public Inspection(int propertyid, String eid, String getdateslot, String timeslots1, String status) {
        this.id = "INS" + (++inspectionCounter);
        this.propertyId = propertyid;
        this.cid = cid;
        this.eid = eid;
        this.date = date;
        this.time = time;
        this.dates= dates;
        this.status = status;
        this.getdatesl=getdateslot;
        this.gettimesl=timeslots1;
    }

    public Inspection(String id, int propertyid, String eid, String getdateslot, String timeslots1, String status) {
        this.id = id;
        ++inspectionCounter;
        this.propertyId = propertyid;
        this.cid = cid;
        this.eid = eid;
        this.date = date;
        this.time = time;
        this.dates= dates;
        this.status = status;
        this.getdatesl=getdateslot;
        this.gettimesl=timeslots1;
    }

    public void cancellInspection() throws PropertyException {
        System.out.println(getStatus());
        setStatus("cancelled");
        setDate(null);
        setTime(null);
        setTimeSlot(Emptytimeslot);
        setdatesSlot(Emptytimeslot);
    }

    public String getdatesl(){return this.getdatesl;}

    public String gettimesl(){return this.gettimesl;}

    public int getdatesize() { return dates.length; }

    public String getId() { return this.id; }

    public int getpId() { return this.propertyId; }

    public String getcId() { return this.cid; }

    public String geteId() { return this.eid; }

    public String getDate() { return this.date; }

    public String getTime() { return this.time; }

    public String getStatus() { return this.status; }

    public String getTimeslot() {
        String print = "";
        for(int i=0;i<timeslots1.length;i++) {
            print += "\n" + this.timeslots1[i];
        }
        return print; }



    public String getdateslot() {
        String print = "";
        for(int i=0;i< dates.length;i++) {
            print += "\n" + this.dates[i];
        }
        return print; }

    public String[] getdatearray(){
        for(int i=0;i<dates.length;i++) {

        }
        return dates;
    }

    public void setId(String id) { this.id = id; }

    public void setpid(int propertyI) { this.propertyId = propertyI; }

    public void setEid(String eid) { this.eid = eid; }

    public void setCid(String cid) { this.cid = cid; }

    public void setDate(String date) { this.date=date; }

    public void setTime(String time) { this.time=time; }

    public void setTimeSlot( String[] timeslots1) { this.timeslots1 = timeslots1; }

    public void setdatesSlot( String[] dates) { this.dates = dates; }


    public void setStatus(String status) throws PropertyException {
        if (status.equalsIgnoreCase("Created")||status.equalsIgnoreCase("null") || status.equalsIgnoreCase("scheduled") || status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("Cancelled")) {
            this.status = status;
        } else {
            throw new PropertyException("Invalid Inspection status.");
        }
    }

    public String showDetails() {
        String printDetails = "";

        printDetails += "Inspection ID:\t\t\t" + getId();
        printDetails += "\nProperty ID:\t\t\t" + getpId();
      //  printDetails += "\nCustomer ID:\t\t\t" + getcId();
        printDetails += "\nEmployee ID:\t\t\t" + geteId();
       // printDetails += "\nDate:\t\t\t" + getDate();
       //  printDetails += "\nTime:\t\t\t" + getTime();
        printDetails += "\nAvailable dates:\t\t\t" + getdatesl();
        printDetails += "\nAvailable Timeslot:\t\t\t" + gettimesl();
        printDetails += "\nStatus:\t\t\t\t\t" + getStatus();

        return printDetails;
    }
}