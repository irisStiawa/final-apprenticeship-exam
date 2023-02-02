package com.lap.roomplanningsystem.controller.tableController;

import java.io.IOException;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Room;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RoomTableController extends BaseController {

    @FXML
    private TableColumn<Room, String> locationColumn;

    @FXML
    private TableColumn<Room, String> numberColumn;

    @FXML
    private TableColumn<Room, Integer> sizeColumn;

    @FXML
    private TableView<Room> tableView;

    @FXML
    private TableColumn<Room, String> titleColumn;



    @FXML
    void initialize() {

        tableView.setItems(model.getDataholder().getRooms());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("R" + String.valueOf(dataFeatures.getValue().getRoomID())));
        titleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));
        sizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().getMaxPersons()));
        locationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getLocation().getDescription()));


        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) ->  {

            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedRoomProperty(nv);
                    showNewView(Constants.PATH_TO_ROOM_DETAIL_VIEW);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedRoomProperty().addListener(new ChangeListener<Room>() {
            @Override
            public void changed(ObservableValue<? extends Room> observableValue, Room oldRoom, Room newRoom) {
                if(newRoom == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });

    }


}

