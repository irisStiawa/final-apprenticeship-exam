package com.lap.roomplanningsystem.repository.JDBC;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.app.Password;
import com.lap.roomplanningsystem.model.User;
import com.lap.roomplanningsystem.repository.Repository;
import com.lap.roomplanningsystem.repository.interfaces.UserRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Optional;

public class UserRepositoryJDBC extends Repository implements UserRepository {


    @Override
    public Optional<User> checkUsernamePw(String username, String password) throws SQLException {

        Connection connection = connect();

        String query = "Select * from users where USERNAME = ? AND ACTIVE = 1";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet resultSet = stmt.executeQuery();

        User user = null;

        if (resultSet.next()) {
            if (Password.verify(resultSet.getString("PASSWORD"), password)) {

                user = newUser(resultSet);
            }
        }

        connection.close();

        return user != null? Optional.of(user): Optional.empty();
    }

    @Override
    public Optional<ObservableList<User>> readAll() throws SQLException {

        Connection connection = connect();

        String query = Constants.READ_ALL_USER;
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet resultSet = stmt.executeQuery();

        connection.close();

        return createUsers(resultSet);
    }

    @Override
    public Optional<User> readUserByID(int id) throws SQLException {

        Connection connection = connect();

        String query = Constants.READ_USER_BY_ID;
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        ResultSet resultSet = stmt.executeQuery();

        connection.close();

        return createUser(resultSet);
    }

    @Override
    public User add(String firstname, String lastname, String title, String username, String authorization, String password, Boolean trainer, Boolean textVisable, String phone, Boolean phoneVisable, String email, Boolean emailVisable, Boolean photoVisable, String text, byte[] photo) throws Exception {

        Connection connection = connect();

        String query = "INSERT INTO users(ACTIVE, TITLE, FIRSTNAME, LASTNAME, USERNAME, PASSWORD, AUTHORIZATION," +
                "COACH, TEXT, TEXTVISABLE, PHONE, PHONEVISABLE, EMAIL, EMAILVISABLE, PHOTO, PHOTOVISABLE) VALUES " +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setBoolean(1, true);
        stmt.setString(2, title);
        stmt.setString(3, firstname);
        stmt.setString(4, lastname);
        stmt.setString(5, username);
        stmt.setString(6, password);
        stmt.setString(7, authorization);
        stmt.setBoolean(8, trainer);
        stmt.setString(9, text);
        stmt.setBoolean(10, textVisable);
        stmt.setString(11, phone);
        stmt.setBoolean(12, phoneVisable);
        stmt.setString(13, email);
        stmt.setBoolean(14, emailVisable);
        stmt.setBytes(15, photo);
        stmt.setBoolean(16, photoVisable);

        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        User user = null;

        while (resultSet.next()) {

            int userID = resultSet.getInt(1);
            user = new User(userID, true, title, firstname, lastname, username, authorization, trainer, text, textVisable,
                    phone, phoneVisable, email, emailVisable, photo, photoVisable);
        }

        connection.close();

        return user;


    }


    @Override
    public boolean update(User user, String password) throws SQLException, IOException {

        boolean updatePassword = password != null;

        Connection connection = connect();

        String query;

        query = updatePassword ? Constants.UPDATE_USER_WITHIN_PASSWORD : Constants.UPDATE_USER_WITHOUT_PASSWORD;

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setBoolean(1, user.isActive());
        stmt.setString(2, user.getTitle());
        stmt.setString(3, user.getFirstname());
        stmt.setString(4, user.getLastname());
        stmt.setString(5, user.getUsername());

        if (updatePassword) {
            stmt.setString(6, password);
            stmt.setString(7, user.getAuthorization());
            stmt.setBoolean(8, user.isTrainer());
            stmt.setString(9, user.getText());
            stmt.setBoolean(10, user.isTextVisable());
            stmt.setString(11, user.getPhone());
            stmt.setBoolean(12, user.isPhoneVisable());
            stmt.setString(13, user.getEmail());
            stmt.setBoolean(14, user.isEmailVisable());
            stmt.setBytes(15, user.getPhoto());
            stmt.setBoolean(16, user.isPhotoVisable());
            stmt.setInt(17, user.getId());
        } else {
            stmt.setString(6, user.getAuthorization());
            stmt.setBoolean(7, user.isTrainer());
            stmt.setString(8, user.getText());
            stmt.setBoolean(9, user.isTextVisable());
            stmt.setString(10, user.getPhone());
            stmt.setBoolean(11, user.isPhoneVisable());
            stmt.setString(12, user.getEmail());
            stmt.setBoolean(13, user.isEmailVisable());
            stmt.setBytes(14, user.getPhoto());
            stmt.setBoolean(15, user.isPhotoVisable());
            stmt.setInt(16, user.getId());
        }

        int isUpdated = stmt.executeUpdate();

        connection.close();

        return isUpdated != 0;

    }

    @Override
    public boolean deActivateUser(User u) throws Exception {
        Connection connection = connect();

        String query = "UPDATE users SET ACTIVE = ? WHERE USERID = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setBoolean(1, u.isActive());
        stmt.setInt(2, u.getId());

        int isActiv = stmt.executeUpdate();

        connection.close();

        return isActiv != 0;
    }

    @Override
    public boolean edit(User user) throws Exception {
        Connection connection = connect();

        String query = "UPDATE users SET PHONE=?, EMAIL=?, TEXT=? WHERE USERID = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, user.getPhone());
        stmt.setString(2, user.getEmail());
        stmt.setString(3, user.getText());
        stmt.setInt(4, user.getId());

        int isEdited = stmt.executeUpdate();

        connection.close();

        return isEdited != 0;
    }

    @Override
    public boolean updateProfileImage(User user, InputStream inputStream) throws Exception {
        Connection connection = connect();

        String query = "UPDATE users SET PHOTO = ? WHERE USERID = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setBlob(1, inputStream);

        stmt.setInt(2, user.getId());

        int isPhotoChanged = stmt.executeUpdate();

        connection.close();

        return isPhotoChanged != 0;
    }


    private Optional<User> createUser(ResultSet resultSet) throws SQLException {

        User user = null;

        while (resultSet.next()) {

            user = newUser(resultSet);

        }

        return Optional.of(user);
    }


    private Optional<ObservableList<User>> createUsers(ResultSet resultSet) throws SQLException {

        ObservableList<User> users = FXCollections.observableArrayList();

        while (resultSet.next()) {

            User user = newUser(resultSet);

            users.add(user);
        }

        return Optional.of(users);
    }

    private User newUser(ResultSet resultSet) throws SQLException {

        return new User(resultSet.getInt("USERID"),
                resultSet.getBoolean("ACTIVE"),
                resultSet.getString("TITLE"),
                resultSet.getString("FIRSTNAME"),
                resultSet.getString("LASTNAME"),
                resultSet.getString("USERNAME"),
                resultSet.getString("AUTHORIZATION"),
                resultSet.getBoolean("COACH"),
                resultSet.getString("TEXT"),
                resultSet.getBoolean("TEXTVISABLE"),
                resultSet.getString("PHONE"),
                resultSet.getBoolean("PHONEVISABLE"),
                resultSet.getString("EMAIL"),
                resultSet.getBoolean("EMAILVISABLE"),
                resultSet.getBytes("PHOTO"),
                resultSet.getBoolean("PHOTOVISABLE"));
    }
}
