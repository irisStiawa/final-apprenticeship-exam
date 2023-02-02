package com.lap.roomplanningsystem.converter;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Location;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class LocationConverter implements ConverterInterface<Location> {

    private ObservableList<Location> locationList;


    public LocationConverter() {
        this.locationList = BaseController.model.getDataholder().getLocations();

    }



    @Override
    public void setConverter(ComboBox<Location> box) {

        box.setConverter(new StringConverter<Location>() {
            @Override
            public String toString(Location location) {
                return location == null ? "Standort" : location.getDescription();
            }

            @Override
            public Location fromString(String s) {
                return matchByString(s);
            }
        });
    }

    @Override
    public Location matchByString(String s) {
        for(Location l : locationList){
            if(l.getDescription().equals(s)){
                return l;
            }
        }
        return null;
    }
}
