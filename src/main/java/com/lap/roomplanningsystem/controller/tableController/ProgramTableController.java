package com.lap.roomplanningsystem.controller.tableController;

import java.io.IOException;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Program;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProgramTableController extends BaseController {


    @FXML
    private TableColumn<Program, String> programDescriptionColumn;

    @FXML
    private TableColumn<Program, String> programNumberColumn;

    @FXML
    private TableView<Program> programTableView;

    @FXML
    private TableColumn<Program, String> descriptionColumn;

    @FXML
    private TableColumn<Program, String> numberColumn;

    @FXML
    private TableView<Program> tableView;



    @FXML
    void initialize() {
        tableView.setItems(model.getDataholder().getPrograms());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("P" + String.valueOf(dataFeatures.getValue().getProgramID())));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {

            try {
                if(nv != null && !model.isDetailView()) {
                    model.setSelectedProgramProperty(nv);
                    showNewView(Constants.PATH_TO_PROGRAM_DETAIL_VIEW);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedProgramProperty().addListener(new ChangeListener<Program>() {
            @Override
            public void changed(ObservableValue<? extends Program> observableValue, Program oldProgram, Program newProgram) {
                if(newProgram == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });

    }


}
