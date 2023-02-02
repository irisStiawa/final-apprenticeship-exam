package com.lap.roomplanningsystem.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {

    private int locationID;
    private StringProperty description;
    private StringProperty adress;
    private StringProperty postCode;
    private StringProperty city;

    public Location() {
    }

    public Location(int locationID, String description, String adress, String postCode, String city) {
        this.locationID = locationID;
        this.description = new SimpleStringProperty(description);
        this.adress = new SimpleStringProperty(adress);
        this.postCode = new SimpleStringProperty(postCode);
        this.city = new SimpleStringProperty(city);


    }

    @Override
    public String toString() {
        return "Location{" +
                "locationID=" + locationID +
                ", description='" + description + '\'' +
                ", adress='" + adress + '\'' +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getAdress() {
        return adress.get();
    }

    public StringProperty adressProperty() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress.set(adress);
    }

    public String getPostCode() {
        return postCode.get();
    }

    public StringProperty postCodeProperty() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode.set(postCode);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }
}
