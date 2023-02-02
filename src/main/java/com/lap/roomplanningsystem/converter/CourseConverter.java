package com.lap.roomplanningsystem.converter;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Course;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class CourseConverter implements ConverterInterface<Course>{

    private ObservableList<Course> courseList;


    public CourseConverter() {
        this.courseList = BaseController.model.getDataholder().getCourses();

    }

    @Override
    public void setConverter(ComboBox<Course> box) {

        box.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                return course == null ? "Kurs" : course.getTitle() + " " + course.getProgram().getDescription();
            }

            @Override
            public Course fromString(String s) {
                return matchByString(s);
            }
        });
    }

    @Override
    public Course matchByString(String s) {
        String[] find = s.split(" ");
        for(Course c : courseList){
            if(c.getTitle().equals(find[0])){
                return c;
            }
        }
        return null;
    }
}
