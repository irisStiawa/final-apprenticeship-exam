package com.lap.roomplanningsystem.repository.interfaces;

import com.lap.roomplanningsystem.model.Contract;
import com.lap.roomplanningsystem.model.Course;
import javafx.collections.ObservableList;

import java.util.Optional;

public interface ContractRepository extends BaseRepository{
    Optional<ObservableList<Contract>> readAll() throws Exception;
}
