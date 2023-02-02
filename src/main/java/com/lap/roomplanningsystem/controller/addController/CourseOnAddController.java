package com.lap.roomplanningsystem.controller.addController;


import java.sql.Date;


import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.converter.ProgramConverter;

import com.lap.roomplanningsystem.model.Course;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Program;
import com.lap.roomplanningsystem.utility.IntegerUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Die Klasse CourseOnAddController ist eine Kindklasse des BaseController.
 * Kontrollerfunktionen: courseOnAdd-view.fxml
 **/
public class CourseOnAddController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField membersInput;

    @FXML
    private ComboBox<Program> programComboBox;


    @FXML
    private DatePicker startDatePicker;



    /**
     * Methode initialize(): inizialisiert die View beim laden des FXML-Files.
     * Aufgaben: setzt Standardwerte der Felder, erstellt und setzt String-Konverter für die Kombobox
     **/
    @FXML
    void initialize() {

        programComboBox.setItems(model.getDataholder().getPrograms());
        programComboBox.setPromptText("Programm");

        ProgramConverter programConverter = new ProgramConverter();
        programConverter.setConverter(programComboBox);
    }

    /**
     * Methode onSaveButtonClicked(): übernimmt ein ActionEvent bei Klick auf den Speicher-Button
     * Aufgaben: validiert die Felder, versucht den Kurs der Datenbank hinzuzufügen,
     * bei Erfolg: wird der Kurs ins Datenmodell übernommen und schließen der View
     **/
    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        if(validateFields()){
            Course course = addCourseByJDBC();

            if(course != null){
                model.getDataholder().addCourse(course);
                closeStage(errorLabel);
            }
        }
    }

    /**
     * Methode validateFields(): Prüft die Felder und retouniert einen boolschen Wert
     * Aufgaben: Prüfung der leeren Felder, Prüfung des einmaligen Vorkommens der Beschreibung, Prüfung ob für die Teilnehmer
     * eine erlaubte Zahl eingegeben wurde, Prüfung ob Enddatum vor dem Startdatum liegt
     **/
    private boolean validateFields() {
        return !emptyFields() && explicitDescription() && validMembers() && validDate();
    }

    /**
     * Methode emptyFields(): Prüft ob die Pflichtfelder ausgefüllt sind und retouniert einen boolschen Wert
     * Aufgaben: Prüft ob alle Pflichfelder Text enthalten und ob der DatePicker einen Wert erhalten hat, wenn nicht
     * wird eine Errormeldung erzeugt
     **/
    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText()) || isBlank(membersInput.getText()) || startDatePicker.getValue() == null || programComboBox.getValue() == null;

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS_AND_BOXES);
        }

        return empty;
    }

    /**
     * Methode explicitDescription(): prüft das Feld für die Beschreibung des Kurses und retouniert einen boolschen Wert
     * Aufgaben: Prüfung des einmaligen Vorkommens der Beschreibung, wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getCourses().stream().noneMatch(c-> c.getTitle().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.COURSE_DESCRIPTION_NOT_ALLOWED);
        }

        return explicit;
    }

    /**
     * Methode validateFields(): prüft das Feld für die Teilnehmerzahl und retouniert einen boolschen Wert
     * Aufgaben: Prüfung ob sich der String wie eine Zahl verhält durch die Klasse IntegerUtility,
     * wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean validMembers() {
        boolean valid = IntegerUtility.getInt(membersInput.getText()) != null;

        if(!valid){
            errorLabel.setText(Constants.MEMBERS_NOT_A_NUMBER);
        }

        return valid;
    }

    /**
     * Methode validateDate(): prüft die Felder für Start- und Enddatum und retouniert einen boolschen Wert
     * Aufgaben: Prüfung ob sich der String wie eine Zahl verhält durch die Klasse IntegerUtility,
     * wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean validDate() {
        boolean valid = !endDatePicker.getValue().isBefore(startDatePicker.getValue());

        if(!valid){
            errorLabel.setText(Constants.ENDDATE_BEFORE_STARTDATE);
        }

        return valid;
    }

    /**
     * Methode addCourseByJDBC(): fügt den Kurs der Datenbank hinzu (wirft bei Fehler eine Exception)
     * und retouniert ein Objekt der Klasse Kurs
     * Aufgaben: einen neuen Kurs der Datenbank hinzufügen über die Klasse CourseRepositoryJDBC
     **/
    private Course addCourseByJDBC() throws Exception {
        return Dataholder.courseRepositoryJDBC.add(descriptionInput.getText(), programComboBox.getValue(), IntegerUtility.getInt(membersInput.getText()),
                Date.valueOf(startDatePicker.getValue()), Date.valueOf(endDatePicker.getValue()));
    }

}
