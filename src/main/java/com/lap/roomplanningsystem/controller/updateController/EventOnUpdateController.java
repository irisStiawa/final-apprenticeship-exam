package com.lap.roomplanningsystem.controller.updateController;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.converter.CourseConverter;
import com.lap.roomplanningsystem.converter.LocationConverter;
import com.lap.roomplanningsystem.converter.RoomConverter;
import com.lap.roomplanningsystem.converter.UserConverter;
import com.lap.roomplanningsystem.model.*;
import com.lap.roomplanningsystem.utility.ListUtility;
import com.lap.roomplanningsystem.validation.DateValidator;
import com.lap.roomplanningsystem.validation.EventValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EventOnUpdateController extends BaseController {

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
    private Label numberLabel;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<Room> roomComboBox;

    @FXML
    private ComboBox<LocalTime> startComboBox;

    private Event event;


    @FXML
    void initialize() {

        Optional<Event> optionalEvent = model.getDataholder().getEvents().stream().filter(e -> e == model.getSelectedEventProperty()).findAny();

        if (optionalEvent.isPresent()) {
            event = optionalEvent.get();

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
    }

    private void initFields() {
        ObservableList<LocalTime> timeList = ListUtility.createTimeList();
        numberLabel.setText("V" + String.valueOf(event.getEventID()));
        locationComboBox.setItems(model.getDataholder().getLocations());
        coachComboBox.setItems(model.getDataholder().getCoaches());
        courseComboBox.setItems(model.getDataholder().getCourses());
        roomComboBox.setItems(ListUtility.availableRoomAtLocation(event.getRoom().getLocation()));
        startComboBox.setItems(timeList);
        endComboBox.setItems(timeList);
        locationComboBox.getSelectionModel().select(event.getRoom().getLocation());
        roomComboBox.getSelectionModel().select(event.getRoom());
        coachComboBox.getSelectionModel().select(event.getCoach());
        courseComboBox.getSelectionModel().select(event.getCourse());
        datePicker.setValue(event.getDate());
        startComboBox.getSelectionModel().select(event.getStartTime().toLocalTime());
        endComboBox.getSelectionModel().select(event.getEndTime().toLocalTime());
    }


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

    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {
        EventValidator eventValidator = new EventValidator();
        eventValidator.setUpdateEvent(this.event);

        if(validateFields() && eventValidator.validSingle(roomComboBox.getValue(), courseComboBox.getValue(), coachComboBox.getValue(), datePicker.getValue(), startComboBox.getValue(),endComboBox.getValue())){

            this.event.setCourse(courseComboBox.getValue());
            this.event.setRoom(roomComboBox.getValue());
            this.event.setCoach(coachComboBox.getValue());
            this.event.setDate(datePicker.getValue());
            this.event.setStartTime(Time.valueOf(startComboBox.getValue()));
            this.event.setEndTime(Time.valueOf(endComboBox.getValue()));


            boolean isUpdated = updateEventByJDBC();

            if(isUpdated){
                int index = model.getDataholder().getEvents().indexOf(this.event);
                model.getDataholder().updateEvent(index, this.event);

                if(!model.isShowInCalendar()){
                    showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                    closeStage(errorLabel);

                } else {
                    model.setShowInCalendar(false);
                    saveButton.setDisable(true);
//                    model.getCalendarView().
                }
            }
        } else {
            errorLabel.setText(eventValidator.getErrString());
        }
    }

    private boolean validateFields() {
        return !emptyFields() && validateDate();
    }


    private boolean emptyFields() {
        boolean empty = courseComboBox.getValue() == null || locationComboBox.getValue() == null || roomComboBox.getValue() == null ||
                datePicker.getValue() == null || startComboBox.getValue() == null || endComboBox.getValue() == null || coachComboBox.getValue() == null;

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS_AND_BOXES);
        }

        return empty;
    }

    private boolean validateDate(){
        if(DateValidator.validDate(datePicker.getValue())){
            if(DateValidator.validTime(startComboBox.getValue(), endComboBox.getValue())){
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



    private boolean updateEventByJDBC() throws Exception {
        return Dataholder.eventRepositoryJDBC.update(event);
    }
}
