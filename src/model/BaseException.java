package model;

public class BaseException extends RuntimeException{
    private String message;

    public BaseException(String msg)
    {
        this.message = msg;
    }
    //Message can be retrieved using this accessor method
    public String getMessage() {
        return message;
    }

}
