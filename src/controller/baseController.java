package controller;

import model.mainModel;

import java.io.IOException;

public class baseController {
    mainModel model;
    String currentUser;

    public void initializeModel(String username, mainModel model) {
        this.currentUser = username;
        this.model = model;
    }
}
