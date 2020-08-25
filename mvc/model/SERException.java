package model;

import javafx.scene.control.Alert;

public class SERException {
    public static class InputException extends BaseException
    {

        public InputException(String msg) {
            super(msg);
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText(msg);
            a.setTitle("Invaild");
            a.showAndWait();

        }
    }




}
