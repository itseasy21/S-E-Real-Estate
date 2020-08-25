package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.mainModel;

import java.io.IOException;

public class baseController {
    mainModel model;
    String currentUser;

    public void initializeModel(String username, mainModel model) {
        this.currentUser = username;
        this.model = model;
    }

    public void switchStage(String name, ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
        Parent loadController = loader.load();

        baseController controller = loader.getController();
        controller.initializeModel(this.currentUser, this.model);
        Scene mainScene = new Scene(loadController);

        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }
}
