package com.lap.roomplanningsystem.controller.updateController;


import java.sql.Date;

import java.util.List;
import java.util.Optional;


import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.converter.ProgramConverter;

import com.lap.roomplanningsystem.model.*;

import com.lap.roomplanningsystem.utility.IntegerUtility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class CourseOnUpdateController extends BaseController {

    @FXML
    private TextField descriptionInput;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField membersInput;

    @FXML
    private Label numberLabel;

    @FXML
    private ComboBox<Program> programComboBox;

    @FXML
    private DatePicker startDatePicker;

    private Course course;



    @FXML
    void initialize() {

        Optional<Course> optionalCourse = model.getDataholder().getCourses().stream().filter(course -> course == model.getSelectedCourseProperty()).findAny();

        if(optionalCourse.isPresent()){
            course = optionalCourse.get();

            numberLabel.setText("K" + String.valueOf(course.getCourseID()));
            descriptionInput.setText(course.getTitle());
            membersInput.setText(String.valueOf(course.getMembers()));
            startDatePicker.setValue(course.getStart().toLocalDate());
            endDatePicker.setValue(course.getEnd().toLocalDate());

            programComboBox.setItems(model.getDataholder().getPrograms());
            programComboBox.getSelectionModel().select(course.getProgram());

            ProgramConverter programConverter = new ProgramConverter();
            programConverter.setConverter(programComboBox);

        }
    }


    @FXML
    void onSaveButtonClicked(ActionEvent event) throws Exception {

        if(validateFields()){
            course.setTitle(descriptionInput.getText());
            course.setProgram(programComboBox.getValue());
            course.setMembers(IntegerUtility.getInt(membersInput.getText()));
            course.setStart(Date.valueOf(startDatePicker.getValue()));
            course.setEnd(Date.valueOf(endDatePicker.getValue()));

            boolean isUpdated = updateCourseByJDBC();

            if(isUpdated){
                showNewView(Constants.PATH_TO_SUCCESSFUL_UPDATE);
                int index = model.getDataholder().getCourses().indexOf(course);
                model.getDataholder().updateCourse(index, course);

                model.getDataholder().updateEvents();
                closeStage(errorLabel);
            }
        }
    }

    private boolean validateFields() {
        return !emptyFields() && explicitDescription() && validMembers() && validDate();
    }


    private boolean emptyFields() {
        boolean empty = isBlank(descriptionInput.getText()) || programComboBox.getValue() == null || isBlank(membersInput.getText()) ||
                startDatePicker.getValue() == null || endDatePicker.getValue() == null;

        if(empty){
            errorLabel.setText(Constants.EMPTY_FIELDS_AND_BOXES);
        }

        return empty;
    }

    private boolean explicitDescription() {
        List<Course> courses = model.getDataholder().getCourses().stream().filter(c-> c !=course).toList();
        boolean explicit = courses.stream().noneMatch(c-> c.getTitle().equals(descriptionInput.getText()));

        if(!explicit){
            errorLabel.setText(Constants.COURSE_DESCRIPTION_NOT_ALLOWED);
        }

        return explicit;
    }

    private boolean validMembers() {
        boolean valid = IntegerUtility.getInt(membersInput.getText()) != null;

        if(!valid){
            errorLabel.setText(Constants.MEMBERS_NOT_A_NUMBER);
        }

        return valid;
    }

    private boolean validDate() {
        boolean valid = !endDatePicker.getValue().isBefore(startDatePicker.getValue());

        if(!valid){
            errorLabel.setText(Constants.ENDDATE_BEFORE_STARTDATE);
        }

        return valid;
    }


    private boolean updateCourseByJDBC() throws Exception {
        return Dataholder.courseRepositoryJDBC.update(course);
    }

}
