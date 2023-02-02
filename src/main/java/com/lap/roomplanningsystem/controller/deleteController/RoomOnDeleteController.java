package com.lap.roomplanningsystem.controller.deleteController;

import java.sql.SQLException;
import java.util.Optional;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RoomOnDeleteController extends BaseController {



    @FXML
    private Label descriptionLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label sizeLabel;

    @FXML
    private Label numberLabel;

    private Room room;


    @FXML
    void initialize() {

        Optional<Room> optionalRoom = model.getDataholder().getRooms().stream().filter(r -> r.getRoomID() == model.getSelectedRoomProperty().getRoomID()).findAny();

        if(optionalRoom.isPresent()){
            room = optionalRoom.get();

            numberLabel.setText("R" + room.getRoomID());
            descriptionLabel.setText(room.getDescription());
            locationLabel.setText(room.getLocation().getDescription());
            sizeLabel.setText(String.valueOf(room.getMaxPersons()));

        }
    }

    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(numberLabel);
    }

    @FXML
    void onYesButtonClicked(ActionEvent event) throws Exception {
        model.setSelectedRoomProperty(null);

        if(deleteRoomByJDBC()){
            model.getDataholder().deleteRoom(room);
            model.getDataholder().updateEvents();
            model.getDataholder().updateRoomEquipments();

        }

        closeStage(numberLabel);
    }

    private boolean deleteRoomByJDBC() throws SQLException {
        return Dataholder.roomRepositoryJDBC.delete(room);
    }
}
