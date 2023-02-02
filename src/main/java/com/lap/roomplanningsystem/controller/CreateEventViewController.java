package com.lap.roomplanningsystem.controller;

import java.io.IOException;
import java.time.*;

import com.lap.roomplanningsystem.app.Constants;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CreateEventViewController extends BaseController{


    @FXML
    private ComboBox<User> coachComboBox;

    @FXML
    private ComboBox<Course> courseComboBox;

    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private ComboBox<Room> roomComboBox;

    @FXML
    private DatePicker seriesEndDatePicker;

    @FXML
    private ComboBox<LocalTime> seriesEndTimeComboBox;

    @FXML
    private DatePicker seriesStartDatePicker;

    @FXML
    private ComboBox<LocalTime> seriesStartTimeComboBox;

    @FXML
    private ComboBox<LocalTime> singleStartTimeComboBox;

    @FXML
    private DatePicker singleDatePicker;

    @FXML
    private ComboBox<LocalTime> singleEndTimeComboBox;

    @FXML
    private ComboBox<String> typComboBox;

    @FXML
    private Label errorLabel;

    @FXML
    private Label resultLabel;

    boolean isOneTime = true;
    @FXML
    private ImageView profilImage;


    @FXML
    void initialize() {

        if(model.getUser()!= null){
            setProfilImage(profilImage);
        }

        locationComboBox.setItems(model.getDataholder().getLocations());
        coachComboBox.setItems(model.getDataholder().getCoaches());
        typComboBox.setItems(ListUtility.createTypList());
        courseComboBox.setItems(model.getDataholder().getCourses());

        ObservableList<LocalTime> timeList = ListUtility.createTimeList();
        singleStartTimeComboBox.setItems(timeList);
        singleEndTimeComboBox.setItems(timeList);
        seriesStartTimeComboBox.setItems(timeList);
        seriesEndTimeComboBox.setItems(timeList);

        locationComboBox.setPromptText("Standort");
        coachComboBox.setPromptText("Trainer");
        typComboBox.setPromptText("Termintyp");
        courseComboBox.setPromptText("Kurse");
        singleStartTimeComboBox.setPromptText("Beginn");
        singleEndTimeComboBox.setPromptText("Ende");
        seriesStartTimeComboBox.setPromptText("Beginn");
        seriesEndTimeComboBox.setPromptText("Ende");


        initListener();
        initConverter();

    }

    private void initListener() {
        locationComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location location, Location newLocation) {
                roomComboBox.setDisable(false);
                roomComboBox.setItems(ListUtility.availableRoomAtLocation(locationComboBox.getValue()));
                roomComboBox.setPromptText("Raum");

            }
        });

        typComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                switch (newValue){
                    case "einmalig" -> setOnTimeAreaVisable();
                    default -> setSeriesAreaVisable();
                }
            }

        });

        model.selectedResultProperty().addListener(new ChangeListener<Result>() {
            @Override
            public void changed(ObservableValue<? extends Result> observableValue, Result oldResult, Result newResult) {
                if(model.getSelectedResultProperty() != null){
                    locationComboBox.setValue(newResult.room().getLocation());
                    roomComboBox.setValue(newResult.room());
                    coachComboBox.setValue(newResult.coach());


                }
            }
        });
    }

    private void initConverter() {
        LocationConverter locationConverter = new LocationConverter();
        locationConverter.setConverter(locationComboBox);

        UserConverter userConverter = new UserConverter();
        userConverter.setConverter(coachComboBox);

        RoomConverter roomConverter = new RoomConverter(locationComboBox);
        roomConverter.setConverter(roomComboBox);

        CourseConverter courseConverter = new CourseConverter();
        courseConverter.setConverter(courseComboBox);
    }


    private void setSeriesAreaVisable() {
        isOneTime = false;
        singleDatePicker.setDisable(true);
        singleStartTimeComboBox.setDisable(true);
        singleEndTimeComboBox.setDisable(true);

        seriesStartDatePicker.setDisable(false);
        seriesEndDatePicker.setDisable(false);
        seriesStartTimeComboBox.setDisable(false);
        seriesEndTimeComboBox.setDisable(false);
    }

    private void setOnTimeAreaVisable() {
        isOneTime = true;
        singleDatePicker.setDisable(false);
        singleStartTimeComboBox.setDisable(false);
        singleEndTimeComboBox.setDisable(false);

        seriesStartDatePicker.setDisable(true);
        seriesEndDatePicker.setDisable(true);
        seriesStartTimeComboBox.setDisable(true);
        seriesEndTimeComboBox.setDisable(true);
    }

    @FXML
    void onLogoutButtonClicked(ActionEvent actionEvent) {
        logout();

    }

    @FXML
    private void onRequestButtonClicked(ActionEvent actionEvent) throws IOException {
        showNewView(Constants.PATH_TO_REQUEST_VIEW);
    }

    @FXML
    private void onSaveButtonClicked(ActionEvent actionEvent) throws Exception {
        errorLabel.setText("");
        resultLabel.setText("");
        model.setBookedEvents(FXCollections.observableArrayList());
        model.setNotBookedEvents(FXCollections.observableArrayList());

        if(validateFields()){
            execute();
        }
    }

    private boolean validateFields() {
        return !emptyFields() && validateSize() && !emptyDateFields() && validateDate();
    }

    private boolean emptyFields() {
        boolean empty = locationComboBox.getValue() == null || roomComboBox.getValue() == null ||
                courseComboBox.getValue() == null || coachComboBox.getValue() == null;

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS_AND_BOXES);
        }

        return empty;
    }

    private boolean emptyDateFields() {
        boolean empty = isOneTime ?  singleDatePicker.getValue() == null || singleStartTimeComboBox.getValue() == null ||
                singleEndTimeComboBox.getValue() == null: seriesStartDatePicker.getValue() == null || seriesEndDatePicker.getValue() == null ||
                seriesStartTimeComboBox.getValue() == null || seriesEndDatePicker.getValue() == null;

        if(empty) {
            errorLabel.setText(Constants.EMPTY_FIELDS_AND_BOXES);
        }

        return empty;
    }

    private boolean validateDate(){
        return isOneTime ?  validateSingleFields() : validateSeriesFields();
    }

    private boolean validateSingleFields(){
        if(DateValidator.validDate(singleDatePicker.getValue())){
            if(DateValidator.validTime(singleStartTimeComboBox.getValue(), singleEndTimeComboBox.getValue())){
                return true;
            } else {
                errorLabel.setText(Constants.ENDTIME_BEFORE_STARTTIME);
                return false;
            }
        } else{
            errorLabel.setText(Constants.DATE_IN_PAST);
            return false;
        }

    }

    private boolean validateSeriesFields(){
        if(DateValidator.validDate(seriesStartDatePicker.getValue()) || DateValidator.validDate(seriesEndDatePicker.getValue())){
            if(DateValidator.validSerie(seriesStartDatePicker.getValue(), seriesEndDatePicker.getValue())){
                if(DateValidator.validTime(seriesStartTimeComboBox.getValue(), seriesEndTimeComboBox.getValue())){
                    return true;
                } else {
                    errorLabel.setText(Constants.ENDTIME_BEFORE_STARTTIME);
                    return false;
                }
            } else{
                errorLabel.setText(Constants.ENDDATE_BEFORE_STARTDATE);
                return false;
            }
        } else {
            errorLabel.setText(Constants.DATE_IN_PAST);
            return false;
        }
    }

    private boolean validateSize() {
        boolean valid = courseComboBox.getValue().getMembers() <= roomComboBox.getValue().getMaxPersons();

        if(!valid){
            errorLabel.setText(Constants.OVERSIZED_MEMBERS);
            return false;
        }
        return valid;
    }

    private void execute() throws Exception {
        if(isOneTime){
            executeSingle();
        } else {
            executeSeries();
        }
    }

    public void executeSingle() throws Exception {
        EventValidator eventValidator = new EventValidator();

        if(DateValidator.oneTime(singleDatePicker.getValue())){
            if(eventValidator.validSingle(roomComboBox.getValue(),courseComboBox.getValue(), coachComboBox.getValue(), singleDatePicker.getValue(), singleStartTimeComboBox.getValue(), singleEndTimeComboBox.getValue())){
                LocalDateTime start = DateFormattorJDBC.format(singleDatePicker.getValue(), singleStartTimeComboBox.getValue());
                LocalDateTime end = DateFormattorJDBC.format(singleDatePicker.getValue(), singleEndTimeComboBox.getValue());

                Event event = Dataholder.eventRepositoryJDBC.add(model.getUser(),roomComboBox.getValue(),courseComboBox.getValue(),
                        coachComboBox.getValue(), singleDatePicker.getValue(), start, end);


                if(event != null){
                    model.getDataholder().addEvent(event);
                    errorLabel.setText("");
                    resultLabel.setText(Constants.EVENT_IS_BOOKED);
                }
            } else {
                errorLabel.setText(eventValidator.getErrString());
            }
        } else {
            errorLabel.setText(Constants.DAY_IS_NOT_ALLOWED);
        }

    }

    public void executeSeries() throws Exception {
         EventValidator eventValidator= handleDates(typComboBox.getValue());

         boolean valid = handleValidEvents(eventValidator);
         boolean inValid = handleInValidEvents(eventValidator);
         if( valid|| inValid){
             showNewView(Constants.PATH_TO_BOOKED_EVENTS_VIEW);
         }

    }

    private boolean handleValidEvents(EventValidator eventValidator) throws Exception {

        if(eventValidator.getValid().size() > 0){
            ObservableList<Event> validEvents = FXCollections.observableArrayList();

            for( LocalDate day : eventValidator.getValid()){
                LocalDateTime start = DateFormattorJDBC.format(day, seriesStartTimeComboBox.getValue());
                LocalDateTime end = DateFormattorJDBC.format(day, seriesEndTimeComboBox.getValue());

                Event validEvent = Dataholder.eventRepositoryJDBC.add(model.getUser(),roomComboBox.getValue(),courseComboBox.getValue(),
                        coachComboBox.getValue(), day, start, end);

                validEvents.add(validEvent);
            }

            if(validEvents.size() > 0){
                model.getDataholder().addEvents(validEvents);
                model.setBookedEvents(validEvents);
                resultLabel.setText(validEvents.size() + Constants.SERIES_EVENT_IS_BOOKED);
                return true;
            }

        }
        return false;
    }

    private boolean handleInValidEvents(EventValidator eventValidator) throws Exception {
        if(eventValidator.getInValid().size() > 0){
            ObservableList<Event> inValidEvents;

            inValidEvents = Dataholder.eventRepositoryJDBC.compileEvents(model.getUser(),roomComboBox.getValue(),courseComboBox.getValue(),
                    coachComboBox.getValue(), eventValidator.getInValid(), seriesStartTimeComboBox.getValue(), seriesEndTimeComboBox.getValue());

            model.setNotBookedEvents(inValidEvents);
            return true;
        }
        return false;
    }


    private EventValidator handleDates(String serie) {
        EventValidator eventValidator = new EventValidator();

        boolean validEvents = switch (serie){
            case "täglich" -> eventValidator.validSeries(roomComboBox.getValue(), courseComboBox.getValue(), coachComboBox.getValue(), DateValidator.daily(seriesStartDatePicker.getValue(), seriesEndDatePicker.getValue()),seriesStartTimeComboBox.getValue(), seriesEndTimeComboBox.getValue());
            case "wöchentlich" -> eventValidator.validSeries(roomComboBox.getValue(), courseComboBox.getValue(), coachComboBox.getValue(), DateValidator.weekly(seriesStartDatePicker.getValue(), seriesEndDatePicker.getValue()),seriesStartTimeComboBox.getValue(), seriesEndTimeComboBox.getValue());
            case "monatlich" -> eventValidator.validSeries(roomComboBox.getValue(), courseComboBox.getValue(), coachComboBox.getValue(), DateValidator.monthly(seriesStartDatePicker.getValue(), seriesEndDatePicker.getValue()),seriesStartTimeComboBox.getValue(), seriesEndTimeComboBox.getValue());
            default -> false;
        };

        if(!validEvents){
            errorLabel.setText(eventValidator.getErrString());
        }

        return eventValidator;
    }

    @FXML
    private void onProfilIconClicked(MouseEvent mouseEvent) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }
}
