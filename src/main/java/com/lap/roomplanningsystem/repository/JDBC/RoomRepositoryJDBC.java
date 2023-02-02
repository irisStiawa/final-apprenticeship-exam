package com.lap.roomplanningsystem.repository.JDBC;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.model.Location;
import com.lap.roomplanningsystem.model.Room;
import com.lap.roomplanningsystem.repository.Repository;
import com.lap.roomplanningsystem.repository.interfaces.RoomRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Optional;


public class RoomRepositoryJDBC extends Repository implements RoomRepository {

    @Override
    public Optional<ObservableList<Room>> readAll() throws Exception {

        Connection connection = connect();

        String query = "{CALL roomListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();

        connection.close();

        return createRooms(resultSet);

    }



    @Override
    public Room add(String description, Location location, int maxPersons, byte[] photo) throws SQLException {


        Connection connection = connect();

        String query = "INSERT INTO rooms (DESCRIPTION, LOCATIONID, MAXPERSONS, PHOTO) VALUES (?,?,?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, description);
        stmt.setInt(2, location.getLocationID());
        stmt.setInt(3, maxPersons);
        stmt.setBytes(4, photo);

        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        Room room = null;

        while(resultSet.next()){

            int roomID  = resultSet.getInt(1);
            room = new Room(roomID, description, location, maxPersons, photo);
        }

        connection.close();

        return room;


    }

    @Override
    public boolean update(Room room) throws Exception {
        Connection connection = connect();

        String query = "UPDATE rooms SET DESCRIPTION = ?, LOCATIONID = ?, MAXPERSONS = ?, PHOTO = ? WHERE ROOMID = ?";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query);

        stmt.setString(1, room.getDescription());
        stmt.setInt(2, room.getLocation().getLocationID());
        stmt.setInt(3, room.getMaxPersons());
        stmt.setBytes(4, room.getPhoto());
        stmt.setInt(5, room.getRoomID());


        int isUpdated = stmt.executeUpdate();

        connection.close();

        return isUpdated != 0;

    }


    @Override
    public boolean delete(Room room) throws SQLException {

        Connection connection = connect();

        String query = "DELETE FROM rooms WHERE ROOMID = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, room.getRoomID());

        int isDeleted = stmt.executeUpdate();

        connection.close();


        return isDeleted != 0;
    }




    @Override
    public Optional<ObservableList<Room>> filter(String filter, boolean equipment) throws Exception {

        Connection connection = connect();

        String query = equipment ?  Constants.ROOM_BASE_FILTER + filter : Constants.ROOM_EQUIPMENT_FILTER + filter;

        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();

        connection.close();

        return createRooms(resultSet);
    }


    private Optional<ObservableList<Room>> createRooms(ResultSet resultSet) throws Exception{
        ObservableList<Room> observableListRooms = FXCollections.observableArrayList();

        while (resultSet.next()) {
            byte [] photo = createImageByteArray(resultSet.getBlob("PHOTO"));

            Room room = new Room(resultSet.getInt("ROOMID"),
                    resultSet.getString("DESCRIPTION"),
                    new Location(resultSet.getInt("LOCATIONID"),
                            resultSet.getString("LDESCRIPTION"),
                            resultSet.getString("ADRESS"),
                            resultSet.getString("POSTCODE"),
                            resultSet.getString("CITY")),
                    resultSet.getInt("MAXPERSONS"),
                    photo);

            observableListRooms.add(room);
        }

        return Optional.of(observableListRooms);
    }



}
