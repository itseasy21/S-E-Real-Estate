package model;

public class SERException extends Exception{
    private String message;
    public SERException(String messsage){
        this.message = messsage;
    }

    @Override
    public String toString() {
        return "SERException{" +
                "message='" + message + '\'' +
                '}';
    }
}
