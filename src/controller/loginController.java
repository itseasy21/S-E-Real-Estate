package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.SERException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController extends baseController implements Initializable {

    @FXML private Pane logo;
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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        @FXML private Pane logo;
////        Config config = new Config();
//        logo.setStyle("-fx-background-color: linear-gradient(to bottom right, derive(goldenrod, 20%), derive(goldenrod, -40%));");
//        ImageView iv1 = new ImageView(new Image("https://i.imgur.com/cXIVXzA.png"));  // Creative commons with attribution license for icons: No commercial usage without authorization. All rights reserved. Design (c) 2008 - Kidaubis Design http://kidaubis.deviantart.com/  http://www.kidcomic.net/ All Rights of depicted characters belong to their respective owners.
//        ImageView iv2 = new ImageView(new Image("http://icons.iconarchive.com/icons/kidaubis-design/cool-heroes/128/Starwars-Stormtrooper-icon.png"));
//        iv1.relocate(10, 10);
//        iv2.relocate(80, 60);
//        logo.getChildren().addAll(iv1, iv2);

    }
}
