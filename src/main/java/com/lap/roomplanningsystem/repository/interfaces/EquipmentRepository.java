package com.lap.roomplanningsystem.repository.interfaces;

import com.lap.roomplanningsystem.model.Equipment;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface EquipmentRepository extends BaseRepository{

    Optional<ObservableList<Equipment>> readAll() throws Exception;

    Equipment add(String text) throws Exception;

    boolean update(Equipment equipment) throws SQLException;

    boolean delete(Equipment equipment) throws SQLException;


}
