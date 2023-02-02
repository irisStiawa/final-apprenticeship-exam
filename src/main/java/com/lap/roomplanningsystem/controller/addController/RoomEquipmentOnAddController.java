package com.lap.roomplanningsystem.controller.addController;

import java.util.List;


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


/**
 * Die Klasse RoomEquipmentOnAddController ist eine Kindklasse des BaseController.
 * Kontrollerfunktionen: roomEquipmentOnAdd-view.fxml
 **/
public class RoomEquipmentOnAddController extends BaseController {

    @FXML
    private ComboBox<Equipment> equipmentComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private ComboBox<Room> roomComboBox;



    /**
     * Methode initialize(): inizialisiert die View beim laden des FXML-Files.
     * Aufgaben: setzt Standardwerte der Felder, erstellt und setzt String-Konverter für die Kombobox
     **/
    @FXML
    void initialize() {
        initComboBoxes();
        initConverter();

       locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location t1) {
                roomComboBox.setItems(ListUtility.availableRoomAtLocation(locationComboBox.getValue()));
                roomComboBox.getSelectionModel().select(0);
            }
        });


    }

    private void initConverter(){
        LocationConverter locationConverter = new LocationConverter();
        locationConverter.setConverter(locationComboBox);

        RoomConverter roomConverter = new RoomConverter(locationComboBox);
        roomConverter.setConverter(roomComboBox);

        EquipmentConverter equipmentConverter = new EquipmentConverter();
        equipmentConverter.setConverter(equipmentComboBox);

    }

    private void initComboBoxes() {
        locationComboBox.setItems(model.getDataholder().getLocations());
        locationComboBox.getSelectionModel().select(0);


        roomComboBox.setItems(ListUtility.availableRoomAtLocation(locationComboBox.getValue()));
        roomComboBox.getSelectionModel().select(0);

        List<Equipment> notAvailableEquipments = model.getDataholder().getRoomEquipments().stream().map(RoomEquipment::getEquipment).toList();
        List<Equipment> equipments = model.getDataholder().getEquipments();
        equipments.removeIf(e -> notAvailableEquipments.stream().anyMatch(equipment -> equipment.getEquipmentID() == e.getEquipmentID()));

        equipmentComboBox.setItems(FXCollections.observableList(equipments));
        equipmentComboBox.getSelectionModel().select(0);
    }


    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        RoomEquipment roomEquipment = addRoomEquipmentByJDBC();

        if(roomEquipment != null){
            model.getDataholder().addRoomEquipment(roomEquipment);
            closeStage(errorLabel);
        }
    }

    private RoomEquipment addRoomEquipmentByJDBC() throws Exception {
        return Dataholder.roomEquipmentRepositoryJDBC.add(roomComboBox.getValue(), equipmentComboBox.getValue());
    }

}
