package model;

public class UserException extends Throwable {
    private String message;
    public UserException(String messsage){
        this.message = messsage;
    }

    @Override
    public String toString() {
        return "UserException{" +
                "message='" + message + '\'' +
                '}';
    }
}
