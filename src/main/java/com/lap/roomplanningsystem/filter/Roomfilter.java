package com.lap.roomplanningsystem.filter;

import com.lap.roomplanningsystem.filterBoxes.FilterBox;
import com.lap.roomplanningsystem.filterBoxes.FilterCheckBox;
import com.lap.roomplanningsystem.model.Room;
import com.lap.roomplanningsystem.model.RoomEquipment;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.lap.roomplanningsystem.controller.BaseController.model;

public class Roomfilter {

    private FilterCheckBox imageCheckBox;

    private String id = "";
    private String description = "";
    private String size = "";
    private String location = "";
    private String equipment = "";
    private boolean image;
    private FilteredList<Room> filteredList;

    ObservableList<FilterBox> filterBoxes= FXCollections.observableArrayList(new Callback<FilterBox, Observable[]>() {
        @Override
        public Observable[] call(FilterBox filterBox) {
            return new Observable[]{filterBox.valueProperty()};
        }
    });


    public Roomfilter() {
    }

    public FilteredList<Room> filterValue (String id, String newValue) {
        switch (id) {
            case "roomID": {
                setId(newValue);
                break;
            }
            case "roomDescription": {
                setDescription(newValue);
                break;
            }
            case "roomSize": {
                setSize(newValue);
                break;
            }
            case "roomLocation": {
                setLocation(newValue);
                break;
            }
            case "roomEquipment": {
                setEquipment(newValue);
                break;
            }
        }

        return filter();
    }

    public FilteredList<Room> filter() {

        FilteredList<Room> filteredList = new FilteredList<>(model.getDataholder().getRooms());
        filteredList.setPredicate(createPredicates());

        this.filteredList = filteredList;

        return filteredList;
    }



    private Predicate<Room> createPredicates() {

        List<Predicate<Room>> predicateList = new ArrayList<>();


        if(!isBlank(id)){
            predicateList.add(r -> String.valueOf(r.getRoomID()).equals(id));
        }

        if(!isBlank(description)){
            predicateList.add(r -> r.getDescription().equals(description));
        }

        if(!isBlank(size)){
            predicateList.add(r -> String.valueOf(r.getMaxPersons()).equals(size));
        }

        if(!isBlank(location)){
            predicateList.add(r -> r.getLocation().getDescription().equals(location));
        }

        if(!isBlank(equipment)){
            List<RoomEquipment> list = filterEquipment();
            predicateList.add(r -> list.stream().anyMatch(re -> r.getRoomID() == re.getRoom().getRoomID()));
        }

        if(image){
            predicateList.add(r -> r.getPhoto() != null);
        }


        return predicateList.stream().reduce(r -> true, Predicate::and);

    }

    private List<RoomEquipment> filterEquipment() {
        return model.getDataholder().getRoomEquipments().stream().filter(re -> re.getEquipment().getDescription().equals(equipment)).toList();
    }


    private boolean isBlank(String s){
        return s.equals("");
    }

    public void addFilterBox(FilterBox filterBox){
        filterBoxes.add(filterBox);
    }

    public FilterCheckBox getImageCheckBox() {
        return imageCheckBox;
    }

    public void setImageCheckBox(FilterCheckBox imageCheckBox) {
        this.imageCheckBox = imageCheckBox;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public FilteredList<Room> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(FilteredList<Room> filteredList) {
        this.filteredList = filteredList;
    }
}

