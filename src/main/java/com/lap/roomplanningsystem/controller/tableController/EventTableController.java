package com.lap.roomplanningsystem.controller.tableController;

import java.io.IOException;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Event;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EventTableController extends BaseController {



    @FXML
    private TableColumn<Event, String> dateColumn;

    @FXML
    private TableColumn<Event, String> endColumn;

    @FXML
    private TableColumn<Event, String> numberColumn;

    @FXML
    private TableColumn<Event, String> startColumn;

    @FXML
    private TableView<Event> tableView;

    @FXML
    private TableColumn<Event, String> titleColumn;




    @FXML
    void initialize() {
        tableView.setItems(model.getDataholder().getEvents());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("V" + String.valueOf(dataFeatures.getValue().getEventID())));
        titleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCourse().getTitle() + "   " + dataFeatures.getValue().getCourse().getProgram().getDescription()));
        dateColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDate().toString()));
        startColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getStartTime().toString()));
        endColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getEndTime().toString()));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) ->  {

            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedEventProperty(nv);
                    showNewView(Constants.PATH_TO_EVENT_DETAIL_VIEW);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        model.selectedEventProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observableValue, Event oldEvent, Event newEvent) {
                if(newEvent == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });

    }


}
