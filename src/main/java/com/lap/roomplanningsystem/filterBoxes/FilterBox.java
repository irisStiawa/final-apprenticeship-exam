package com.lap.roomplanningsystem.filterBoxes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

public class FilterBox {
    private ChoiceBox<String> choiceBox;
    private String defaultValue;
    private StringProperty value = new SimpleStringProperty("");

    public FilterBox(ChoiceBox<String> choiceBox, String defaultValue, ObservableList<String> items) {
        this.choiceBox = choiceBox;
        this.defaultValue = defaultValue;

        choiceBox.setItems(items);
        choiceBox.setValue(defaultValue);
        initChangeListener(choiceBox);
    }

    private void initChangeListener(ChoiceBox<String> choiceBox) {
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                setValue(newValue);
            }
        });
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public ChoiceBox<String> getChoiceBox() {
        return choiceBox;
    }

    public void setChoiceBox(ChoiceBox<String> choiceBox) {
        this.choiceBox = choiceBox;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
