package com.lap.roomplanningsystem.controller;

import com.calendarfx.model.*;
import com.calendarfx.view.CalendarView;
import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.model.Dataholder;
import com.lap.roomplanningsystem.model.Event;
import com.lap.roomplanningsystem.model.Location;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;

public class CalendarViewController extends BaseController {

    @FXML
    private CalendarView calendarView;
    @FXML
    private ImageView profilImage;
    @FXML
    private Button loginButton;

    Calendar calendar;

    @FXML
    void initialize() {
        if(model.getUser() != null){
            isLogged();
        }

        model.setCalendarView(calendarView);
        calendar = calendarView.getCalendarSources().get(0).getCalendars().get(0);

        initCalendar();

        model.newEventProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observableValue, Event oldEvent, Event newEvent) {
                //Entry created
                if(model.isAddEntry()){
                    accurateEntry(newEvent, model.getNewEntry());
                    model.setNewEntry(null);
                    model.setAddEntry(false);
                }
            }
        });

        model.removeEntryProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldRemoveState, Boolean newRemoveState) {
                if(newRemoveState){
                    calendar.removeEntry(model.getNewEntry());
                    model.setAddEntry(false);
                    model.setRemoveEntry(false);

                }
            }
        });
    }



    private void accurateEntry(Event newEvent, Entry entry) {
        LocalDate d = newEvent.getDate();
        LocalTime start = newEvent.getStartTime().toLocalTime();
        LocalTime end = newEvent.getEndTime().toLocalTime();
        Location location = newEvent.getRoom().getLocation();

        Interval interval = new Interval(LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), start.getHour(), start.getMinute()),
                LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), end.getHour(), end.getMinute()));
        entry.setTitle(newEvent.getCourse().getTitle() + " " + newEvent.getCourse().getProgram().getDescription());
        entry.setLocation(newEvent.getRoom().getDescription() + ": " +location.getDescription() + ", " + location.getAdress() + ", " + location.getPostCode() + " " + location.getCity());
        entry.setInterval(interval);
        entry.setUserObject(newEvent);
    }

    private void isLogged() {
        setProfilImage(profilImage);
        loginButton.setText("Logout");
        loginButton.setOnAction(this::onLogoutButtonClicked);
    }

    private void initCalendar(){
        ObservableList<Event> events = model.getDataholder().getEvents();

        //Handle Authorization
        if(model.getAuthorization().equals("standard")){
            calendar.setReadOnly(true);
        }

        //ADD Entries
        if(events.size() > 0){
            events.forEach(event->{
                Entry<Event> entry = createEntry(event);
                calendar.addEntry(entry);

            });
        }

        handleCalendarEvents();
        handleDetailViews();

    }

    private void handleDetailViews() {
        calendarView.setEntryDetailsPopOverContentCallback(param -> {
            Entry<?> entry = param.getEntry();
            Event event = (Event) entry.getUserObject();
            model.setSelectedEventProperty(event);
            model.setShowInCalendar(true);
            return loadFXMLRootNode(model.getAuthorization().equals("standard")? Constants.PATH_TO_EVENT_DETAIL_VIEW: Constants.PATH_TO_EVENT_ON_UPDATE_VIEW);
        });
    }

    private void handleCalendarEvents() {
        // Calendarevents ADD, DELETE
        calendar.addEventHandler(new EventHandler<CalendarEvent>() {
            @Override
            public void handle(CalendarEvent calendarEvent) {
                if(calendarEvent.isEntryAdded()){
                    try {
                        if (!model.isAddEntry()){
                            model.setNewEntry(calendarEvent.getEntry());
                            model.setAddEntry(true);

                            if(!model.isDetailView() && !model.isLogout()){
                                showNewView(Constants.PATH_TO_EVENT_ON_ADD_VIEW);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(calendarEvent.isEntryRemoved() && !model.isAddEntry()){
                    Entry<?> entry = calendarEvent.getEntry();
                    calendar.removeEntry(entry);
                    Event event = (Event) entry.getUserObject();
                    try {
                        model.setSelectedEventProperty(null);
                        boolean isDeleted = Dataholder.eventRepositoryJDBC.delete(event);
                        if(isDeleted){
                            model.getDataholder().deleteEvent(event);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    private Entry<Event> createEntry(Event event) {
        LocalDate d = event.getDate();
        LocalTime start = event.getStartTime().toLocalTime();
        LocalTime end = event.getEndTime().toLocalTime();
        Location location = event.getRoom().getLocation();

        Interval interval = new Interval(LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), start.getHour(), start.getMinute()),
                LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), end.getHour(), end.getMinute()));
        Entry<Event> entry = new Entry<>(event.getCourse().getTitle() + " " + event.getCourse().getProgram().getDescription(), interval);
        entry.setLocation(event.getRoom().getDescription() + ": " +location.getDescription() + ", " + location.getAdress() + ", " + location.getPostCode() + " " + location.getCity());
        entry.setUserObject(event);


        return entry;
    }



    private void onLogoutButtonClicked(ActionEvent actionEvent){
        logout();
        loginButton.setText("Login");
        loginButton.setOnAction(this:: onLoginButtonClicked);
        removeProfilImage(profilImage);
        initCalendar();
        model.setLogout(false);
    }

    @FXML
    private void onLoginButtonClicked(ActionEvent actionEvent) {
        model.setPathToView(Constants.PATH_TO_LOGIN_VIEW);
    }

    @FXML
    private void onProfilIconClicked(MouseEvent mouseEvent) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }


}
