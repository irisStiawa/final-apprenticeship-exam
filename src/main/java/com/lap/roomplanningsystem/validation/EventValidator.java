package com.lap.roomplanningsystem.validation;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Predicate;

public class EventValidator {
    private ObservableList<Event> events;
    private Event updateEvent = null;
    ObservableList<LocalDate> valid = FXCollections.observableArrayList();
    ObservableList<LocalDate> inValid = FXCollections.observableArrayList();
    private String errString;

    private Room selectedRoom;
    private Course selectedCourse;
    private LocalTime selectedStart;
    private LocalTime selectedEnd;
    private User selectedCoach;


    public EventValidator() {
        this.events = BaseController.model.getDataholder().getEvents();
    }


    public boolean validSingle(Room room, Course course, User coach, LocalDate day, LocalTime start, LocalTime end){
        setSelection(room,course, coach, start,end);
        return collision(day) && withinCourse(day);
    }

    public boolean validSeries(Room room, Course course, User coach,ObservableList<LocalDate> days, LocalTime start, LocalTime end){
        setSelection(room,course, coach, start,end);
        for(LocalDate day : days){
            if(collision(day) && withinCourse(day)){
                valid.add(day);
            } else{
                inValid.add(day);
            }
        }
        return valid.size() > 0 || inValid.size() > 0;
    }

    public boolean collision(LocalDate day){
        FilteredList<Event> filteredList = new FilteredList<>(events);
        Predicate<Event> predicateForDay = event -> event.getDate().equals(day);
        filteredList.setPredicate(predicateForDay);
//        boolean isAvailable = false;

        for(Event event: filteredList){
            boolean verify = updateEvent == null || event.getEventID() != updateEvent.getEventID() ;

            if(!availablity(verify, event)){
                return false;
            }
        }

        return true;
    }

    private boolean availablity(boolean verify, Event event) {
        if(verify && isRoomNotAvailable(event)){
            setErrString(Constants.ROOM_NOT_AVAILABLE);
            return false;
        } else {
            if(verify && !isValidTime(event.getStartTime(), event.getEndTime()) && isCourseRegistered(event)){
                setErrString(Constants.EVENT_EXIST);
                return false;
            } else if(verify && sameCoach(event) && !isValidTime(event.getStartTime(), event.getEndTime())){
                setErrString(Constants.TRAINER_NOT_AVAILABLE);
                return false;
            }
        }
        return true;
    }


    public boolean isRoomNotAvailable(Event event){
       return event.getRoom().getRoomID() == selectedRoom.getRoomID() && !isValidTime(event.getStartTime(), event.getEndTime());
    }

    private boolean isCourseRegistered(Event event) {
        return event.getCourse().getCourseID() == selectedCourse.getCourseID();
    }

    private boolean sameCoach(Event event) {
        return event.getCoach().getId() == selectedCoach.getId();
    }

    private boolean isValidTime(Time start, Time end) {
        boolean valid = selectedStart.isBefore(start.toLocalTime()) ? selectedEnd.isBefore(start.toLocalTime()) || selectedEnd.equals(start.toLocalTime()) : selectedStart.isAfter(end.toLocalTime()) || selectedStart.equals(end.toLocalTime());

        if (!valid){
            setErrString("Veranstaltungszeiten überschneiden sich!");
        }
        return valid;
    }

    public static boolean isValidTime(LocalTime start, LocalTime end, Event event) {
        return start.isBefore(event.getStartTime().toLocalTime()) ? end.isBefore(event.getStartTime().toLocalTime()) || end.equals(event.getStartTime().toLocalTime()) : start.isAfter(event.getEndTime().toLocalTime()) || start.equals(event.getEndTime().toLocalTime());

    }


    private boolean withinCourse(LocalDate day) {
        boolean valid = day.isAfter(selectedCourse.getStart().toLocalDate()) && day.isBefore(selectedCourse.getEnd().toLocalDate());
        if (!valid){
            setErrString("Datum außerhalb des Kursdatums");
        }
        return valid;
    }

    public boolean courseCollision(String course, LocalDate date, LocalTime startTime, LocalTime endTime) {
        FilteredList<Event> filteredList = new FilteredList<>(events);
        Predicate<Event> predicateForDay = event -> event.getDate().equals(date);
        filteredList.setPredicate(predicateForDay);


        for(Event event: filteredList){
            if(course.equals(event.getCourse().getTitle() + " " + event.getCourse().getProgram().getDescription()) && !isValidTime(startTime, endTime, event)){
                return true;
            }
        }

        return false;
    }

    public String getErrString() {
        return errString;
    }

    public void setErrString(String errString) {
        this.errString = errString;
    }

    public ObservableList<LocalDate> getValid() {
        return valid;
    }

    public void setValid(ObservableList<LocalDate> valid) {
        this.valid = valid;
    }

    public ObservableList<LocalDate> getInValid() {
        return inValid;
    }

    public void setInValid(ObservableList<LocalDate> inValid) {
        this.inValid = inValid;
    }

    public Event getUpdateEvent() {
        return updateEvent;
    }

    public void setUpdateEvent(Event updateEvent) {
        this.updateEvent = updateEvent;
    }

    private void setSelection(Room room, Course course, User coach, LocalTime start, LocalTime end){
        this.selectedRoom = room;
        this.selectedCourse = course;
        this.selectedCoach = coach;
        this.selectedStart = start;
        this.selectedEnd = end;
    }


}
