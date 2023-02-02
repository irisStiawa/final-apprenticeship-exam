package com.lap.roomplanningsystem.searcher;

import com.lap.roomplanningsystem.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventSearcher implements Searcher<Event>{

    @Override
    public ObservableList<Event> search(String s, ObservableList<Event> list) {
        ObservableList<Event> events = FXCollections.observableArrayList();

        for (Event e : list) {
            if (("V" + String.valueOf(e.getEventID())).toLowerCase().contains(s.toLowerCase()) || e.getCourse().getTitle().toLowerCase().contains(s.toLowerCase()) ||
                    e.getCourse().getProgram().getDescription().toLowerCase().contains(s.toLowerCase()) || String.valueOf(e.getDate()).toLowerCase().contains(s.toLowerCase()) ||
                    String.valueOf(e.getStartTime()).toLowerCase().contains(s.toLowerCase()) || String.valueOf(e.getEndTime()).toLowerCase().contains(s.toLowerCase())) {
                events.add(e);
            }
        }

        return events;
    }
}
