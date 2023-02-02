package com.lap.roomplanningsystem.controller.updateController;

import com.lap.roomplanningsystem.controller.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SuccessfulUpdateController extends BaseController {


    @FXML
    private Label label;



    @FXML
    void initialize() {
    }

    @FXML
    void onAcceptButtonClicked(ActionEvent event) {
        closeStage(label);
    }

}