package com.lap.roomplanningsystem.controller.detailController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UserDetailViewController extends BaseController {

    @FXML
    private Label activLabel;

    @FXML
    private Label authorizationLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private Label mailVisableLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label phoneVisableLabel;

    @FXML
    private Label photoVisableLabel;

    @FXML
    private Label textLabel;

    @FXML
    private Label textVisableLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label coachLabel;

    @FXML
    private Label usernameLabel;

    private User user;



    @FXML
    void initialize() {

        Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(user -> user == model.getSelectedUserProperty()).findAny();

        if(optionalUser.isPresent()) {
            user = optionalUser.get();

            firstnameLabel.setText(user.getFirstname());
            lastnameLabel.setText(user.getLastname());
            titleLabel.setText(user.getTitle());
            activLabel.setText(user.isActive() ? "aktiv" : "inaktiv");
            authorizationLabel.setText(user.getAuthorization().equals("admin") ? "Administator" : "Trainer");
            usernameLabel.setText(user.getUsername());
            coachLabel.setText(user.isTrainer() ? "ja" : "nein");
            textLabel.setText(user.getText());
            textVisableLabel.setText(user.isTextVisable() ? "ja" : "nein");
            phoneLabel.setText(user.getPhone());
            phoneVisableLabel.setText(user.isPhoneVisable() ? "ja" : "nein");
            mailLabel.setText(user.getEmail());
            mailVisableLabel.setText(user.isEmailVisable() ? "ja" : "nein");
            photoVisableLabel.setText(user.isPhotoVisable() ? "ja" : "nein");

            if (user.getPhoto() != null) {
                imageView.setImage(new Image(new ByteArrayInputStream(user.getPhoto())));
            }
        }
    }


    @FXML
    void  onActivButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_USER_ON_ACTIV_VIEW);
        closeStage(numberLabel);

    }

    @FXML
    void onUpdateButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_USER_UPDATE_VIEW);
        closeStage(numberLabel);
    }


}
