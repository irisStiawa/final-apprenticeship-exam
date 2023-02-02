package com.lap.roomplanningsystem.controller;

import java.io.IOException;

import com.lap.roomplanningsystem.app.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HomeViewController extends BaseController{


    @FXML
    private ImageView profilImage;
    @FXML
    private Button loginButton;



    @FXML
    void initialize() {
        assert profilImage != null : "fx:id=\"profilImage\" was not injected: check your FXML file 'home-view.fxml'.";

        if(model.getUser() != null){
            isLogged();
        }
    }



    private void isLogged() {
        loginButton.setText("Logout");
        loginButton.setOnAction(this::onLogoutButtonClicked);
        setProfilImage(profilImage);
    }

    @FXML
    void onLogoutButtonClicked(ActionEvent event) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_CALENDAR_VIEW);
    }

    @FXML
    void onProfilIconClicked(MouseEvent event) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }

    }

    @FXML
    private void onLoginButtonClicked(ActionEvent actionEvent) {
        model.setPathToView(Constants.PATH_TO_LOGIN_VIEW);
    }

    @FXML
    private void onContractButtonClicked(ActionEvent actionEvent) throws IOException {
        if(!model.isDetailView()){
            showNewView(Constants.PATH_TO_ADD_CONTRACT_VIEW);
        }
    }


}
