package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.mainModel;

import java.io.IOException;
import java.sql.SQLException;
import controller.*;

public class mainLauncher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws SQLException {

        //load all data from DB and import into memory
        mainModel main = new mainModel();
        main.syncDB();

        loginHandler(stage, main);
    }

    private void loginHandler(Stage stage, mainModel model) {
        try {
            // get a reference to the loader
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/activity_login.fxml"));

            // load the fxml file
            Parent root = loader.load();

            // loading like this, we can get access to the controller

            loginController controller = loader.getController();
            controller.initializeModel("",model);

            Scene scene = new Scene(root, 747, 349);
            stage.setTitle("Login - S&E Real Estate");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
