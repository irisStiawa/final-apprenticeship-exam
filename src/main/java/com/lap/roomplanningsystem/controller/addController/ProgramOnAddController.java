package com.lap.roomplanningsystem.controller.addController;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Program;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Die Klasse ProgramOnAddController ist eine Kindklasse des BaseController.
 * Kontrollerfunktionen: programOnAdd-view.fxml
 **/
public class ProgramOnAddController extends BaseController {

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
     * Aufgaben: validiert das Feld, versucht das Programm der Datenbank hinzuzufügen,
     * bei Erfolg: wird das Program ins Datenmodell übernommen und schließen der View
     **/
    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        if(validateFields()) {
            Program program = addProgramByJDBC();

            if (program != null) {
                model.getDataholder().addProgram(program);
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
     * Methode explicitDescription(): Prüft das Feld für die Beschreibung des Kurses und retouniert einen boolschen Wert
     * Aufgaben: Prüfung des einmaligen Vorkommens der Beschreibung, wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean explicitDescription() {
        boolean explicit = model.getDataholder().getPrograms().stream().noneMatch(p-> p.getDescription().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.PROGRAMM_DESCRIPTION_NOT_ALLOWED);
        }

        return explicit;
    }

    /**
     * Methode emptyFields(): Prüft ob das Pflichtfeld ausgefüllt ist und retouniert einen boolschen Wert
     * Aufgaben: Prüft ob das Pflichfeld Text enthält, wenn nicht wird eine Errormeldung erzeugt
     **/
    private boolean emptyFields() {
        if(isBlank(descriptionInput.getText())){
            errorLabel.setText(Constants.EMPTY_FIELDS);
        }

        return isBlank(descriptionInput.getText());
    }


    /**
     * Methode addProgramByJDBC(): Fügt das Programm der Datenbank hinzu (wirft bei Fehler eine Exception)
     * und retouniert ein Objekt der Klasse Program
     * Aufgaben: einen neues Pragramm der Datenbank hinzufügen über die Klasse ProgramRepositoryJDBC
     **/
    private Program addProgramByJDBC() throws Exception {
       return Dataholder.programRepositoryJDBC.add(descriptionInput.getText());
    }

}
