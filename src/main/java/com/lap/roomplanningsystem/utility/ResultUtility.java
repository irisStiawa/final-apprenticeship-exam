package com.lap.roomplanningsystem.utility;

import com.lap.roomplanningsystem.model.Result;
import com.lap.roomplanningsystem.model.Room;
import com.lap.roomplanningsystem.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ResultUtility {
    ObservableList<Room> rooms;
    ObservableList<User> coaches;
    ObservableList<Result> results = FXCollections.observableArrayList();

    public ResultUtility(ObservableList<Room> rooms, ObservableList<User> coaches) {
        this.rooms = rooms;
        this.coaches = coaches;

    }

    public void createResults() {
        if(coaches != null){
            rooms.forEach(r -> coaches.forEach(c -> results.add(new Result(r, c))));
        } else {
            rooms.forEach(r -> results.add(new Result(r, null)));
        }
    }


    public ObservableList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ObservableList<Room> rooms) {
        this.rooms = rooms;
    }

    public ObservableList<User> getCoaches() {
        return coaches;
    }

    public void setCoaches(ObservableList<User> coaches) {
        this.coaches = coaches;
    }

    public ObservableList<Result> getResults() {
        return results;
    }

    public void setResults(ObservableList<Result> results) {
        this.results = results;
    }
}
