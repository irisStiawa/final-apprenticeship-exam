package com.lap.roomplanningsystem.controller.updateController;


import java.util.List;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;

import com.lap.roomplanningsystem.model.*;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LocationOnUpdateController extends BaseController {

    @FXML
    private TextField adressInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField descriptionInput;

    @FXML
    private Label numberLabel;

    @FXML
    private TextField postCodeInput;
    @FXML
    private Label errorLabel;

    private Location location;




    @FXML
    void initialize() {

        Optional<Location> optionalLocation = model.getDataholder().getLocations().stream().filter(location -> location == model.getSelectedLocationProperty()).findAny();

        if(optionalLocation.isPresent()) {
            location = optionalLocation.get();

            numberLabel.setText("S" + String.valueOf(location.getLocationID()));
            descriptionInput.setText(location.getDescription());
            adressInput.setText(location.getAdress());
            postCodeInput.setText(location.getPostCode());
            cityInput.setText(location.getCity());

        }

    }

    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){
            location.setDescription(descriptionInput.getText());
            location.setAdress(adressInput.getText());
            location.setPostCode(postCodeInput.getText());
            location.setCity(cityInput.getText());

            boolean isUpdated = updateLocationByJDBC();

            if(isUpdated){
                showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                int index = model.getDataholder().getLocations().indexOf(location);
                model.getDataholder().updateLocation(index, location);
                model.getDataholder().updateRooms();
                model.getDataholder().updateEvents();
                model.getDataholder().updateRoomEquipments();
                closeStage(errorLabel);
            }


        }
    }

    private boolean validateFields() {
        return !emptyFields() && explicitDescription();
    }


    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText()) || isBlank(adressInput.getText()) || isBlank(cityInput.getText()) || isBlank(postCodeInput.getText());

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS);
        }

        return empty;
    }

    private boolean explicitDescription() {
        List<Location> locations = model.getDataholder().getLocations().stream().filter(l-> l !=location).toList();
        boolean explicit = locations.stream().noneMatch(l-> l.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.LOCATION_DESCRIPTION_NOT_ALLOWED);
        }

        return explicit;
    }

    private boolean updateLocationByJDBC() throws Exception {
        return Dataholder.locationRepositoryJDBC.update(location);
    }




}
