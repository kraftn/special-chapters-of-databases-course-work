package ru.databases.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.databases.client.models.Person;
import ru.databases.client.utils.Formatter;

public class FindItemsByPersonController {
    @FXML
    private TextField tfSurname, tfName, tfPatronymic;

    public FindItemsByPersonController(Dialog<Person> dialog, String title) {
        dialog.setTitle(title);
        dialog.setHeaderText("Укажите необходимые параметры поиска:");
        ButtonType bOk = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        ButtonType bCancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(bOk, bCancel);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == bOk) {
                return new Person(Formatter.checkStringIsEmpty(tfSurname.getText()), Formatter.checkStringIsEmpty(tfName.getText()),
                        Formatter.checkStringIsEmpty(tfPatronymic.getText()));
            } else {
                return null;
            }
        });
    }
}
