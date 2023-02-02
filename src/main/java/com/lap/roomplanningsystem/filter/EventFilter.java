package com.lap.roomplanningsystem.filter;

import com.lap.roomplanningsystem.filterBoxes.FilterBox;
import com.lap.roomplanningsystem.model.Event;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EventFilter {

    private String id = "";
    private String description = "";
    private String date = "";
    private String start = "";
    private String end = "";

    ObservableList<FilterBox> filterBoxes= FXCollections.observableArrayList(new Callback<FilterBox, Observable[]>() {
        @Override
        public Observable[] call(FilterBox filterBox) {
            return new Observable[]{filterBox.valueProperty()};
        }
    });

    public FilteredList<Event> getFilteredList(ObservableList<Event> events){
        return filter(events);
    }


    public FilteredList<Event> setFilterValue(ObservableList<Event> events, String id, String newValue){

        switch(id){
            case "eventID": {setId(newValue); break;}
            case "eventDescription": {setDescription(newValue); break;}
            case "eventDate" : {setDate(newValue); break;}
            case "eventStart" : {setStart(newValue); break;}
            case "eventEnd": {setEnd(newValue); break;}
        }

        return filter(events);
    }


    private FilteredList<Event> filter(ObservableList<Event> events) {

        FilteredList<Event> filteredList = new FilteredList<>(events);
        filteredList.setPredicate(createPredicates());

        return filteredList;
    }


    public Predicate<Event> createPredicates(){

        List<Predicate<Event>> predicateList = new ArrayList<>();


        if(!isBlank(id)){
            predicateList.add(e -> String.valueOf(e.getEventID()).equals(id));
        }

        if(!isBlank(description)){
            predicateList.add(e -> (e.getCourse().getProgram().getDescription()).equals(description));
        }

        if(!isBlank(date)){
            predicateList.add(e -> e.getDate().toString().equals(date));
        }

        if(!isBlank(start)){
            predicateList.add(e -> e.getStartTime().toString().equals(start));
        }

        if(!isBlank(end)){
            predicateList.add(e -> e.getEndTime().toString().equals(end));
        }



        return predicateList.stream().reduce(r -> true, Predicate::and);
    }

    private boolean isBlank(String s) {
        return s.equals("");
    }


    public void addFilterBox(FilterBox filterBox){
        filterBoxes.add(filterBox);
    }


    public ObservableList<FilterBox> getFilterBoxes() {
        return filterBoxes;
    }

    public void setFilterBoxes(ObservableList<FilterBox> filterBoxes) {
        this.filterBoxes = filterBoxes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }


}
