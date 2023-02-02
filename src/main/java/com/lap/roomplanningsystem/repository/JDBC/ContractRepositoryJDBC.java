package com.lap.roomplanningsystem.repository.JDBC;

import com.lap.roomplanningsystem.model.Contract;
import com.lap.roomplanningsystem.model.Course;
import com.lap.roomplanningsystem.model.Program;
import com.lap.roomplanningsystem.repository.Repository;
import com.lap.roomplanningsystem.repository.interfaces.ContractRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Optional;

public class ContractRepositoryJDBC extends Repository implements ContractRepository {

    @Override
    public Optional<ObservableList<Contract>> readAll() throws Exception {

        Connection connection = connect();

        String query = "SELECT * from contracts";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query);

        ResultSet resultSet = stmt.executeQuery();

        connection.close();


        return createContracts(resultSet);
    }

    public Contract add(String title, String firstname, String lastname, String adress, String postCode, String land, String phone, Date start, String type) throws Exception {
        Connection connection = connect();

        String query = "INSERT INTO contracts (TITLE, FIRSTNAME, LASTNAME, ADRESS, POSTCODE, LAND, PHONE, START, TYPE) VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, title);
        stmt.setString(2, firstname);
        stmt.setString(3, lastname);
        stmt.setString(4, adress);
        stmt.setString(5, postCode);
        stmt.setString(6, land);
        stmt.setString(7, phone);
        stmt.setDate(8, start);
        stmt.setString(9, type);


        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        Contract contract = null;

        while(resultSet.next()){
            int contractID  = resultSet.getInt(1);
            contract = new Contract(contractID, title, firstname, lastname, adress, postCode, land, phone, start, type);
        }

        connection.close();

        return contract;
    }

    private Optional<ObservableList<Contract>> createContracts(ResultSet resultSet) throws SQLException {
        ObservableList<Contract> observableListContract = FXCollections.observableArrayList();

        while (resultSet.next()) {

            Contract contract = new Contract(resultSet.getInt("CONTRACTID"),
                    resultSet.getString("TITLE"),
                    resultSet.getString("FIRSTNAME"),
                    resultSet.getString("LASTNAME"),
                    resultSet.getString("ADRESS"),
                    resultSet.getString("POSTCODE"),
                    resultSet.getString("LAND"),
                    resultSet.getString("PHONE"),
                    resultSet.getDate("START"),
                    resultSet.getString("TYPE"));


            observableListContract.add(contract);
        }

        return Optional.of(observableListContract);
    }
}
