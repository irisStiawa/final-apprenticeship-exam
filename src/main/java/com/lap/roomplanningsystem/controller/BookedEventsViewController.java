package com.lap.roomplanningsystem.controller;

import com.lap.roomplanningsystem.model.Event;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BookedEventsViewController extends BaseController{

    @FXML
    private TableColumn<Event, String> descriptionColumn;

    @FXML
    private TableColumn<Event, String> endColumn;

    @FXML
    private TableColumn<Event, String> numberColumn;

    @FXML
    private TableColumn<Event, String> dateColumn;

    @FXML
    private TableColumn<Event, String> startColumn;

    @FXML
    private TableView<Event> tableView;

    @FXML
    private Label label;

    @FXML
    private TableColumn<Event, String> dateColumn2;

    @FXML
    private TableColumn<Event, String> descriptionColumn2;

    @FXML
    private TableColumn<Event, String> endColumn2;

    @FXML
    private TableColumn<Event, String> numberColumn2;

    @FXML
    private TableColumn<Event, String> startColumn2;

    @FXML
    private TableView<Event> tableView2;



    @FXML
    void initialize() {

        tableView.setItems(model.getBookedEvents());
        tableView2.setItems(model.getNotBookedEvents());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("V" + String.valueOf(dataFeatures.getValue().getEventID())));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCourse().getTitle() + "   " + dataFeatures.getValue().getCourse().getProgram().getDescription()));
        dateColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDate().toString()));
        startColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getStartTime().toString()));
        endColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getEndTime().toString()));

        numberColumn2.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("V" + String.valueOf(dataFeatures.getValue().getEventID())));
        descriptionColumn2.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCourse().getTitle() + "   " + dataFeatures.getValue().getCourse().getProgram().getDescription()));
        dateColumn2.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDate().toString()));
        startColumn2.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getStartTime().toString()));
        endColumn2.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getEndTime().toString()));

    }


    @FXML
    void onAcceptButtonClicked(ActionEvent event) {
        closeStage(label);

    }

}
