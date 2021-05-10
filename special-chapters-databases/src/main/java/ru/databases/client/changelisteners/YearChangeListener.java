package ru.databases.client.changelisteners;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class YearChangeListener implements ChangeListener<String> {
    private TextField textField;

    public YearChangeListener(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(!newValue.matches("\\d{0,4}")){
            textField.setText(oldValue);
        }
    }
}
