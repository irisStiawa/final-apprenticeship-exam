package com.lap.roomplanningsystem.model;


import java.sql.Time;
import java.time.LocalDate;

public class Event {
    private int eventID;
    private User creator;
    private Room  room;
    private Course course;
    private User coach;
    private LocalDate date;
    private Time startTime;
    private Time endTime;


    public Event(int eventID, User creator, Room room, Course course, User coach, LocalDate date, Time start, Time end) {
        this.eventID = eventID;
        this.creator = creator;
        this.room = room;
        this.course = course;
        this.coach = coach;
        this.date = date;
        this.startTime = start;
        this.endTime = end;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventID=" + eventID +
                ", creator=" + creator +
                ", room=" + room +
                ", course=" + course +
                ", coach=" + coach +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
