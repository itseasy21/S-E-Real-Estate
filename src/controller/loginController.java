package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.SERException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController extends baseController implements Initializable {

    @FXML private TextField username;

    @FXML
    private void loginHandler(ActionEvent actionEvent) throws IOException, SERException.InputException {
        String check_user = username.getText();

        if(check_user.startsWith("s") && model.isValidUser(check_user)){
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
            throw new SERException.InputException("Username You Entered is not Vaild");
            //System.out.println("invalid user");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
