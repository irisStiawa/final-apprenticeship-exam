package com.lap.roomplanningsystem.model;

public class RoomEquipment {
    private int roomEquipmentID;
    private Room room;
    private Equipment equipment;

    public RoomEquipment(int roomEquipmentID, Room room, Equipment equipment) {
        this.roomEquipmentID = roomEquipmentID;
        this.room = room;
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "RoomEquipment{" +
                "roomEquipmentID=" + roomEquipmentID +
                ", room=" + room +
                ", equipment=" + equipment +
                '}';
    }

    public int getRoomEquipmentID() {
        return roomEquipmentID;
    }

    public void setRoomEquipmentID(int roomEquipmentID) {
        this.roomEquipmentID = roomEquipmentID;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
