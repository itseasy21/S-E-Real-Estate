package model;

import java.util.ArrayList;
import java.util.HashMap;

public class mainModel {

    public static ArrayList<User> userDB = new ArrayList<User>();
//    public static ArrayList<Post> tempPostDB = new ArrayList<Post>();
    public static HashMap<String, Property> propertyDB = new HashMap<>();

    public mainModel() {
//        dbConnect dbHandler = new dbConnect();
//        this.conn = dbHandler.getConn();
    }

    public void syncDB() {
        //TODO syncronize data in memory with data from sqliteDB
    }

    public boolean isValidUser(String check_user) {
        return true;
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
