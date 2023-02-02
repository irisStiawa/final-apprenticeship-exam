package com.lap.roomplanningsystem.validation;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.*;
import com.lap.roomplanningsystem.utility.ListUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class RequestValidator {
    private  String location = "";
    private  String room = "";
    private  Boolean roomDisable = true;
    private  String size = "";
    private  String equipment = "";
    private  String course = "";
    private  String user = "";
    private LocalDate date;
    private LocalTime startTime;
    private  LocalTime endTime;


    public Predicate<Room> filter() {
        List<Predicate<Room>> predicateList = new ArrayList<>();

        if(!isBlank(location)) {
            predicateList.add(r -> r.getLocation().getDescription().equals(location));
        }

        if(!isBlank(room) && !roomDisable){
            predicateList.add(r -> room.equals(r.getDescription()));

        }

        if(!isBlank(size)){
            predicateList.add(r -> r.getMaxPersons() >= Integer.parseInt(size));

        }



        if(date != null){
            ObservableList<Room> rooms = FXCollections.observableArrayList();

            if(startTime != null){
                rooms = ListUtility.handleWithTimeValidation(date, startTime, endTime);
            } else {
                rooms = ListUtility.handleWithoutTimeValidation(date);
            }


            predicateList.add(rooms::contains);
        }

        //TODO: korrigieren
        if(!isBlank(course) && date != null && startTime != null) {
            EventValidator eventValidator = new EventValidator();
            boolean coll = eventValidator.courseCollision(course, date, startTime, endTime);
            if(coll){
                predicateList.add(r -> false);
            }
        }

        if(!isBlank(equipment)){
            Room matchRoom = null;
            for(RoomEquipment roomEquipment : BaseController.model.getDataholder().getRoomEquipments()){
                if(roomEquipment.getEquipment().getDescription().equals(equipment)){
                    matchRoom = roomEquipment.getRoom();
                    break;
                }
            }

            if(matchRoom != null){
                Room finalMatchRoom = matchRoom;
                predicateList.add(r -> r.getRoomID() == finalMatchRoom.getRoomID());
            } else {
                predicateList.add(r -> false);
            }

        }

        if(!isBlank(user)){
            Optional<User> optionalUser = BaseController.model.getDataholder().getCoaches().stream().filter(c-> user.equals(c.getFirstname() + " " + c.getLastname())).findAny();
            User coach = optionalUser.orElse(null);
            ObservableList<User> coaches = FXCollections.observableArrayList();

            if(startTime != null){
                coaches = ListUtility.handleWithTimeValidation(coach, date, startTime, endTime);
            } else {
                coaches = ListUtility.handleWithoutTimeValidation(coach, date);
            }

            BaseController.model.setRequestResultCoaches(coaches);

        } else {
            ObservableList<User> coaches = FXCollections.observableArrayList();

            if(startTime != null){
                coaches = ListUtility.handleWithTimeValidation(null, date, startTime, endTime);
            } else {
                coaches = ListUtility.handleWithoutTimeValidation(null, date);
            }

            BaseController.model.setRequestResultCoaches(coaches);

        }

        Predicate<Room> combinedPredicate = predicateList.stream().reduce(r -> true, Predicate::and);

        return combinedPredicate;
    }




    private boolean isBlank(String s){
        return s == null || s.equals("");
    }

    public Boolean getRoomDisable() {
        return roomDisable;
    }

    public void setRoomDisable(Boolean roomDisable) {
        this.roomDisable = roomDisable;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
