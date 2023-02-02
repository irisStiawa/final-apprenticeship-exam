package com.lap.roomplanningsystem.controller.updateController;

import java.util.Optional;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UserOnEditController extends BaseController {

    @FXML
    private TextField emailInput;

    @FXML
    private Label errorLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private TextField phoneInput;

    @FXML
    private TextArea textInput;

    private User user;




    @FXML
    void initialize() {
        Optional<User> optionalUser = model.getDataholder().getUsers().stream().filter(user -> user.getId() == model.getUser().getId()).findAny();

        if(optionalUser.isPresent()){
            user = optionalUser.get();

            numberLabel.setText("B" + String.valueOf(user.getId()));
            phoneInput.setText(user.getPhone());
            emailInput.setText(user.getEmail());
            textInput.setText(user.getText());

        }

    }

    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        user.setPhone(phoneInput.getText());
        user.setEmail(emailInput.getText());
        user.setText(textInput.getText());

        boolean isUpdated = updateUserByJDBC();

        if (isUpdated) {
            int index = model.getDataholder().getUsers().indexOf(user);
            model.getDataholder().updateUser(index, user);

            //TODO: Test ob notwendig!!!
            if(user.getId() == model.getUser().getId()){
                model.setUser(user);
            }

            model.getDataholder().updateEvents();
            closeStage(errorLabel);
        }


    }

    private boolean updateUserByJDBC() throws Exception {
        return Dataholder.userRepositoryJDBC.edit(user);
    }


}
