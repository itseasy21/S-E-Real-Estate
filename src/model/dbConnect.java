package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import config.Config;

public class dbConnect {

    Connection conn = null;
    Config config = new Config();

    public Connection getConn(){
        try {
            // db parameters
            String url = "jdbc:sqlite:" + config.getDbName();
            // create a connection to the database
            this.conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.conn;
    }
}
