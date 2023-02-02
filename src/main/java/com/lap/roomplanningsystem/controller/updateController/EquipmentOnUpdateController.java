package com.lap.roomplanningsystem.controller.updateController;

import java.util.List;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EquipmentOnUpdateController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private Label numberLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    private Equipment equipment;

    @FXML
    void initialize() {

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(equipment -> equipment == model.getSelectedEquipmentProperty()).findAny();

        if(optionalEquipment.isPresent()){
            equipment = optionalEquipment.get();

            numberLabel.setText("A" + String.valueOf(equipment.getEquipmentID()));
            descriptionInput.setText(equipment.getDescription());

        }
    }



    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){
            equipment.setDescription(descriptionInput.getText());

            boolean isUpdated = updateEquipmentByJDBC();

            if(isUpdated){
                showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                int index = model.getDataholder().getEquipments().indexOf(equipment);
                model.getDataholder().updateEquipment(index, equipment);

                model.getDataholder().updateRoomEquipments();
                closeStage(errorLabel);
            }
        }
    }



    private boolean validateFields() {
        return !emptyFields() && explicitDescription();
    }


    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText());

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS);
        }

        return empty;
    }

    private boolean explicitDescription() {
        List<Equipment> equipments = model.getDataholder().getEquipments().stream().filter(e-> e !=equipment).toList();
        boolean explicit = equipments.stream().noneMatch(e-> e.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.EQUIPMENT_DESCRIPTION_NOT_ALLOWED);
        }

        return explicit;
    }

    private boolean updateEquipmentByJDBC() throws Exception {
        return Dataholder.equipmentRepositoryJDBC.update(equipment);
    }
}
