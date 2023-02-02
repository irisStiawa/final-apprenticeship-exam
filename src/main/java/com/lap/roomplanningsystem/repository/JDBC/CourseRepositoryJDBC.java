package com.lap.roomplanningsystem.repository.JDBC;

import com.lap.roomplanningsystem.model.*;
import com.lap.roomplanningsystem.repository.Repository;
import com.lap.roomplanningsystem.repository.interfaces.CourseRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Optional;

public class CourseRepositoryJDBC extends Repository implements CourseRepository {
    @Override
    public Optional<ObservableList<Course>> readAll() throws Exception {
        Connection connection = connect();

        String query = "{CALL courseListStatement()}";

        CallableStatement stmt = connection.prepareCall(query);
        ResultSet resultSet = stmt.executeQuery();

        connection.close();

        return createCourses(resultSet);
    }

    @Override
    public Course add(String description, Program program, Integer members, Date startDate, Date endDate) throws Exception {
        Connection connection = connect();

        String query = "INSERT INTO course (PROGRAMID, TITLE, MEMBERS, START, END) VALUES (?,?,?,?,?)";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, program.getProgramID());
        stmt.setString(2, description);
        stmt.setInt(3, members);
        stmt.setDate(4, startDate);
        stmt.setDate(5, endDate);
        stmt.executeQuery();

        ResultSet resultSet = stmt.getGeneratedKeys();

        Course course = null;

        while(resultSet.next()){
            int courseID  = resultSet.getInt(1);
            course = new Course(courseID, program, description, members, startDate, endDate);
        }

        connection.close();

        return course;
    }


    @Override
    public boolean update(Course course) throws SQLException {
        Connection connection = connect();

        String query = "UPDATE course SET PROGRAMID = ?, TITLE = ?, MEMBERS = ?, START = ?, END = ? WHERE COURSEID = ?";

        PreparedStatement stmt = null;

        stmt = connection.prepareStatement(query);

        stmt.setInt(1, course.getProgram().getProgramID());
        stmt.setString(2, course.getTitle());
        stmt.setInt(3, course.getMembers());
        stmt.setDate(4, course.getStart());
        stmt.setDate(5, course.getEnd());
        stmt.setInt(6, course.getCourseID());


        int isUpdated = stmt.executeUpdate();

        connection.close();

        return isUpdated != 0;


    }

    @Override
    public boolean delete(Course course) throws SQLException {
        Connection connection = connect();

        String query = "DELETE FROM course WHERE COURSEID = ?";

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, course.getCourseID());

        int isDeleted = stmt.executeUpdate();

        connection.close();

        return isDeleted != 0;
    }




    private Optional<ObservableList<Course>> createCourses(ResultSet resultSet) throws SQLException {
        ObservableList<Course> observableListCourse = FXCollections.observableArrayList();

        while (resultSet.next()) {

            Course course = new Course(resultSet.getInt("COURSEID"),
                    new Program(resultSet.getInt("PROGRAMID"),
                            resultSet.getString("DESCRIPTION")),
                    resultSet.getString("TITLE"),
                    resultSet.getInt("MEMBERS"),
                    resultSet.getDate("START"),
                    resultSet.getDate("END"));


            observableListCourse.add(course);
        }

        return Optional.of(observableListCourse);
    }


}
