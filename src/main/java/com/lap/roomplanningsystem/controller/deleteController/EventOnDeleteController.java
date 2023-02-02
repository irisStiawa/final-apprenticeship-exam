package com.lap.roomplanningsystem.controller.deleteController;

import java.sql.SQLException;
import java.util.Optional;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EventOnDeleteController extends BaseController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label endLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label roomLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Label numberLabel;

    private Event event;


    @FXML
    void initialize() {


        Optional<Event> optionalEvent = model.getDataholder().getEvents().stream().filter(e -> e == model.getSelectedEventProperty()).findAny();

        if(optionalEvent.isPresent()){
            event = optionalEvent.get();

            numberLabel.setText("V" + event.getEventID());
            descriptionLabel.setText(event.getCourse().getTitle() + "  " + event.getCourse().getProgram().getDescription());
            dateLabel.setText(String.valueOf(event.getDate()));
            startLabel.setText(String.valueOf(event.getStartTime()));
            endLabel.setText(String.valueOf(event.getEndTime()));
            roomLabel.setText(event.getRoom().getDescription());
            locationLabel.setText(event.getRoom().getLocation().getDescription());
        }

    }


    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(numberLabel);
    }

    @FXML
    void onYesButtonClicked(ActionEvent event) throws SQLException {
        model.setSelectedEventProperty(null);

        if(deleteEventByJDBC()){
            model.getDataholder().deleteEvent(this.event);
        }

        closeStage(numberLabel);

    }

    private boolean deleteEventByJDBC() throws SQLException {
        return Dataholder.eventRepositoryJDBC.delete(event);
    }
}
