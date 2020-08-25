package model;

import java.util.ArrayList;

public class mainModel {

    public static ArrayList<User> userDB = new ArrayList<User>();
//    public static ArrayList<Post> tempPostDB = new ArrayList<Post>();

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
}
