package com.lap.roomplanningsystem.controller.deleteController;

import java.sql.SQLException;
import java.util.Optional;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LocationOnDeleteController extends BaseController {

    @FXML
    private Label adressLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label postCodeLabel;

    private Location location;
    @FXML
    private Label numberLabel;


    @FXML
    void initialize() {
        Optional<Location> optionalLocation = model.getDataholder().getLocations().stream().filter(l -> l.getLocationID() == model.getSelectedLocationProperty().getLocationID()).findAny();

        if(optionalLocation.isPresent()){
            location = optionalLocation.get();

            numberLabel.setText("S" + location.getLocationID());
            descriptionLabel.setText(location.getDescription());
            adressLabel.setText(location.getAdress());
            postCodeLabel.setText(location.getPostCode());
            cityLabel.setText(location.getCity());

        }
    }

    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(descriptionLabel);

    }

    @FXML
    void onYesButtonClicked(ActionEvent event) throws Exception {
        model.setSelectedLocationProperty(null);

        if(deleteLocationByJDBC()){
            model.getDataholder().deleteLocation(location);
            model.getDataholder().updateRooms();
            model.getDataholder().updateEvents();
            model.getDataholder().updateEquipments();
        }

        closeStage(descriptionLabel);

    }

    private boolean deleteLocationByJDBC() throws SQLException {
        return Dataholder.locationRepositoryJDBC.delete(location);
    }
}
