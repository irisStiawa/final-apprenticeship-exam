package com.lap.roomplanningsystem.controller.tableController;

import java.io.IOException;
import java.sql.Date;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Course;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CourseTableController extends BaseController {

    @FXML
    private TableColumn<Course, String> descriptionColumn;

    @FXML
    private TableColumn<Course, Date> endColumn;

    @FXML
    private TableColumn<Course, String> numberColumn;

    @FXML
    private TableColumn<Course, String> programColumn;

    @FXML
    private TableColumn<Course, Integer> sizeColumn;

    @FXML
    private TableColumn<Course, Date> startColumn;

    @FXML
    private TableView<Course> tableView;




    @FXML
    void initialize() {
        tableView.setItems(model.getDataholder().getCourses());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("K" + String.valueOf(dataFeatures.getValue().getCourseID())));
        programColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getProgram().getDescription()));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getTitle()));
        sizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().getMembers()));
        startColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Date>(dataFeatures.getValue().getStart()));
        endColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Date>(dataFeatures.getValue().getEnd()));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {

            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedCourseProperty(nv);
                    showNewView(Constants.PATH_TO_COURSE_DETAIL_VIEW);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedCourseProperty().addListener(new ChangeListener<Course>() {
            @Override
            public void changed(ObservableValue<? extends Course> observableValue, Course oldCourse, Course newCourse) {
                if(newCourse == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });
    }


}


