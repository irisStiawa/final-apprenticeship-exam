package com.lap.roomplanningsystem.filterBoxes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

public class FilterCheckBox {
    private CheckBox checkBox;
    private BooleanProperty check = new SimpleBooleanProperty();

    public FilterCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;

        setFilterListener(checkBox);

    }

    private void setFilterListener(CheckBox checkBox) {
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                setCheck(newValue);
            }
        });
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public boolean isCheck() {
        return check.get();
    }

    public BooleanProperty checkProperty() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check.set(check);
    }
}
