package com.lap.roomplanningsystem.searcher;

import com.lap.roomplanningsystem.model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomSearcher implements Searcher<Room> {

    @Override
    public ObservableList<Room> search(String s, ObservableList<Room> list) {
        ObservableList<Room> rooms = FXCollections.observableArrayList();

        for (Room r : list) {
            if (("R" + String.valueOf(r.getRoomID())).toLowerCase().contains(s.toLowerCase()) || r.getDescription().toLowerCase().contains(s.toLowerCase())
                    || String.valueOf(r.getMaxPersons()).toLowerCase().contains(s.toLowerCase()) || r.getLocation().getDescription().toLowerCase().contains(s.toLowerCase())) {
                rooms.add(r);
            }
        }

        return rooms;
    }
}