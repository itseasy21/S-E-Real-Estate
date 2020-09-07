package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SERException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class registerController extends baseController implements Initializable {

    @FXML
    private Pane logo;
    @FXML private TextField username, password;

    @FXML
    private void loginHandler(ActionEvent actionEvent) throws IOException, SERException.InputException {
        String check_user = username.getText();
        String check_pass = password.getText();

        if(model.isValidUser(check_user, check_pass)){
            System.out.println("valid user");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/activity_main.fxml"));
            Parent mainController = loader.load();
            Scene mainScene = new Scene(mainController);

            //Call controller and send username
            baseController controller = loader.getController();
            controller.initializeModel(check_user, this.model);

            Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            window.setTitle("S & E Real Estate");
            window.setScene(mainScene);
            window.show();

        }else{
            throw new SERException.InputException("Username You Entered is not Valid");
        }

    }

    @FXML
    private void registerHandler(ActionEvent actionEvent) throws IOException, SERException.InputException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/activity_register.fxml"));
        Parent mainController = loader.load();
        Scene mainScene = new Scene(mainController);

        //Call controller and send username
        baseController controller = loader.getController();
        controller.initializeModel("",this.model);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setTitle("S & E Real Estate");
        window.setScene(mainScene);
        window.show();

//    }else{
        throw new SERException.InputException("Username You Entered is not Valid");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}