package model;

public class UserException extends Throwable {
    private String message;
    public UserException(String messsage){
        this.message = messsage;
    }

    @Override
    public String toString() {
        return "One or more Error Occurred:\n" + message;
    }
}
