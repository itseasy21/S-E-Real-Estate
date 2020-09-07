package config;

public class Config {

    String dir = System.getProperty("user.dir");
    String dbName = "/sereal.db";

    public String getDir() {
        return this.dir;
    }

    public String getDbName() {
        return getDir() + this.dbName;
    }
}
