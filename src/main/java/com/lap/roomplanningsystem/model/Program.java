package com.lap.roomplanningsystem.model;

public class Program{
    private int programID;
    private String description;

    public Program(int programID, String description) {
        this.programID = programID;
        this.description = description;
    }

    public Program(String description) {
        this.description = description;
    }

    public int getProgramID() {
        return programID;
    }

    public void setProgramID(int programID) {
        this.programID = programID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
