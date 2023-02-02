package com.lap.roomplanningsystem.repository.interfaces;

import com.lap.roomplanningsystem.model.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface RoomEquipmentRepository extends BaseRepository {

    Optional<ObservableList<RoomEquipment>> readAll() throws Exception;

    RoomEquipment add(Room room, Equipment equipment) throws Exception;

    boolean update(RoomEquipment roomEquipment) throws SQLException;


    boolean delete(RoomEquipment roomEquipment) throws SQLException;
}
