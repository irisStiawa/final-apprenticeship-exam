package com.lap.roomplanningsystem.repository.interfaces;

import com.lap.roomplanningsystem.model.User;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository extends BaseRepository {

    Optional<ObservableList<User>> readAll() throws Exception;

    Optional<User> checkUsernamePw(String username, String password) throws SQLException;

    Optional<User> readUserByID(int id) throws SQLException;

    User add(String firstname, String lastname, String title, String username, String authorization, String password, Boolean trainer,
             Boolean textVisable, String phone, Boolean phoneVisable, String email, Boolean emailVisable, Boolean photoVisable,
             String text, byte[] photo) throws Exception;

    boolean update(User user, String password) throws SQLException, IOException;

    boolean deActivateUser(User u) throws Exception;

    boolean edit(User user) throws Exception;

    boolean updateProfileImage(User user, InputStream inputStream) throws Exception;


}

