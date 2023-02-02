package com.lap.roomplanningsystem.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class ProfilViewController extends BaseController{

    @FXML
    private Label authorizationLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label textLabel;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label usernameLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView imageView;

    @FXML
    private ImageView profilImage;

    @FXML
    private Label errorLabel;

    User user;

    FileChooser fileChooser = new FileChooser();
    byte[] bytes;



    @FXML
    void initialize() {
        if(model.getUser()!= null){
            setProfilImage(profilImage);
        }

        Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(user -> user.getId() == model.getUser().getId()).findAny();

        if (optionalUser.isPresent()) {
            user = optionalUser.get();

            firstnameLabel.setText(user.getFirstname());
            lastnameLabel.setText(user.getLastname());
            titleLabel.setText(!user.getTitle().equals("") ? user.getTitle() : "ohne");
            usernameLabel.setText(user.getUsername());
            authorizationLabel.setText(user.getAuthorization().equals("admin") ? "Administrator" : "Trainer");
            phoneLabel.setText(user.isPhoneVisable()? user.getPhone() : "nicht sichtbar");
            emailLabel.setText(user.isEmailVisable()? user.getEmail() : "nicht sichtbar");
            textLabel.setText(user.isTextVisable()? user.getText() : "nicht sichtbar");

            if (user.getPhoto() != null) {
                imageView.setImage(new Image(new ByteArrayInputStream(user.getPhoto())));
            }
        }
    }

    @FXML
    void onUpdateButtonClicked(ActionEvent actionEvent) throws IOException {
        showNewView(Constants.PATH_TO_EDIT_USER);
    }

    @FXML
    void onLogoutButtonClicked(ActionEvent actionEvent) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);
    }

    @FXML
    private void onUpdateImageClicked(MouseEvent mouseEvent) {
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        try{
            bytes = Files.newInputStream(Path.of(file.getAbsolutePath())).readAllBytes();
            user.setPhoto(bytes);
            boolean isChanged = updateProfilImageBYJDBC();

            if (isChanged) {
                imageView.setImage(new Image(new ByteArrayInputStream(bytes)));
            } else {
                errorLabel.setText(Constants.OVERSIZED_IMAGE);
            }
        } catch (Exception e){
            errorLabel.setText(Constants.NO_IMAGE_SELECTED);
        }

    }

    private boolean updateProfilImageBYJDBC() throws Exception {
        return Dataholder.userRepositoryJDBC.updateProfileImage(user, new ByteArrayInputStream(bytes));
    }

}


