package com.lap.roomplanningsystem.controller.tableController;

import java.io.IOException;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.RoomEquipment;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RoomEquipmentTableController extends BaseController {

    @FXML
    private TableColumn<RoomEquipment, String> equipmentColumn;

    @FXML
    private TableColumn<RoomEquipment, String> numberColumn;

    @FXML
    private TableColumn<RoomEquipment, String> roomColumn;

    @FXML
    private TableView<RoomEquipment> tableView;




    @FXML
    void initialize() {

        tableView.setItems(model.getDataholder().getRoomEquipments());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("RA" + String.valueOf(dataFeatures.getValue().getRoomEquipmentID())));
        roomColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getRoom().getDescription()));
        equipmentColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getEquipment().getDescription()));


        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedRoomEquipmentProperty(nv);
                    showNewView(Constants.PATH_TO_ROOMEQUIPMENT_DETAIL_VIEW);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedRoomEquipmentProperty().addListener(new ChangeListener<RoomEquipment>() {
            @Override
            public void changed(ObservableValue<? extends RoomEquipment> observableValue, RoomEquipment oldRoomEquipment, RoomEquipment newRoomEquipment) {
                if(newRoomEquipment == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });
    }



}
