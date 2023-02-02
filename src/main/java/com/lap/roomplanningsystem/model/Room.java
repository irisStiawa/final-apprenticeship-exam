package com.lap.roomplanningsystem.model;

import java.util.Arrays;

public class Room {

    private int roomID;
    private String description;
    private Location location;
    private int maxPersons;
    private byte[] photo;


    public Room(int roomID, String description, Location location, int maxPersons, byte[] photo) {
        this.roomID = roomID;
        this.description = description;
        this.location = location;
        this.maxPersons = maxPersons;
        this.photo = photo;

    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", description='" + description + '\'' +
                ", location=" + location +
                ", maxPersons=" + maxPersons +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMaxPersons() {
        return maxPersons;
    }

    public void setMaxPersons(int maxPersons) {
        this.maxPersons = maxPersons;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }


}
