package com.lap.roomplanningsystem.controller.addController;



import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Equipment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.lang.constant.Constable;

/**
 * Die Klasse EquipmentOnAddController ist eine Kindklasse des BaseController.
 * Kontrollerfunktionen: equipmentOnAdd-view.fxml
 **/
public class EquipmentOnAddController extends BaseController {


    @FXML
    private TextField descriptionInput;

    @FXML
    private Label errorLabel;


    /**
     * Methode initialize(): inizialisiert die View beim laden des FXML-Files.
     **/
    @FXML
    void initialize() {
    }


    /**
     * Methode onSaveButtonClicked(): übernimmt ein ActionEvent bei Klick auf den Speicher-Button
     * Aufgaben: validiert das Feld, versucht die Ausstattung der Datenbank hinzuzufügen,
     * bei Erfolg: wird die Ausstattung ins Datenmodell übernommen und schließen der View
     **/
    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){
            Equipment equipment = addEquipmentByJDBC();

            if(equipment != null){
                model.getDataholder().addEquipment(equipment);
                closeStage(errorLabel);
            }
        }
    }

    /**
     * Methode validateFields(): Prüft das Pflichtfeld und retouniert einen boolschen Wert
     * Aufgaben: Prüfung des leeren Felds, Prüfung des einmaligen Vorkommens der Beschreibung
     **/
    private boolean validateFields() {
        return !emptyFields() && explicitDescription();
    }


    /**
     * Methode emptyFields(): Prüft ob das Pflichtfeld ausgefüllt ist und retouniert einen boolschen Wert
     * Aufgaben: Prüft ob das Pflichfeld Text enthält, wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText());

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS);
        }

        return empty;
    }

    /**
     * Methode explicitDescription(): Prüft das Feld für die Beschreibung des Kurses und retouniert einen boolschen Wert
     * Aufgaben: Prüfung des einmaligen Vorkommens der Beschreibung, wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getEquipments().stream().noneMatch(e-> e.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.EQUIPMENT_DESCRIPTION_NOT_ALLOWED);
        }

        return explicit;
    }

    /**
     * Methode addEquipmentByJDBC(): Fügt die Ausstattung der Datenbank hinzu (wirft bei Fehler eine Exception)
     * und retouniert ein Objekt der Klasse Ausstattung
     * Aufgaben: einen neuen Ausstattung der Datenbank hinzufügen über die Klasse EquipmentRepositoryJDBC
     **/
    private Equipment addEquipmentByJDBC() throws Exception {
        return Dataholder.equipmentRepositoryJDBC.add(descriptionInput.getText());
    }
}
