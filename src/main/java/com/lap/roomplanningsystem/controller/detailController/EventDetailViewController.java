package com.lap.roomplanningsystem.controller.detailController;

import java.io.IOException;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EventDetailViewController extends BaseController {

    @FXML
    private Label coachLabel;

    @FXML
    private Label courseLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label endLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label roomLabel;

    @FXML
    private Label startLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    private Event event;



    @FXML
    void initialize() {

        Optional<Event> optionalEvent = model.getDataholder().getEvents().stream().filter(event -> event == model.getSelectedEventProperty()).findAny();

        if(optionalEvent.isPresent()){
            event = optionalEvent.get();

            numberLabel.setText("V" + event.getEventID());
            courseLabel.setText(event.getCourse().getTitle() + " " + event.getCourse().getProgram().getDescription());
            locationLabel.setText(event.getRoom().getLocation().getDescription());
            roomLabel.setText(event.getRoom().getDescription());
            dateLabel.setText(event.getDate().toString());
            startLabel.setText(event.getStartTime().toString());
            endLabel.setText(event.getEndTime().toString());
            coachLabel.setText(event.getCoach().getLastname());
        }

        initAuthorization();

    }


    private void initAuthorization() {
        switch(model.getAuthorization()){
            case "coach" -> initCoachAuthorization();
            case "admin" -> initAdminAuthorization();
        }
    }

    private void initAdminAuthorization() {
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
    }

    private void initCoachAuthorization() {
        if(event.getCreator().getId() == model.getUser().getId()){
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
        }
    }

    @FXML
    void onDeleteButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EVENT_ON_DELETE_VIEW);
        closeStage(numberLabel);

    }

    @FXML
    void onUpdateButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_EVENT_ON_UPDATE_VIEW);
        closeStage(numberLabel);

    }

}