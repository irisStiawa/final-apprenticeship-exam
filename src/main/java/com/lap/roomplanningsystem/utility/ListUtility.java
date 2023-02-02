package com.lap.roomplanningsystem.utility;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.*;
import com.lap.roomplanningsystem.validation.EventValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListUtility {

    public static ObservableList<Room> availableRoomAtLocation(Location location) {
        List<Room> rooms = BaseController.model.getDataholder().getRooms().stream().filter(r -> r.getLocation().getDescription().equals(location.getDescription())).toList();
        return FXCollections.observableArrayList(rooms);
    }

    public static ObservableList<LocalTime> createTimeList() {
        ObservableList<LocalTime> timeList = FXCollections.observableArrayList();

        for(int i = 0; i < 24; i++){
            timeList.add(LocalTime.of(i,0));
            timeList.add(LocalTime.of(i,30));
        }

        return timeList;
    }



    public static ObservableList<Boolean> booleanList() {
        ObservableList<Boolean> booleanList = FXCollections.observableArrayList();
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        return booleanList;
    }

    public static ObservableList<String> authorizationList() {
        ObservableList<String> authorizationList = FXCollections.observableArrayList();
        authorizationList.add("Administrator");
        authorizationList.add("Trainer");
        return authorizationList;
    }

    public static ObservableList<String> createTypList() {
        return FXCollections.observableArrayList("einmalig", "täglich", "wöchentlich", "monatlich");

    }

    public static ObservableList<String> createContractTypList() {
        return FXCollections.observableArrayList("Halbjahresvertrag", "Jahresvertrag");

    }

    public static ObservableList<String> createTimeValues() {
        ObservableList<String> time = FXCollections.observableArrayList();
        time.add("");
        for(int i = 0; i<24; i++){
            if(i<10){
                time.add("0" + i + ":00:00");
            } else {
                time.add(i + ":00:00");
            }
        }

        return time;
    }

    public static ObservableList<String> createDateValues(ObservableList<String> datetime) {
        ObservableList<String> dates = FXCollections.observableArrayList();

        for(String d : datetime){
            if(!StringUtility.isBlank(d)) {
                dates.add(d.substring(0, 10));
            }else {
                dates.add(d);
            }
        }

        return dates;

    }

    public static  ObservableList<Room> handleWithoutTimeValidation(LocalDate date) {
        ObservableList<Room> availableRooms = BaseController.model.getDataholder().getRooms();
        List<Event> eventList = BaseController.model.getDataholder().getEvents().stream().filter(e -> e.getDate().equals(date)).toList();
        if(eventList.size() > 0){
            availableRooms.removeIf(r-> eventList.stream().anyMatch(event -> event.getRoom().getRoomID() == r.getRoomID()));

        }
        return availableRooms;
    }

    public static ObservableList<Room>  handleWithTimeValidation(LocalDate date, LocalTime startTime, LocalTime endTime) {
        ObservableList<Room> availableRooms = BaseController.model.getDataholder().getRooms();
        List<Event> eventList = BaseController.model.getDataholder().getEvents().stream().filter(e -> e.getDate().equals(date)).toList();
        eventList = eventList.stream().filter(e-> !EventValidator.isValidTime(startTime, endTime, e)).toList();


        if(eventList.size() > 0){
            List<Event> finalEventList = eventList;
            availableRooms.removeIf(r-> finalEventList.stream().anyMatch(event -> event.getRoom().getRoomID() == r.getRoomID()));

        }
        return availableRooms;
    }

    public static  ObservableList<User> handleWithoutTimeValidation(User coach, LocalDate date) {
        ObservableList<User> coaches = BaseController.model.getDataholder().getCoaches();
        List<Event> eventList = BaseController.model.getDataholder().getEvents().stream().filter(e -> e.getDate().equals(date)).toList();
        if(eventList.size() > 0){
            coaches.removeIf(c-> eventList.stream().anyMatch(event -> event.getCoach().getId() == c.getId()));

        }

        if(coach!= null){
            coaches.removeIf(c-> coach.getId() != c.getId());
        }
        return coaches;
    }

    public static ObservableList<User>  handleWithTimeValidation(User coach, LocalDate date, LocalTime startTime, LocalTime endTime) {
        ObservableList<User> coaches = BaseController.model.getDataholder().getCoaches();
        List<Event> eventList = BaseController.model.getDataholder().getEvents().stream().filter(e -> e.getDate().equals(date)).toList();
        eventList = eventList.stream().filter(e-> !EventValidator.isValidTime(startTime, endTime, e)).toList();


        if(eventList.size() > 0){
            List<Event> finalEventList = eventList;
            coaches.removeIf(c-> finalEventList.stream().anyMatch(event -> event.getCoach().getId() == c.getId()));
        }

        if(coach!= null){
            coaches.removeIf(c-> coach.getId() != c.getId());
        }

        return coaches;
    }

    public static ObservableList<String> initItems() {
        ObservableList<String> items = FXCollections.observableArrayList();

        items.add("Benutzer");
        items.add("Räume");
        items.add("Veranstaltungen");
        items.add("Kurse");
        items.add("Programme");
        items.add("Standorte");
        items.add("Ausstattung");
        items.add("Raumausstattung");
        items.add("Verträge");

        return items;
    }

    public static HashMap<String, ObservableList<String>> roomRequestList(Location location) {
        HashMap<String, ObservableList<String>> requestLists= new HashMap<>();
        requestLists.put("rooms", FXCollections.observableArrayList());
        requestLists.put("sizes",FXCollections.observableArrayList());
        ObservableList<Room> roomList = availableRoomAtLocation(location);

        for (Room room : roomList){
            requestLists.get("rooms").add(room.getDescription());
            requestLists.get("sizes").add(String.valueOf(room.getMaxPersons()));
        }

        return requestLists;
    }
}
