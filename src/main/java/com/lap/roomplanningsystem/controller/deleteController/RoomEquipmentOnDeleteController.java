
package com.lap.roomplanningsystem.controller.deleteController;

        import java.sql.SQLException;
        import java.util.Optional;

        import com.lap.roomplanningsystem.controller.BaseController;
        import com.lap.roomplanningsystem.model.Dataholder;
        import com.lap.roomplanningsystem.model.RoomEquipment;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Label;

public class RoomEquipmentOnDeleteController extends BaseController {

    @FXML
    private Label equipmentLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label roomLabel;

    private RoomEquipment roomEquipment;



    @FXML
    void initialize() {

        Optional<RoomEquipment> optionalRoomEquipment = model.getDataholder().getRoomEquipments().stream().filter(e -> e.getRoomEquipmentID() == model.getSelectedRoomEquipmentProperty().getRoomEquipmentID()).findAny();

        if(optionalRoomEquipment.isPresent()){
            roomEquipment = optionalRoomEquipment.get();
            numberLabel.setText("RA" + roomEquipment.getRoomEquipmentID());
            roomLabel.setText(roomEquipment.getRoom().getDescription());
            equipmentLabel.setText(roomEquipment.getEquipment().getDescription());
        }

    }

    @FXML
    void onNoButtonClicked(ActionEvent event) {
        closeStage(numberLabel);

    }

    @FXML
    void onYesButtonClicked(ActionEvent event) throws SQLException {
        model.setSelectedRoomEquipmentProperty(null);

        if(deleteRoomEquipmentByJDBC()){
            model.getDataholder().deleteRoomEquipment(roomEquipment);
        }

        closeStage(numberLabel);

    }

    private boolean deleteRoomEquipmentByJDBC() throws SQLException {
        return Dataholder.roomEquipmentRepositoryJDBC.delete(roomEquipment);
    }

}
