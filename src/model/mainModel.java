package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class mainModel {

    public static ArrayList<User> userDB = new ArrayList<User>();
    public static HashMap<Integer, Property> propertyDB;
    Connection conn;

    public mainModel() {
        dbConnect dbHandler = new dbConnect();
        this.conn = dbHandler.getConn();
        this.propertyDB = new HashMap<>();
    }

    public void syncDB() {
        //TODO syncronize data in memory with data from sqliteDB
    }

    public boolean isValidUser(String check_user, String check_pass) {
        try {
            Statement stmt = this.conn.createStatement();
            String loginQuery = "select count(*) from Customer where email = '"+ check_user +"' and password='"+ check_pass +"'";
            ResultSet rsLogin = stmt.executeQuery(loginQuery);
            rsLogin.next();
            int count = rsLogin.getInt(1);
            rsLogin.close();

            if(count == 1){
                return true;
            }else{
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public void addProperty(Property property){
        int propertyId = propertyDB.size() + 1;
        if(property.getPropertyId() == 0)
            property.setPropertyId(propertyId);
        propertyDB.put(propertyId,property);
    }

    public Property listProperty(String propertyId) throws PropertyException {
        if(propertyDB.containsKey(propertyId)){
            return propertyDB.get(propertyId);
        } else {
            throw new PropertyException("Invalid property ID");
        }

    }
    public void listPropertiesForSale() {
        try {
            System.out.println(" ");
            System.out.println("***********Properties For Sale*********");
            System.out.println(" ");
            //lists only properties that are assigned with single employee
            propertyDB.values()
                    .stream()
                    .filter(e -> e.isPropertyTypeSale() && e.isEmployeeAssigned())
                    .forEach(p -> System.out.println(p.toString()));
        } catch(NullPointerException e){
            System.out.println("None");
           return;
        }
    }

    public void listPropertiesForRent() {
        try {
            System.out.println(" ");
            System.out.println("******Properties For Rent*******:");
            System.out.println(" ");
            propertyDB.values()
                    .stream()
                    .filter(i -> i.isPropertyTypeRental() && i.isEmployeeAssigned())
                    .forEach(p -> System.out.println(p.toString()));
        } catch(NullPointerException e){
            System.out.println("None");
            return;
        }
    }
    public void listUnassignedProperties() {
        try {
            System.out.println(" ");
            System.out.println("*********Unassigned Properties*******:");
            propertyDB.values()
                    .stream()
                    .filter(i -> !i.isEmployeeAssigned())
                    .forEach(p -> System.out.println(p.toString()));
        } catch(NullPointerException e){
            System.out.println("None");
            return;
        }
    }
    public void listProperties(){
        listPropertiesForSale();
        listPropertiesForRent();
        listUnassignedProperties();
    }

   public boolean isPropertyDBEmpty(){
        return propertyDB.isEmpty();
   }
   public int getPropertyDBSize(){
        return propertyDB.size();
   }
}
