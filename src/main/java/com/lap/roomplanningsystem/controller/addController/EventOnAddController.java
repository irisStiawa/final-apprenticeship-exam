package com.lap.roomplanningsystem.controller.addController;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.converter.CourseConverter;
import com.lap.roomplanningsystem.converter.LocationConverter;
import com.lap.roomplanningsystem.converter.RoomConverter;
import com.lap.roomplanningsystem.converter.UserConverter;
import com.lap.roomplanningsystem.formattor.DateFormattorJDBC;
import com.lap.roomplanningsystem.model.*;
import com.lap.roomplanningsystem.utility.ListUtility;
import com.lap.roomplanningsystem.validation.DateValidator;
import com.lap.roomplanningsystem.validation.EventValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Die Klasse EventOnAddController ist eine Kindklasse des BaseController.
 * Kontrollerfunktionen: eventOnAdd-view.fxml
 **/
public class EventOnAddController extends BaseController {

    @FXML
    private ComboBox<User> coachComboBox;

    @FXML
    private ComboBox<Course> courseComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<LocalTime> endComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<Location> locationComboBox;


    @FXML
    private ComboBox<Room> roomComboBox;

    @FXML
    private ComboBox<LocalTime> startComboBox;



    /**
     * Methode initialize(): inizialisiert die View beim laden des FXML-Files.
     * Aufgaben: setzt Standardwerte der Felder und Listener, erstellt und setzt String-Konverter für die Kombobox
     **/
    @FXML
    void initialize() {
        initFields();
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

    /**
     * Methode initFields(): setzt Standartwerte für Komboboxen
     * Aufgaben: erstellt eine Observable-Liste der Klasse LocalTime durch die Klasse ListUtility; setzt Strandardwerte:
     * Referenzen zum Datenmodell, Observable-Liste mit erlaubten Uhrzeiten
     **/
    private void initFields() {
        ObservableList<LocalTime> timeList = ListUtility.createTimeList();
        locationComboBox.setItems(model.getDataholder().getLocations());
        coachComboBox.setItems(model.getDataholder().getCoaches());
        courseComboBox.setItems(model.getDataholder().getCourses());
        startComboBox.setItems(timeList);
        endComboBox.setItems(timeList);

    }

    /**
     * Methode initConverter(): setzt Konverter für Komboboxen
     * Aufgaben: erstellt Konverter der Klassen CourseConverter, LocationConverter, RoomConverter, UserConverter und
     * setzt diese dann auf die entsprechende Kombobox
     **/
    private void initConverter(){
        CourseConverter courseConverter = new CourseConverter();
        courseConverter.setConverter(courseComboBox);
        LocationConverter locationConverter = new LocationConverter();
        locationConverter.setConverter(locationComboBox);
        RoomConverter roomConverter = new RoomConverter(locationComboBox);
        roomConverter.setConverter(roomComboBox);
        UserConverter userConverter = new UserConverter();
        userConverter.setConverter(coachComboBox);
    }

    /**
     * Methode onSaveButtonClicked(): übernimmt ein ActionEvent bei Klick auf den Speicher-Button
     * Aufgaben: setzt die Errormeldung zurück, erstellt ein Objekt der Klasse EventValidator
     * validiert die Felder, versucht den Kurs der Datenbank hinzuzufügen
     * bei Fehler der Felderprüfung: wird eine Errormeldung erzeugt
     * bei Erfolg der FelderPrüfung: wird der Kurs ins Datenmodell übernommen, eine Referenz zur Variable newEvent(aus der Klasse Model)
     * erzeugt und schließen der View
     **/
    @FXML
    void onSaveButtonClicked(ActionEvent actionEvent) throws Exception {
        errorLabel.setText("");
        EventValidator eventValidator = new EventValidator();

        if(validateFields() && eventValidator.validSingle(roomComboBox.getValue(), courseComboBox.getValue(), coachComboBox.getValue(),datePicker.getValue(), startComboBox.getValue(),endComboBox.getValue())){

            Event event = addEventByJDBC();

            if(event != null){
                model.getDataholder().addEvent(event);
                model.setNewEvent(event);
                closeStage(errorLabel);

            }
        } else {
            errorLabel.setText(eventValidator.getErrString());


        }
    }

    /**
     * Methode validateFields(): Prüft die Felder und retouniert einen boolschen Wert
     * Aufgaben: Prüfung der leeren Felder, Prüfung ob Enddatum vor dem Startdatum liegt, Prüfung ob kleiner oder
     * gleich Teilnehmerzahl und Raumgröße ist
     **/
    private boolean validateFields() {
        return !emptyFields() && validateDate() && validateSize();
    }



    /**
     * Methode emptyFields(): Prüft ob die Pflichtfelder ausgefüllt sind und retouniert einen boolschen Wert
     * Aufgaben: Prüft ob die Pflichfelder Text enthalten oder einen Wert gewählt wurde, wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean emptyFields() {
        boolean empty = courseComboBox.getValue() == null || locationComboBox.getValue() == null || roomComboBox.getValue() == null ||
                datePicker.getValue() == null || startComboBox.getValue() == null || endComboBox.getValue() == null || coachComboBox.getValue() == null;

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS_AND_BOXES);
        }
        return empty;
    }

    /**
     * Methode validateDate(): Prüft die Felder für Start- und Enddatum und retouniert einen boolschen Wert
     * Aufgaben: Prüfung ob die End- vor der Startzeit und das End- vor dem Startdatum ist, wenn dies zutrifft dann wird eine
     * Errormeldung erzeugt
     **/
    private boolean validateDate(){
        if(DateValidator.validDate(datePicker.getValue())){
            if(DateValidator.validTime(startComboBox.getValue(), endComboBox.getValue())){
                return true;
            } else {
                errorLabel.setText(Constants.ENDTIME_BEFORE_STARTTIME);
                return false;
            }
        } else{
            errorLabel.setText(Constants.ENDDATE_BEFORE_STARTDATE);
            return false;
        }

    }

    /**
     * Methode validateSize(): Prüft ob Teilnehmeranzahl kleiner oder gleich Raumgröße ist und retouniert einen boolschen Wert
     * Aufgaben: Prüfung ob die Teilnehmerzahl kleiner oder gleich groß wie die maximale Personenzahl für den ausgewählten Raum ist
     * wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean validateSize() {
        boolean valid = courseComboBox.getValue().getMembers() <= roomComboBox.getValue().getMaxPersons();

        if(!valid){
            errorLabel.setText(Constants.OVERSIZED_MEMBERS);
            return false;
        }
        return valid;
    }



    /**
     * Methode addEventByJDBC(): Fügt das Event der Datenbank hinzu (wirft bei Fehler eine Exception)
     * und retouniert das ein Objekt der Klasse Event
     * Aufgaben: für Start- und Enzeit jeweils ein Objekt der Klasse LocalDateTime erstellen durch die Klasse DateFormattorJDBC,
     * einen neues Event der Datenbank hinzufügen über die Klasse EventRepositoryJDBC
     **/
    private Event addEventByJDBC() throws Exception {
        LocalDateTime start = DateFormattorJDBC.format(datePicker.getValue(), startComboBox.getValue());
        LocalDateTime end = DateFormattorJDBC.format(datePicker.getValue(), endComboBox.getValue());
        return Dataholder.eventRepositoryJDBC.add(model.getUser(), roomComboBox.getValue(), courseComboBox.getValue(), coachComboBox.getValue(),
                datePicker.getValue(), start, end);
    }
}
