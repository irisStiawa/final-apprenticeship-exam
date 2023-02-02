package com.lap.roomplanningsystem.controller.detailController;

import java.io.IOException;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.RoomEquipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RoomEquipmentDetailViewController extends BaseController {

    @FXML
    private Label equipmentLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label roomLabel;

    private RoomEquipment roomEquipment;




    @FXML
    void initialize() {

        Optional<RoomEquipment> optionalRoomEquipment = model.getDataholder().getRoomEquipments().stream().filter(roomEquipment -> roomEquipment == model.getSelectedRoomEquipmentProperty()).findAny();

        if(optionalRoomEquipment.isPresent()) {
            roomEquipment = optionalRoomEquipment.get();


            numberLabel.setText("RA" + String.valueOf(roomEquipment.getRoomEquipmentID()));
            roomLabel.setText(roomEquipment.getRoom().getDescription());
            locationLabel.setText(roomEquipment.getRoom().getLocation().getDescription());
            equipmentLabel.setText(roomEquipment.getEquipment().getDescription());
        }
    }



    @FXML
    void onDeleteButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_ROOMEQUIPMENT_ON_DELETE_VIEW);
        closeStage(numberLabel);
    }

    @FXML
    void onUpdateButtonClicked(ActionEvent event)  throws IOException {
        showNewView(Constants.PATH_TO_ROOMEQUIPMENT_UPDATE_VIEW);
        closeStage(numberLabel);
    }

}
