package ru.databases.client.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Optional;

public class InflateUtils {
    private InflateUtils() {}

    public static Parent loadFxml(String resource) {
        try {
            return FXMLLoader.load(resource.getClass().getResource(resource));
        } catch (IOException e) {
            throw new RuntimeException("Could not load fxml: " + resource, e);
        }
    }

    public static Parent loadFxmlWithCustomController(String resource, Object controller) {
        try {
            Callback<Class<?>, Object> controllerFactory = param -> {
                if (param == controller.getClass()) {
                    return controller;
                } else {
                    throw new RuntimeException("Cannot not bind custom controller with " + resource);
                }
            };
            return FXMLLoader.load(
                    resource.getClass().getResource(resource),
                    null,
                    null,
                    controllerFactory
            );
        } catch (IOException e) {
            throw new RuntimeException("Could not load fxml: " + resource, e);
        }
    }

    public static void createAndShowError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void createAndShowInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Optional<ButtonType> createAndShowConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getButtonTypes().clear();
        alert.getDialogPane().getButtonTypes().addAll(new ButtonType("ОК",
                ButtonBar.ButtonData.OK_DONE), new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE));
        alert.setTitle("Подтверждение");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    public static <T> void fillComboBox(ComboBox<T> comboBox, T[] values) {
        ObservableList<T> observableList = FXCollections.observableArrayList(values);
        comboBox.setItems(observableList);
    }
}
