package com.lap.roomplanningsystem.controller;

import java.io.IOException;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.utility.ListUtility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class StammdataViewController extends BaseController{


    @FXML
    private ChoiceBox<String> choiceBoxForTables;
    @FXML
    private BorderPane tableBorderPane;

    @FXML
    private ImageView profilImage;


    @FXML
    void initialize() throws IOException {
        if(model.getUser()!= null){
            setProfilImage(profilImage);
        }

        loadFXMLInBorderPaneCenter(Constants.PATH_TO_USER_TABLE_VIEW);


        choiceBoxForTables.setItems(ListUtility.initItems());
        choiceBoxForTables.setValue("Benutzer");

        choiceBoxForTables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                try {
                    vaildateTable(newValue);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void loadFXMLInBorderPaneCenter(String fxmlPath) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
        tableBorderPane.setCenter(view);
    }


    private void vaildateTable(String newValue) throws IOException {
        switch(newValue){
            case "Benutzer" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_USER_TABLE_VIEW);
            case "Räume" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_ROOM_TABLE_VIEW);
            case "Veranstaltungen" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_EVENT_TABLE_VIEW);
            case "Kurse" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_COURSE_TABLE_VIEW);
            case "Programme" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_PROGRAM_TABLE_VIEW);
            case "Standorte" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_LOCATION_TABLE_VIEW);
            case "Ausstattung" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_EQUIPMENT_TABLE_VIEW);
            case "Raumausstattung" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_ROOMEQUIPMENT_TABLE_VIEW);
            case "Verträge" -> loadFXMLInBorderPaneCenter(Constants.PATH_TO_CONTRACT_TABLE_VIEW);
        }
    }

    @FXML
    void onLogoutButtonClicked(ActionEvent actionEvent) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);

    }


    @FXML
    private void onNewButtonClicked(MouseEvent mouseEvent) throws IOException {
        switch(choiceBoxForTables.getValue()){
            case "Benutzer" -> showNewView(Constants.PATH_TO_USER_ADD_VIEW);
            case "Räume" -> showNewView(Constants.PATH_TO_ROOM_ADD_VIEW);
            case "Veranstaltungen" -> model.setPathToView(Constants.PATH_TO_CREATE_EVENT_VIEW);
            case "Kurse" -> showNewView(Constants.PATH_TO_COURSE_ADD_VIEW);
            case "Programme" -> showNewView(Constants.PATH_TO_PROGRAM_ADD_VIEW);
            case "Standorte" -> showNewView(Constants.PATH_TO_LOCATION_ADD_VIEW);
            case "Ausstattung" -> showNewView(Constants.PATH_TO_EQUIPMENT_ADD_VIEW);
            case "Raumausstattung" -> showNewView(Constants.PATH_TO_ROOMEQUIPMENT_ADD_VIEW);

        }
    }

    @FXML
    private void onProfilIconClicked(MouseEvent mouseEvent) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }
}