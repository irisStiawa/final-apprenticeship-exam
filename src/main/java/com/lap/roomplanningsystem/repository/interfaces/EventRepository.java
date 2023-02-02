package com.lap.roomplanningsystem.repository.interfaces;

import com.lap.roomplanningsystem.model.*;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public interface EventRepository extends BaseRepository{

    Optional<ObservableList<Event>> readAll() throws Exception;

    Optional<ObservableList<Event>> filter(String filter) throws Exception;

    Event add(User creator, Room room, Course course, User coach, LocalDate date, LocalDateTime start, LocalDateTime end) throws Exception;

    boolean update(Event event) throws SQLException;

    boolean delete(Event event) throws SQLException;

    ObservableList<Event> compileEvents(User creator, Room room, Course course, User coach, ObservableList<LocalDate> days, LocalTime start, LocalTime end) throws Exception;


}
