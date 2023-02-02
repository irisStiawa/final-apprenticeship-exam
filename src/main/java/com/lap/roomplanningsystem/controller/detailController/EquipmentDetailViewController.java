package com.lap.roomplanningsystem.controller.detailController;

import java.io.IOException;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Equipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EquipmentDetailViewController extends BaseController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label numberLabel;

    private Equipment equipment;

    @FXML
    void initialize() {

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(equipment -> equipment == model.getSelectedEquipmentProperty()).findAny();

        if(optionalEquipment.isPresent()) {
            equipment = optionalEquipment.get();
            numberLabel.setText("A" + String.valueOf(equipment.getEquipmentID()));
            descriptionLabel.setText(equipment.getDescription());
        }

    }



    @FXML
    void onDeleteButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EQUIPMENT_ON_DELETE_VIEW);
        closeStage(numberLabel);
    }

    @FXML
    void onUpdateButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EQUIPMENT_UPDATE_VIEW);
        closeStage(numberLabel);
    }



}
