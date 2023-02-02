package com.lap.roomplanningsystem.controller;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.filter.EventFilter;
import com.lap.roomplanningsystem.filterBoxes.FilterBox;

import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Event;

import com.lap.roomplanningsystem.searcher.EventSearcher;
import com.lap.roomplanningsystem.searcher.Searcher;
import com.lap.roomplanningsystem.utility.ListUtility;
import com.lap.roomplanningsystem.utility.StringUtility;
import javafx.beans.property.SimpleObjectProperty;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventsViewController extends BaseController{

    @FXML
    private ChoiceBox<String> descriptionChoiceBox;
    @FXML
    private ChoiceBox<String> endChoiceBox;
    @FXML
    private ChoiceBox<String> dateChoiceBox;
    @FXML
    private ChoiceBox<String> numberChoiceBox;
    @FXML
    private ChoiceBox<String> startChoiceBox;
    @FXML
    private TableView<Event> eventTable;
    @FXML
    private TableColumn<Event, String> numberColumn;
    @FXML
    private TableColumn<Event, String> titleColumn;
    @FXML
    private TableColumn<Event, String> dateColumn;
    @FXML
    private TableColumn<Event, String> startColumn;
    @FXML
    private TableColumn<Event, String> endColumn;

    @FXML
    private TextField searchField;
    @FXML
    private ImageView profilImage;
    @FXML
    private Button loginButton;

    private final EventFilter filter = new EventFilter();

    @FXML
    void initialize() throws SQLException {
        if(model.getUser() != null){
            isLogged();
        }

        eventTable.setItems(model.getDataholder().getEvents());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("V" + String.valueOf(dataFeatures.getValue().getEventID())));
        titleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCourse().getTitle() + "   " + dataFeatures.getValue().getCourse().getProgram().getDescription()));
        dateColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDate().toString()));
        startColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getStartTime().toString()));
        endColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getEndTime().toString()));

        eventTable.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) ->  {
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
                    eventTable.getSelectionModel().clearSelection();
                }
            }
        });

        initFilter();
    }

    private void isLogged() {
        loginButton.setText("Logout");
        loginButton.setOnAction(this::onLogoutButtonClicked);
        setProfilImage(profilImage);
    }

    private void initFilter() throws SQLException {
        ArrayList<ObservableList<String>> list = Dataholder.eventRepositoryJDBC.listsForChoiceBox(Constants.CALL_LISTS_FOR_CHOICEBOX_EVENT_FILTER);
        ObservableList<String> times = ListUtility.createTimeValues();

        filter.addFilterBox(new FilterBox(numberChoiceBox, "Nr." , list.get(0)));
        filter.addFilterBox(new FilterBox(descriptionChoiceBox, "Veranstaltung" , list.get(1)));
        filter.addFilterBox(new FilterBox(dateChoiceBox, "Datum" , ListUtility.createDateValues(list.get(2))));
        filter.addFilterBox(new FilterBox(startChoiceBox, "Beginn" , times));
        filter.addFilterBox(new FilterBox(endChoiceBox, "Ende" , times));

        setFilterListenerChoiceBox();

    }





    private void setFilterListenerChoiceBox() {
        filter.getFilterBoxes().addListener(new ListChangeListener<FilterBox>() {
            @Override
            public void onChanged(Change<? extends FilterBox> c) {

                while(c.next()) {
                    FilterBox box = filter.getFilterBoxes().get(c.getFrom());

                    if (c.wasUpdated()) {
                        if(!StringUtility.isBlank(box.getValue())){
                            if(!box.getValue().equals(box.getDefaultValue())){
                                FilteredList<Event> events = filter.setFilterValue(model.getDataholder().getEvents(), box.getChoiceBox().getId(), box.getValue());
                                initEventTable(events);
                            }
                        } else{
                            FilteredList<Event> events = filter.setFilterValue(model.getDataholder().getEvents(), box.getChoiceBox().getId(), box.getValue());
                            initEventTable(events);
                            box.getChoiceBox().setValue(box.getDefaultValue());

                        }
                    }
                }
            }
        });
    }





    private void initEventTable(ObservableList<Event> events) {
        eventTable.setItems(events);
    }


    @FXML
    private void onLoginButtonClicked(ActionEvent actionEvent) {
        model.setPathToView(Constants.PATH_TO_LOGIN_VIEW);
    }



    private void onLogoutButtonClicked(ActionEvent actionEvent){
        logout();
    }

    @FXML
    private void onProfilIconClicked(MouseEvent mouseEvent) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }

    @FXML
    private void onSearch(KeyEvent keyEvent) throws Exception {
        String search = searchField.getText();
        ObservableList<Event> events = filter.getFilteredList(model.getDataholder().getEvents());

        if (!isBlank(search)) {
            Searcher<Event> searcher = new EventSearcher();
            ObservableList<Event> searchResults = searcher.search(search, events);
            initEventTable(searchResults);
        } else {
            initEventTable(events);
        }
    }

}
