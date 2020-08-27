package model;

public class PayrollException extends Throwable{
    private String message;
    public PayrollException(String messsage){
        this.message = messsage;
    }

    @Override
    public String toString() {
        return "PayrollException{" +
                "message='" + message + '\'' +
                '}';
    }
}
