package com.lap.roomplanningsystem.repository.interfaces;

import com.lap.roomplanningsystem.model.Location;
import com.lap.roomplanningsystem.model.Room;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface RoomRepository {

    Optional<ObservableList<Room>> readAll() throws Exception;

    Optional<ObservableList<Room>> filter(String stmt, boolean equipment) throws Exception;

    Room add(String description, Location location, int maxPersons, byte[] photo) throws SQLException;

    boolean update(Room room) throws Exception;

    boolean delete(Room room) throws SQLException;


}
