package com.lap.roomplanningsystem.model;

import java.sql.Date;

public class Course{

    private int courseID;
    private Program program;
    private String title;
    private int members;
    private Date start;
    private Date end;

    public Course(int courseID, Program program, String title, int members, Date start, Date end) {
        this.courseID = courseID;
        this.program = program;
        this.title = title;
        this.members = members;
        this.start = start;
        this.end = end;
    }

    public Course() {

    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


}
