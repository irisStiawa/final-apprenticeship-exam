package com.lap.roomplanningsystem.model;

public class Equipment{

    private int equipmentID;
    private String description;

    public Equipment(int equipmentID, String description) {
        this.equipmentID = equipmentID;
        this.description = description;
    }

    public Equipment() {

    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
