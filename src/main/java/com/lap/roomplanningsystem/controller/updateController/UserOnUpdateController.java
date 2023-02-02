package com.lap.roomplanningsystem.controller.updateController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.converter.BooleanConverter;
import com.lap.roomplanningsystem.model.*;
import com.lap.roomplanningsystem.utility.ListUtility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class UserOnUpdateController extends BaseController {

    @FXML
    private ComboBox<Boolean> activComboBox;

    @FXML
    private ComboBox<String> authorizationCombobox;


    @FXML
    private TextField emailInput;

    @FXML
    private ComboBox<Boolean> emailVisableComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField firstnameInput;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField lastnameInput;

    @FXML
    private Label numberLabel;

    @FXML
    private TextField phoneInput;

    @FXML
    private ComboBox<Boolean> phoneVisableComboBox;

    @FXML
    private ComboBox<Boolean> photoVisableComboBox;

    @FXML
    private TextArea textInput;

    @FXML
    private ComboBox<Boolean> textVisableComboBox;

    @FXML
    private TextField titleInput;

    @FXML
    private ComboBox<Boolean> coachComboBox;

    @FXML
    private TextField usernameInput;

    private User user;

    FileChooser fileChooser = new FileChooser();
    InputStream inputStream = null;
    byte[] photo = null;



    @FXML
    void initialize() {

        Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(user -> user == model.getSelectedUserProperty()).findAny();

        if(optionalUser.isPresent()){
            user = optionalUser.get();

            initFields();
        }

    }

    private void initFields(){
        numberLabel.setText("B" + String.valueOf(user.getId()));
        firstnameInput.setText(user.getFirstname());
        lastnameInput.setText(user.getLastname());
        titleInput.setText(user.getTitle());
        usernameInput.setText(user.getUsername());
        phoneInput.setText(user.getPhone());
        emailInput.setText(user.getEmail());
        textInput.setText(user.getText());
        imageView.setImage(user.getPhoto() != null? new Image(new ByteArrayInputStream(user.getPhoto())): null);
        activComboBox.getSelectionModel().select(user.isActive());
        coachComboBox.getSelectionModel().select(user.isTrainer());
        textVisableComboBox.getSelectionModel().select(user.isTextVisable());
        emailVisableComboBox.getSelectionModel().select(user.isEmailVisable());
        phoneVisableComboBox.getSelectionModel().select(user.isPhoneVisable());
        photoVisableComboBox.getSelectionModel().select(user.isPhotoVisable());
        authorizationCombobox.getSelectionModel().select(user.getAuthorization().equals("admin") ? "Administrator" : "Trainer");

        ObservableList<Boolean> booleanList = ListUtility.booleanList();

        activComboBox.setItems(booleanList);
        coachComboBox.setItems(booleanList);
        textVisableComboBox.setItems(booleanList);
        emailVisableComboBox.setItems(booleanList);
        phoneVisableComboBox.setItems(booleanList);
        photoVisableComboBox.setItems(booleanList);
        authorizationCombobox.setItems(ListUtility.authorizationList());

        BooleanConverter.setConverter(activComboBox);
        BooleanConverter.setConverter(coachComboBox);
        BooleanConverter.setConverter(textVisableComboBox);
        BooleanConverter.setConverter(emailVisableComboBox);
        BooleanConverter.setConverter(phoneVisableComboBox);
        BooleanConverter.setConverter(photoVisableComboBox);
    }


    @FXML
    void onUpdateImageButtonClicked(ActionEvent event){
        File file = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        try{
            inputStream = new FileInputStream(file);
            imageView.setImage(new Image(inputStream));
            photo = Files.newInputStream(Path.of(file.getAbsolutePath())).readAllBytes();
        } catch (Exception e){
            System.out.println(Constants.NO_IMAGE_SELECTED);
        }
    }

    @FXML
    void onChangePasswordButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_CREATE_PASSWORD_VIEW);
    }



    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        if(validateFields()){
            updateUser();

            if(photo != null){
                user.setPhoto(photo);
            }

            boolean isUpdated = updateUserByJDBC();

            if (isUpdated) {
                showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                int index = model.getDataholder().getUsers().indexOf(user);
                model.getDataholder().updateUser(index, user);

                if(user.getId() == model.getUser().getId()){
                    model.setUser(user);
                }

                model.getDataholder().updateEvents();
                closeStage(errorLabel);
            }


        }
    }

    private void updateUser(){
        String authorize = authorizationCombobox.getValue().equals("Administrator") ? "admin" : "coach";
        user.setFirstname(firstnameInput.getText());
        user.setLastname(lastnameInput.getText());
        user.setActive(activComboBox.getValue());
        user.setTitle(titleInput.getText());
        user.setUsername(usernameInput.getText());
        user.setAuthorization(authorize);
        user.setTrainer(coachComboBox.getValue());
        user.setText(textInput.getText());
        user.setTextVisable(textVisableComboBox.getValue());
        user.setEmail(emailInput.getText());
        user.setEmailVisable(emailVisableComboBox.getValue());
        user.setPhone(phoneInput.getText());
        user.setPhoneVisable(phoneVisableComboBox.getValue());
        user.setPhotoVisable(photoVisableComboBox.getValue());

    }

    private boolean validateFields() {
        return !emptyFields() && explicitDescription();
    }

    private boolean explicitDescription() {
        List<User> users = model.getDataholder().getUsers().stream().filter(u-> u !=user).toList();
        boolean explicit = users.stream().noneMatch(r-> r.getUsername().equals(usernameInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.USERNAME_NOT_ALLOWED);
        }

        return explicit;
    }


    private boolean emptyFields() {
        boolean empty = isBlank(firstnameInput.getText()) || isBlank(lastnameInput.getText()) || isBlank(usernameInput.getText()) ||
                authorizationCombobox.getValue() == null;

        if(empty){
            errorLabel.setText(Constants.EMPTY_OBLIGATORY_FIELDS);
        }

        return empty;
    }


    private boolean updateUserByJDBC() throws SQLException, IOException {
        return Dataholder.userRepositoryJDBC.update(user, model.getHashedPassword());
    }
}
