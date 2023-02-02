package com.lap.roomplanningsystem.controller;

import java.sql.SQLException;

import com.lap.roomplanningsystem.app.Constants;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class LoginViewController extends BaseController{

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private TextField textFieldUsername;
    @FXML
    private Label errorLabel;
    @FXML
    private VBox loginView;


    @FXML
    void initialize() {

        loginView.sceneProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                loginView.requestFocus();
            }
        });

        loginView.setOnKeyPressed(this::onKeyPressed);



    }

    private void onKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            try {
                checkLogin();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onLoginButtonClicked(MouseEvent event) {
        try {
            checkLogin();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkLogin() throws SQLException {
        if(validateFields()){
            boolean loginResult = model.validateLogin(textFieldUsername.getText(), textFieldPassword.getText());

            if(!loginResult){
                errorLabel.setText(Constants.NO_CORRECT_LOGINDATA);
            } else {
                model.setPathToView(Constants.PATH_TO_HOME_VIEW);
            }
        } else {
            errorLabel.setText(Constants.MISSING_LOGINDATA);
        }
    }

    private boolean validateFields(){
        return !emptyFields();
    }

    private boolean emptyFields() {
        return textFieldUsername.getText().isBlank() && textFieldPassword.getText().isBlank();
    }

}