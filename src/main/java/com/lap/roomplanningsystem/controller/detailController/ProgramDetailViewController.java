package com.lap.roomplanningsystem.controller.detailController;

import java.io.IOException;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Program;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProgramDetailViewController extends BaseController {

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label numberLabel;

    private Program program;



    @FXML
    void initialize() {

        Optional<Program> optionalProgram = model.getDataholder().getPrograms().stream().filter(program -> program == model.getSelectedProgramProperty()).findAny();

        if(optionalProgram.isPresent()) {
            program = optionalProgram.get();
            numberLabel.setText("P" + String.valueOf(program.getProgramID()));
            descriptionLabel.setText(program.getDescription());
        }
    }


    @FXML
    void onDeleteButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_PROGRAM_ON_DELETE_VIEW);
        closeStage(numberLabel);
    }

    @FXML
    void onUpdateButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_PROGRAM_UPDATE_VIEW);
        closeStage(numberLabel);
    }

}
