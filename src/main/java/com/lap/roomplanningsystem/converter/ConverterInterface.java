package com.lap.roomplanningsystem.converter;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public interface ConverterInterface<T>  {

    void setConverter(ComboBox<T> box);

    T matchByString(String s);
}
