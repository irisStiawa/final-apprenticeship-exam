package com.lap.roomplanningsystem.repository.interfaces;

import com.lap.roomplanningsystem.model.Location;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public interface LocationRepository extends BaseRepository{

    Optional<ObservableList<Location>> readAll() throws Exception;

    Location add(String description, String adress, String postCode, String city) throws Exception;

    Boolean update(Location location) throws SQLException;

    boolean delete(Location location) throws SQLException;



}
