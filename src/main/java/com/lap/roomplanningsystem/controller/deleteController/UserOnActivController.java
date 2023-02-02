package com.lap.roomplanningsystem.controller.deleteController;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Optional;

public class UserOnActivController extends BaseController {

    @FXML
    private Label authoricationLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label usernameLabel;

    private User user;


    @FXML
    void initialize() {
        Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(u -> u.getId() == model.getSelectedUserProperty().getId()).findAny();

        if(optionalUser.isPresent()){
            user = optionalUser.get();

            firstnameLabel.setText(user.getFirstname());
            lastnameLabel.setText(user.getLastname());
            usernameLabel.setText(user.getUsername());
            authoricationLabel.setText(user.getAuthorization() == "admin" ? "Administrator" : "Trainer");
        }
    }

    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(firstnameLabel);
    }

    @FXML
    void onYesButtonClicked(ActionEvent event) throws Exception {
        model.setSelectedUserProperty(null);

        user.setActive(false);

        if(deActivateUserByJDBC()){
            int index = model.getDataholder().getUsers().indexOf(user);
            model.getDataholder().updateUser(index, user);
            model.getDataholder().updateEvents();
        }

        closeStage(firstnameLabel);
    }

    private boolean deActivateUserByJDBC() throws Exception {
        return Dataholder.userRepositoryJDBC.deActivateUser(user);
    }

}
