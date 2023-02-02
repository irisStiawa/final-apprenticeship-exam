package com.lap.roomplanningsystem.controller.addController;

import java.sql.Date;
import java.time.LocalDate;


import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Contract;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.utility.ListUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ContractOnAddController extends BaseController {

    @FXML
    private TextField adressInput;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField firstnameInput;

    @FXML
    private TextField landInput;

    @FXML
    private TextField lastnameInput;

    @FXML
    private Label numberLabel;

    @FXML
    private TextField phoneInput;

    @FXML
    private TextField postCodeInput;

    @FXML
    private TextField titleInput;
    @FXML
    private ChoiceBox typeComboBox;


    @FXML
    void initialize() {
        assert adressInput != null : "fx:id=\"adressInput\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";
        assert firstnameInput != null : "fx:id=\"firstnameInput\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";
        assert landInput != null : "fx:id=\"landInput\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";
        assert lastnameInput != null : "fx:id=\"lastnameInput\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";
        assert phoneInput != null : "fx:id=\"phoneInput\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";
        assert postCodeInput != null : "fx:id=\"postCodeInput\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";
        assert titleInput != null : "fx:id=\"titleInput\" was not injected: check your FXML file 'contractOnAdd-view.fxml'.";

        typeComboBox.setItems(ListUtility.createContractTypList());


    }


    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        if(validateFields()){
            Contract contract = addContractByJDBC();

            if(contract != null){
                model.getDataholder().addContract(contract);
                closeStage(errorLabel);
            }
        }

    }

    private boolean validateFields() {
        return !emptyFields() && validDate();
    }

    private boolean emptyFields() {
        boolean empty = typeComboBox.getValue() == null || isBlank(firstnameInput.getText()) || isBlank(lastnameInput.getText()) || datePicker.getValue() == null
                || isBlank(adressInput.getText()) || isBlank(postCodeInput.getText()) || isBlank(landInput.getText()) || isBlank(phoneInput.getText());


        if(empty){
            errorLabel.setText(Constants.EMPTY_OBLIGATORY_FIELDS);
        }

        return empty;
    }

    private boolean validDate() {
        boolean valid = !datePicker.getValue().isBefore(LocalDate.now());

        if(!valid){
            errorLabel.setText("Start darf nicht in der Vergangenheit liegen!");
        }

        return valid;
    }


    private Contract addContractByJDBC() throws Exception {
        String type = typeComboBox.getValue().equals("Halbjahresvertrag") ? "half" : "year";
        return Dataholder.contractRepositoryJDBC.add(titleInput.getText(), firstnameInput.getText(), lastnameInput.getText(), adressInput.getText(), postCodeInput.getText(),
                landInput.getText(), phoneInput.getText(), Date.valueOf(datePicker.getValue()), type);
    }


}
