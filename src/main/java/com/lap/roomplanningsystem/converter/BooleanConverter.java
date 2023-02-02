package com.lap.roomplanningsystem.converter;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class BooleanConverter {

    public static void setConverter(ComboBox<Boolean> box) {
        box.setConverter(new StringConverter<Boolean>() {
            @Override
            public String toString(Boolean b) {
                return b ? "ja" : "nein";
            }

            @Override
            public Boolean fromString(String s) {
                return s.equals("ja")? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }
}
