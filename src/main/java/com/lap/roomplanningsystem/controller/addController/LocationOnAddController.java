package com.lap.roomplanningsystem.controller.addController;



import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


/**
 * Die Klasse LocationOnAddController ist eine Kindklasse des BaseController.
 * Kontrollerfunktionen: locationOnAdd-view.fxml
 **/
public class LocationOnAddController extends BaseController {

    @FXML
    private TextField adressInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField descriptionInput;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField postCodeInput;



    /**
     * Methode initialize(): inizialisiert die View beim laden des FXML-Files.
     **/
    @FXML
    void initialize() {
    }

    /**
     * Methode onSaveButtonClicked(): übernimmt ein ActionEvent bei Klick auf den Speicher-Button
     * Aufgaben: validiert die Felder, versucht die Standort der Datenbank hinzuzufügen,
     * bei Erfolg: wird die Standort ins Datenmodell übernommen und schließen der View
     **/
    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){
            Location location = addLocationByJDBC();

            if(location != null){
                model.getDataholder().addLocation(location);
                closeStage(errorLabel);
            }


        }
    }

    /**
     * Methode validateFields(): Prüft die Felder und retouniert einen boolschen Wert
     * Aufgaben: Prüfung der leeren Felder, Prüfung des einmaligen Vorkommens der Beschreibung
     **/
    private boolean validateFields() {
        return !emptyFields() && explicitDescription();
    }


    /**
     * Methode emptyFields(): Prüft ob die Pflichtfelder ausgefüllt sind und retouniert einen boolschen Wert
     * Aufgaben: Prüft ob die Pflichfelder Text enthalten, wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText()) || isBlank(adressInput.getText()) || isBlank(cityInput.getText()) || isBlank(postCodeInput.getText());

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS);
        }

        return empty;
    }

    /**
     * Methode explicitDescription(): Prüft das Feld für die Beschreibung des Standortes und retouniert einen boolschen Wert
     * Aufgaben: Prüfung des einmaligen Vorkommens der Beschreibung, wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getLocations().stream().noneMatch(l-> l.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.LOCATION_DESCRIPTION_NOT_ALLOWED);
        }

        return explicit;
    }

    /**
     * Methode addLocationByJDBC(): Fügt den Standort der Datenbank hinzu (wirft bei Fehler eine Exepction)
     * und retouniert ein Objekt der Klasse Standort
     * Aufgaben: einen neuen Standort der Datenbank hinzufügen über die Klasse LocationRepositoryJDBC
     **/
    private Location addLocationByJDBC() throws Exception {
        return Dataholder.locationRepositoryJDBC.add(descriptionInput.getText(), adressInput.getText(),  postCodeInput.getText(), cityInput.getText());
    }
}
