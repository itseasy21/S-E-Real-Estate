package model;
import java.text.SimpleDateFormat;
import java.util.*;

public class Inspectionmain {

    String[] dates=new String[6];
    String[] timeslots1=new String[4];
    String[] Emptytimeslot=new String[1];
    Inspection a;
   // String[] iArray;
    public static HashMap<String, Inspection> inspectionDB = new HashMap<String, Inspection>();
  //  ArrayList<Inspection> inspection = new ArrayList<Inspection>();

    public void createInspection(Property pid,Inspection a) throws PropertyException {
        int propertyid=pid.getPropertyId();
        String id="I" +1;
        a.setId(a.getId());
       // a.setpid(propertyid);
        a.setpid(1);
        //a.setEid(pid.getEmployeeId());
        a.setEid(1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < a.getdatesize(); i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            String newDate = sdf.format(cal.getTime());
            dates[i] = newDate;
        }

        a.setdatesSlot(dates);
       // System.out.println(a.getdateslot());
        Scanner sc = new Scanner(System.in);
        //System.out.println("create 5 time slots");
        //for(int i=0;i<timeslots1.length;i++){
            String[] timeslots1 = {"10:00am", "10:30am", "11:00am", "11:30am", "12:00pm"}; //for test case
            //timeslots1[i]=sc.nextLine();
            for(int i=0;i<timeslots1.length;i++) {
               // System.out.println("timeslot" + timeslots1[i]);
                a.setTimeSlot(timeslots1);
            }
       // }
        a.setStatus("Created");
        inspectionDB.put(id,a);
        //System.out.println(inspectionDB.values());


      //  iArray = new String[]{id, , String.valueOf(a.geteId()), String.valueOf(a.getdateslot()), a.getTimeslot()};
        //Inspection i=new Inspection(id,propertyid,a.geteId(),dates, timeslots1);
       // inspection.add(i);
       // inspection.add(i);
        /*for(int j=0;j<inspection.size();j++){
            System.out.println(inspection.get(j).getId());
            System.out.println(inspection.get(j).getpId());
            System.out.println(inspection.get(j).geteId());
            if(!inspection.get(j).getId().isEmpty()){
                System.out.println(inspection.get(j).getpId()); }
        }*/
     //   System.out.println(inspectionDB.get();

     //   for (Inspection ignored :inspectionDB.values()){
       // System.out.println("Value: "+ inspectionDB.values());
        //}


    }



    public void bookInspection(Customer cid,Inspection a) throws PropertyException {
        //String[] timeslots = {"10:00am", "10:30am", "11:00am", "11:30am", "12:00pm"};
         if(a.getStatus().equalsIgnoreCase("created")) {
        int c = cid.getId();
        a.setCid(c);
        Scanner sc = new Scanner(System.in);
        System.out.println("BOOK AN INSPECTION");
        System.out.println("-----------------------------------------");
        boolean loop = true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < a.getdatesize(); i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            String newDate = sdf.format(cal.getTime());
            dates[i] = newDate;
        }
        do {
            System.out.println("available dates for inspection are:");
         //   System.out.println(dates.length);
            for (int i = 0; i < dates.length; i++) {
                System.out.println(dates[i]);
            }
            System.out.println("select the date you want to book an inspection");
            //String selectdate = sc.next();   //input from customer
            String selectdate = dates[1];
            System.out.println(selectdate);

            for (int i = 0; i < dates.length; i++) {

                if (dates[i].equalsIgnoreCase(selectdate)) {
                    a.setDate(dates[i]);
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


        String[] timeslots1 = {"10:00am", "10:30am", "11:00am", "11:30am", "12:00pm"}; //for test case
        loop = true;
        do {
            System.out.println("available time slots for inspection are:");
           // for (int i = 0; i < a.getTimeslot().length(); i++) {
                System.out.println(a.getTimeslot());
            //}
            System.out.println("select one of the available Time slots for the inspection");
            //String selecttime = sc.next();
            String selecttime = timeslots1[1];
            System.out.println(selecttime);
            for (int i = 0; i < timeslots1.length; i++) {
                if (timeslots1[i].equalsIgnoreCase(selecttime)) {
                    a.setTime(timeslots1[i]);
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
        System.out.println("inspection time and date set sucessfully for " + a.getDate() + " at " + a.getTime());
        a.setStatus("Scheduled");
        }
        else{
           System.out.println("no inspection available for this property");
         }
    }

    public void cancellInspection(Inspection a) throws PropertyException {
        System.out.println("Inspection id: "+a.getId());
        System.out.println("Initial status : "+a.getStatus());
        System.out.println("Cancelling inspection.....\n");
        a.setStatus("cancelled");
        a.setDate(null);
        a.setTime(null);
        a.setTimeSlot(Emptytimeslot);
        a.setdatesSlot(Emptytimeslot);
    }




}