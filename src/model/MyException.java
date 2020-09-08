package model;

public class MyException extends Throwable{
    private String message;
    public MyException(String messsage){
        this.message = messsage;
    }

    @Override
    public String toString() {
        return "PayrollException{" +
                "message='" + message + '\'' +
                '}';
    }
}
