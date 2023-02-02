package com.lap.roomplanningsystem.controller.updateController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.converter.EquipmentConverter;
import com.lap.roomplanningsystem.converter.LocationConverter;
import com.lap.roomplanningsystem.converter.RoomConverter;
import com.lap.roomplanningsystem.model.*;
import com.lap.roomplanningsystem.utility.ListUtility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class RoomEquipmentOnUpdateController extends BaseController {

    @FXML
    private ComboBox<Equipment> equipmentComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private Label numberLabel;

    @FXML
    private ComboBox<Room> roomComboBox;

    private RoomEquipment roomEquipment;



    @FXML
    void initialize() {
        Optional<RoomEquipment> optionalRoomEquipment = model.getDataholder().getRoomEquipments().stream().filter(roomEquipment -> roomEquipment == model.getSelectedRoomEquipmentProperty()).findAny();

        if(optionalRoomEquipment.isPresent()) {
            roomEquipment = optionalRoomEquipment.get();

            numberLabel.setText("RA" + String.valueOf(roomEquipment.getRoomEquipmentID()));
            locationComboBox.setItems(model.getDataholder().getLocations());
            roomComboBox.setItems(ListUtility.availableRoomAtLocation(roomEquipment.getRoom().getLocation()));
            equipmentComboBox.setItems(FXCollections.observableList(createAvailableEquipmentList()));

            locationComboBox.getSelectionModel().select(roomEquipment.getRoom().getLocation());
            roomComboBox.getSelectionModel().select(roomEquipment.getRoom());
            equipmentComboBox.getSelectionModel().select(roomEquipment.getEquipment());

            initConverter();

            locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
                @Override
                public void changed(ObservableValue<? extends Location> observableValue, Location location, Location newLocation) {
                    roomComboBox.setItems(ListUtility.availableRoomAtLocation(newLocation));
                    roomComboBox.getSelectionModel().select(null);
                    roomComboBox.setPromptText("Raum");
                }
            });
        }
    }

    private List<Equipment> createAvailableEquipmentList(){
        List<Equipment> notAvailableEquipments = model.getDataholder().getRoomEquipments().stream().map(RoomEquipment::getEquipment).toList();
        List<Equipment> equipments = new ArrayList<>(model.getDataholder().getEquipments());
        equipments.removeIf(e -> e.getEquipmentID() != roomEquipment.getEquipment().getEquipmentID() && notAvailableEquipments.stream().anyMatch(equipment -> equipment.getEquipmentID() == e.getEquipmentID()));
        return equipments;
    }

    private void initConverter() {
        LocationConverter locationConverter = new LocationConverter();
        locationConverter.setConverter(locationComboBox);

        RoomConverter roomConverter = new RoomConverter(locationComboBox);
        roomConverter.setConverter(roomComboBox);

        EquipmentConverter equipmentConverter = new EquipmentConverter();
        equipmentConverter.setConverter(equipmentComboBox);

    }

    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        roomEquipment.setRoom(roomComboBox.getValue());
        roomEquipment.setEquipment(equipmentComboBox.getValue());

        boolean isUpdated =  updateRoomEquipmentByJDBC();

        if(isUpdated){
            showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
            int index = model.getDataholder().getRoomEquipments().indexOf(roomEquipment);
            model.getDataholder().updateRoomEquipment(index, roomEquipment);
            closeStage(errorLabel);
        }

    }


   private boolean updateRoomEquipmentByJDBC() throws Exception {
        return Dataholder.roomEquipmentRepositoryJDBC.update(roomEquipment);
    }



}
