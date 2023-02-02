package com.lap.roomplanningsystem.controller.detailController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RoomDetailViewController extends BaseController {


    @FXML
    private Button deleteButton;

    @FXML
    private ImageView imageView;

    @FXML
    private Label locationLabel;

    @FXML
    private Label membersLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Button updateButton;

    private Room room;

    private Stage stage;



    @FXML
    void initialize() {

        Optional<Room> optionalRoom = model.getDataholder().getRooms().stream().filter(room -> room == model.getSelectedRoomProperty()).findAny();

        if (optionalRoom.isPresent()){
            room = optionalRoom.get();

            numberLabel.setText("R" + room.getRoomID());
            titleLabel.setText(room.getDescription());
            locationLabel.setText(room.getLocation().getDescription());
            membersLabel.setText(String.valueOf(room.getMaxPersons()));

            if(room.getPhoto() != null){
                imageView.setImage(new Image(new ByteArrayInputStream(room.getPhoto())));
            }

        }

        updateAuthorization();

    }




    private void updateAuthorization() {
        if(model.getAuthorization().equals("admin")){
        updateButton.setVisible(true);
        deleteButton.setVisible(true);
        }
    }


    @FXML
    private void onDeleteButtonClicked(ActionEvent event)  throws IOException {
        showNewView(Constants.PATH_TO_ROOM_ON_DELETE_VIEW);
        closeStage(numberLabel);
    }

    @FXML
    private void onUpdateButtonClicked(ActionEvent event) throws IOException {
        showNewView(Constants.PATH_TO_ROOM_UPDATE_VIEW);
        closeStage(numberLabel);
    }


}
