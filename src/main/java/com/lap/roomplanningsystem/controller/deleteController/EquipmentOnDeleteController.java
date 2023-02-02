package com.lap.roomplanningsystem.controller.deleteController;

import java.util.Optional;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EquipmentOnDeleteController extends BaseController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label numberLabel;

    private Equipment equipment;



    @FXML
    void initialize() {
        assert descriptionLabel != null : "fx:id=\"descriptionLabel\" was not injected: check your FXML file 'equipmentOnDelete-view.fxml'.";
        assert numberLabel != null : "fx:id=\"numberLabel\" was not injected: check your FXML file 'equipmentOnDelete-view.fxml'.";

        Optional<Equipment> optionalEquipment = model.getDataholder().getEquipments().stream().filter(e -> e.getEquipmentID() == model.getSelectedEquipmentProperty().getEquipmentID()).findAny();

        if(optionalEquipment.isPresent()){
            equipment = optionalEquipment.get();
            numberLabel.setText("A" + equipment.getEquipmentID());
            descriptionLabel.setText(equipment.getDescription());
        }

    }

    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(numberLabel);
    }

    @FXML
    void onYesButtonClicked(ActionEvent event) throws Exception {
        model.setSelectedEquipmentProperty(null);

        if(deleteEventByJDBC()){
            model.getDataholder().deleteEquipment(equipment);
            model.getDataholder().updateUsers();
        }

        closeStage(numberLabel);

    }

    private boolean deleteEventByJDBC() throws Exception{
        return Dataholder.equipmentRepositoryJDBC.delete(equipment);
    }

}
