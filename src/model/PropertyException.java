package model;

public class PropertyException extends Exception {
    private String message;
    public PropertyException(String messsage){
        this.message = messsage;
    }

    @Override
    public String toString() {
        return "PropertyException{" +
                "message='" + message + '\'' +
                '}';
    }
}
