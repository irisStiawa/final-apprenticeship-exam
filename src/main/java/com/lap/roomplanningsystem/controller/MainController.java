package com.lap.roomplanningsystem.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;

import java.io.IOException;

public class MainController extends BaseController{


    @FXML
    private BorderPane mainView;


    private void loadFXMLInBorderPaneCenter(String fxmlPath) throws IOException {
        Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
        mainView.setCenter(view);
    }

    @FXML
    private void initialize(){
        model.pathToViewProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(newValue != null){
                    try {
                        loadFXMLInBorderPaneCenter(newValue);

                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        Window.getWindows().addListener(new ListChangeListener<Window>() {
            @Override
            public void onChanged(Change<? extends Window> change) {
                if(Window.getWindows().size() > 1){
                    model.setDetailView(true);
                } else {
                    removeSelection();
                    if(model.getNewEntry() != null){
                        model.setRemoveEntry(true);
                    }

                    model.setDetailView(false);
                }
            }
        });

    }

    private void removeSelection() {
        model.setSelectedEventProperty(null);
        model.setSelectedRoomProperty(null);
        model.setSelectedCourseProperty(null);
        model.setSelectedProgramProperty(null);
        model.setSelectedLocationProperty(null);
        model.setSelectedRoomEquipmentProperty(null);
        model.setSelectedEquipmentProperty(null);
        model.setSelectedUserProperty(null);
    }
}
