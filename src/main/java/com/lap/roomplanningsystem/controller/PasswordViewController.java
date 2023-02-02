package com.lap.roomplanningsystem.controller;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.app.Password;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class PasswordViewController extends BaseController{

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private PasswordField passwordInput2;



    @FXML
    void initialize() {
    }


    @FXML
    void onSaveButtonClicked(ActionEvent event) {
        if(validateFields()){
            model.setHashedPassword(Password.hash(passwordInput.getText()));
            closeStage(errorLabel);
        }
    }


    private boolean validateFields() {
        return !emptyFields() && replicatePassword() && validatePassword() ;
    }

    private boolean validatePassword() {
        boolean valid = Password.validate(passwordInput.getText());

        if(!valid){
            errorLabel.setText(Constants.NOT_AVAILABLE_PASSWORD);
        }

        return valid;
    }


    private boolean replicatePassword() {
        boolean replicate = passwordInput.getText().equals(passwordInput2.getText());

        if(!replicate){
            errorLabel.setText(Constants.REPEAT_PASSWORD);
        }

        return replicate;
    }

    private boolean emptyFields() {
        boolean empty = isBlank(passwordInput.getText()) || isBlank(passwordInput2.getText());

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS);
        }

        return empty;
    }

}
