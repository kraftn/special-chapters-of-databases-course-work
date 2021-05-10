package ru.databases.client.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import ru.databases.client.changelisteners.DoubleValueChangeListener;
import ru.databases.client.models.Painting;
import ru.databases.client.utils.*;
import ru.databases.client.navigation.NavigationManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaintingController extends FormController {
    @FXML
    private TextField tfItemName, tfCentury, tfWorth, tfSurname, tfName, tfPatronymic, tfGenre, tfStyle,
            tfTechnique, tfWidth, tfHeight;
    @FXML
    private DatePicker dpEnteringDate;
    @FXML
    private Button bOk;

    public Painting data;

    private void setChangeListeners() {
        tfCentury.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,2}")) {
                    tfCentury.setText(oldValue);
                }
            }
        });
        tfWorth.textProperty().addListener(new DoubleValueChangeListener(tfWorth));
        tfWidth.textProperty().addListener(new DoubleValueChangeListener(tfWidth));
        tfHeight.textProperty().addListener(new DoubleValueChangeListener(tfHeight));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChangeListeners();
        if (!mongoManager.isWritingNecessary()) {
            bOk.setDisable(true);
        }

        if (data != null) {
            tfItemName.setText(Formatter.checkStringIsNull(data.getName()));
            tfCentury.setText(Formatter.convertToString(data.getCentury()));
            dpEnteringDate.setValue(data.getEnteringDate());
            tfWorth.setText(Formatter.convertToString(data.getWorth()));

            tfSurname.setText(Formatter.checkStringIsNull(data.getArtist().getSurname()));
            tfName.setText(Formatter.checkStringIsNull(data.getArtist().getName()));
            tfPatronymic.setText(Formatter.checkStringIsNull(data.getArtist().getPatronymic()));

            tfGenre.setText(Formatter.checkStringIsNull(data.getGenre()));
            tfStyle.setText(Formatter.checkStringIsNull(data.getStyle()));
            tfTechnique.setText(Formatter.checkStringIsNull(data.getTechnique()));

            tfWidth.setText(Formatter.convertToString(data.getSize().getWidth()));
            tfHeight.setText(Formatter.convertToString(data.getSize().getHeight()));

            Image image = redisManager.loadImage(Painting.class, data.getId().toString());
            if (image != null) {
                ivPicture.setFitWidth(imageSize);
                ivPicture.setFitHeight(imageSize);
                ivPicture.setImage(image);
            }
        }
    }

    @FXML
    public void ok() {
        if (!tfItemName.getText().isEmpty()) {
            boolean isNewObject = false;
            if (null == data) {
                isNewObject = true;
                data = new Painting();
            }

            data.setName(Formatter.checkStringIsEmpty(tfItemName.getText()));
            data.setCentury(Formatter.convertToInteger(tfCentury.getText()));
            data.setEnteringDate(dpEnteringDate.getValue());
            data.setWorth(Formatter.convertToDouble(tfWorth.getText()));

            data.getArtist().setSurname(Formatter.checkStringIsEmpty(tfSurname.getText()));
            data.getArtist().setName(Formatter.checkStringIsEmpty(tfName.getText()));
            data.getArtist().setPatronymic(Formatter.checkStringIsEmpty(tfPatronymic.getText()));

            data.setGenre(Formatter.checkStringIsEmpty(tfGenre.getText()));
            data.setStyle(Formatter.checkStringIsEmpty(tfStyle.getText()));
            data.setTechnique(Formatter.checkStringIsEmpty(tfTechnique.getText()));

            data.getSize().setWidth(Formatter.convertToDouble(tfWidth.getText()));
            data.getSize().setHeight(Formatter.convertToDouble(tfHeight.getText()));

            if (isNewObject) {
                mongoManager.insert(Painting.class, data);
            } else {
                mongoManager.update(Painting.class, data);
            }
            if (isNewImage) {
                redisManager.saveImage(Painting.class, data.getId().toString(), ivPicture.getImage());
            }

            goToTableScene();
        } else {
            InflateUtils.createAndShowError("Введите название картины.");
        }
    }

    protected void goToTableScene() {
        List<Painting> content = mongoManager.getItems(Painting.class);
        TableView<Painting> table = TableManager.getPaintingsTable(content);
        NavigationManager.from(ivPicture).goToTableScene(table, "Картины");
    }
}
