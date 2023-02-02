package com.lap.roomplanningsystem.searcher;

import javafx.collections.ObservableList;

public interface Searcher<T> {

    ObservableList<T> search(String s, ObservableList<T> list);
}
