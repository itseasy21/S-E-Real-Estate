package model;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Inspection {

    private int propertyId, cid, eid;
    private String id,status;
    private String date,time;
    String[] timeslots1=new String[4];
    String[] Emptytimeslot=new String[1];
    String[] dates=new String[6];

    public Inspection(String id, int propertyid, int eid, String getdateslot, String timeslots1, String status) {
        this.id = id;
        this.propertyId = propertyId;
        this.cid = cid;
        this.eid = this.eid;
        this.date = date;
        this.time = time;
        this.dates= dates;
        this.status = null;
    }

/*
    public Inspection(String id) {
        this.id = id;
        this.propertyId = propertyId;
        this.cid = cid;
        this.eid = eid;
        this.date = date;
        this.time = time;
        this.dates= dates;
        this.timeslots1=timeslots1;
        this.status = null;
    }
*/


    public void createInspection(Property pid) throws PropertyException {
        int propertyid=pid.getPropertyId();
        setpid(propertyid);
        //setEid(pid.getEmployeeId());
        setEid(1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < dates.length; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            String newDate = sdf.format(cal.getTime());
            dates[i] = newDate;

        }
        setdatesSlot(dates);
        Scanner sc = new Scanner(System.in);
        //System.out.println("create 5 time slots");
        //for(int i=0;i<timeslots1.length;i++){
        String[] timeslots1 = {"10:00am", "10:30am", "11:00am", "11:30am", "12:00pm"}; //for test case
        //timeslots1[i]=sc.nextLine();
        for(int i=0;i<timeslots1.length;i++) {
            // System.out.println("timeslot" + timeslots1[i]);
            setTimeSlot(timeslots1);
        }
        // }
        setStatus("Created");

    }


    public void bookInspection(Customer cid) throws PropertyException {
        //String[] timeslots = {"10:00am", "10:30am", "11:00am", "11:30am", "12:00pm"};
        // if(this.getStatus().equalsIgnoreCase("created")) {
        int c = cid.getId();
        setCid(c);
        Scanner sc = new Scanner(System.in);
        System.out.print("BOOK AN INSPECTION");
        System.out.print("-----------------------------------------");
        boolean loop = true;
        do {

            System.out.println("\navailable dates for inspection are:");
            System.out.println(dates.length);
            for (int i = 0; i < dates.length; i++) {
                System.out.println(dates[i]);
            }
            System.out.println("select the date you want to book an inspection");
            //String selectdate = sc.next();
            String selectdate = dates[1];
            System.out.println(selectdate);
            System.out.println(dates);
            for (int i = 0; i < dates.length; i++) {

                if (dates[i].equalsIgnoreCase(selectdate)) {
                    setDate(dates[i]);
                    loop = false;
                    break;
                } else {
                    loop = true;
                }
            }
            if (loop == true) {
                System.out.println("incorrect date entered...");
            }
        } while (loop == true);

        loop = true;
        do {
            System.out.println("available time slots for inspection are:");
            for (int i = 0; i < timeslots1.length; i++) {
                System.out.println(timeslots1[i]);
            }
            System.out.print("select one of the available Time slots for the inspection");
            String selecttime = sc.next();
            for (int i = 0; i < timeslots1.length; i++) {
                if (timeslots1[i].equalsIgnoreCase(selecttime)) {
                    setTime(timeslots1[i]);
                    loop = false;
                    break;
                } else {
                    loop = true;
                }
            }
            if (loop == true) {
                System.out.println("incorrect time slot entered...");
            }
        } while (loop == true);
        System.out.println("inspection time and date set sucessfully for " + getDate() + "at" + getTime());
        setStatus("Scheduled");
        //  }
        //else{
        //   System.out.println("no inspection available for this property");
        // }
    }

    public void cancellInspection() throws PropertyException {
        System.out.println(getStatus());
        setStatus("cancelled");
        setDate(null);
        setTime(null);
        setTimeSlot(Emptytimeslot);
        setdatesSlot(Emptytimeslot);
    }

    public int getdatesize() { return dates.length; }

    public String getId() { return this.id; }

    public int getpId() { return this.propertyId; }

    public int getcId() { return this.cid; }

    public int geteId() { return this.eid; }

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

    public void setEid(int eid) { this.eid = eid; }

    public void setCid(int cid) { this.cid = cid; }

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

        printDetails += "Inspection ID:\t" + getId();
        printDetails += "\nProperty ID:\t" + getpId();
        printDetails += "\nCustomer ID:\t" + getcId();
        printDetails += "\nEmployee ID:\t" + geteId();
        printDetails += "\nDate:\t" + getDate();
        printDetails += "\nTime:\t" + getTime();
        printDetails += "\nAvailable dates:\t" + getdateslot();
        printDetails += "\nTimeslot:\t" + getTimeslot();

        printDetails += "\nStatus:\t" + getStatus();

        return printDetails;
    }
}