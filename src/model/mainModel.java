package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class mainModel {

    public static ArrayList<User> userDB = new ArrayList<User>();
    Statement stmt = null;
    Connection conn = null;
    public static HashMap<String, Property> propertyDB = new HashMap<>();

    public mainModel() {
        dbConnect dbHandler = new dbConnect();
        this.conn = dbHandler.getConn();
    }

    public void syncDB() {
        //TODO syncronize data in memory with data from sqliteDB
    }

    public boolean isValidUser(String check_user) {
        try {
            stmt = this.conn.createStatement();
            String loginQuery = "select count(*) from user where username = '"+ check_user +"'";
            ResultSet rsLogin = stmt.executeQuery(loginQuery);
            rsLogin.next();
            int count = rsLogin.getInt(1);
            rsLogin.close();

            if(count == 1){
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
    public void addProperty(Property property){
        String propertyId = "P"+(propertyDB.size() + 1);
        propertyDB.put(propertyId,property);
    }
    public void listProperty(String propertyId) throws PropertyException {
        if(propertyDB.containsKey(propertyId)){
            propertyDB.get(propertyId).toString();
        } else {
            throw new PropertyException("Invalid property ID");
        }

    }

}
