package com.lap.roomplanningsystem.controller.addController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.app.Password;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.converter.BooleanConverter;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.User;
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
import javafx.stage.FileChooser;

/**
 * Die Klasse UserOnAddController ist eine Kindklasse des BaseController.
 * Kontrollerfunktionen: userOnAdd-view.fxml
 **/
public class UserOnAddController extends BaseController {

    @FXML
    private ComboBox<Boolean> activComboBox;

    @FXML
    private ComboBox<String> authorizationCombobox;

    @FXML
    private TextField emailInput;

    @FXML
    private ComboBox<Boolean> emailVisableComboBox;


    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private TextField phoneInput;

    @FXML
    private ComboBox<Boolean> phoneVisableComboBox;

    @FXML
    private ComboBox<Boolean> photoVisableComboBox;;

    @FXML
    private TextArea textInput;

    @FXML
    private ComboBox<Boolean> textVisableComboBox;

    @FXML
    private TextField titleInput;

    @FXML
    private ComboBox<Boolean> coachComboBox;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField usernameInput;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField password2Input;
    @FXML
    private TextField passwordInput;

    FileChooser fileChooser = new FileChooser();
    InputStream inputStream = null;

    byte[] photo = null;




    @FXML
    void initialize() {

        ObservableList<Boolean> booleanList = ListUtility.booleanList();

        activComboBox.setItems(booleanList);
        coachComboBox.setItems(booleanList);
        textVisableComboBox.setItems(booleanList);
        emailVisableComboBox.setItems(booleanList);
        phoneVisableComboBox.setItems(booleanList);
        photoVisableComboBox.setItems(booleanList);
        authorizationCombobox.setItems(ListUtility.authorizationList());

        activComboBox.getSelectionModel().select(0);
        coachComboBox.getSelectionModel().select(0);
        textVisableComboBox.getSelectionModel().select(0);
        emailVisableComboBox.getSelectionModel().select(0);
        phoneVisableComboBox.getSelectionModel().select(0);
        photoVisableComboBox.getSelectionModel().select(0);


        BooleanConverter.setConverter(activComboBox);
        BooleanConverter.setConverter(coachComboBox);
        BooleanConverter.setConverter(textVisableComboBox);
        BooleanConverter.setConverter(emailVisableComboBox);
        BooleanConverter.setConverter(phoneVisableComboBox);
        BooleanConverter.setConverter(photoVisableComboBox);

        initFileChooser();

    }

    private void initFileChooser() {
        fileChooser.setTitle(Constants.ADD_USER_IMAGE);
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
    }

    @FXML
    void onSetImageButtonClicked(ActionEvent event) throws IOException {
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
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){

            User user = addUserByJDBC();

            if(user != null) {
                model.getDataholder().addUser(user);
                closeStage(errorLabel);
            }
        }
    }

    private boolean validateFields() {
        return !emptyFields() && replicatePassword() && explicitDescription() && validatePassword();
    }

    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getUsers().stream().noneMatch(u-> u.getUsername().equals(usernameInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.USERNAME_NOT_ALLOWED);
        }

        return explicit;
    }

    private boolean replicatePassword() {
        boolean replicate = passwordInput.getText().equals(password2Input.getText());

        if(!replicate){
            errorLabel.setText(Constants.REPEAT_PASSWORD);
        }

        return replicate;
    }

    private boolean validatePassword() {
        boolean valid = Password.validate(passwordInput.getText());

        if(!valid){
            errorLabel.setText(Constants.NOT_AVAILABLE_PASSWORD);
        }

        return valid;
    }

    private boolean emptyFields() {
         boolean empty = isBlank(firstnameInput.getText()) || isBlank(lastnameInput.getText()) || isBlank(usernameInput.getText()) ||
                 isBlank(passwordInput.getText()) || isBlank(password2Input.getText()) || authorizationCombobox.getValue() == null;

        if(empty){
            errorLabel.setText(Constants.EMPTY_OBLIGATORY_FIELDS);
        }

        return empty;
    }

    private User addUserByJDBC() throws Exception {
        String authorize = authorizationCombobox.getValue().equals("Administrator") ? "admin" : "coach";
        return Dataholder.userRepositoryJDBC.add(firstnameInput.getText(), lastnameInput.getText(), titleInput.getText(), usernameInput.getText(),
                authorize, Password.hash(passwordInput.getText()), coachComboBox.getValue(), textVisableComboBox.getValue(),
                phoneInput.getText(), phoneVisableComboBox.getValue(), emailInput.getText(), emailVisableComboBox.getValue(),
                photoVisableComboBox.getValue(), textInput.getText(),photo);
    }
}
