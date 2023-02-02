package com.lap.roomplanningsystem.repository.interfaces;

import javafx.collections.ObservableList;

import java.util.Optional;

public interface BaseRepository<T> {
    Optional<ObservableList<T>> readAll() throws Exception;


}
