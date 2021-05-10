package ru.databases.client.changelisteners;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class DoubleValueChangeListener implements ChangeListener<String> {
    private TextField textField;

    public DoubleValueChangeListener(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!newValue.matches("(\\d+(,\\d{0,2})?)?")){
            textField.setText(oldValue);
        }
    }
}
