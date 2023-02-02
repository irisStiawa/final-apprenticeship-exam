package com.lap.roomplanningsystem.filterBoxes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class FilterComboBox {
    ComboBox<String> comboBox;
    String defaultValue;
    private StringProperty value = new SimpleStringProperty("");

    public FilterComboBox(ComboBox<String> comboBox, String defaultValue, ObservableList<String> items, Boolean setListener) {
        this.comboBox = comboBox;
        this.defaultValue = defaultValue;

        comboBox.setItems(items);
        comboBox.setPromptText(defaultValue);

        if(setListener){
            createListener();
        }

    }

    private void createListener() {
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(newValue.equals("")){
//                    comboBox.setPromptText(defaultValue);
                }
            }
        });
    }


    public void setItems(ObservableList<String> items){
        comboBox.setItems(items);
    }
}
