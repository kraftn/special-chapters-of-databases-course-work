package ru.databases.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import ru.databases.client.changelisteners.DoubleValueChangeListener;
import ru.databases.client.changelisteners.IntegerValueChangeListener;
import ru.databases.client.changelisteners.YearChangeListener;
import ru.databases.client.models.Coin;
import ru.databases.client.options.CoinageQuality;
import ru.databases.client.utils.*;
import ru.databases.client.navigation.NavigationManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CoinController extends FormController {
    @FXML
    private TextField tfItemName, tfSeries, tfCreationYear, tfWorth, tfDenominationValue, tfMonetaryUnit, tfEdition,
            tfMaterial, tfDiameter, tfThickness, tfWeight;
    @FXML
    private DatePicker dpEnteringDate;
    @FXML
    private ComboBox<CoinageQuality> cbQuality;
    @FXML
    private Button bOk;

    public Coin data;

    private void setChangeListeners() {
        tfCreationYear.textProperty().addListener(new YearChangeListener(tfCreationYear));
        tfWorth.textProperty().addListener(new DoubleValueChangeListener(tfWorth));
        tfDenominationValue.textProperty().addListener(new IntegerValueChangeListener(tfDenominationValue));
        tfEdition.textProperty().addListener(new IntegerValueChangeListener(tfEdition));

        tfDiameter.textProperty().addListener(new DoubleValueChangeListener(tfDiameter));
        tfThickness.textProperty().addListener(new DoubleValueChangeListener(tfThickness));
        tfWeight.textProperty().addListener(new DoubleValueChangeListener(tfWeight));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChangeListeners();
        InflateUtils.fillComboBox(cbQuality, CoinageQuality.values());
        if (!mongoManager.isWritingNecessary()) {
            bOk.setDisable(true);
        }

        if (data != null) {
            tfItemName.setText(Formatter.checkStringIsNull(data.getName()));
            tfSeries.setText(Formatter.checkStringIsNull(data.getSeries()));
            tfCreationYear.setText(Formatter.convertToString(data.getCreationYear()));
            dpEnteringDate.setValue(data.getEnteringDate());
            tfWorth.setText(Formatter.convertToString(data.getWorth()));

            tfDenominationValue.setText(Formatter.convertToString(data.getDenomination().getValue()));
            tfMonetaryUnit.setText(Formatter.checkStringIsNull(data.getDenomination().getUnit()));
            tfEdition.setText(Formatter.convertToString(data.getEdition()));
            tfMaterial.setText(Formatter.checkStringIsNull(data.getMaterial()));
            cbQuality.setValue(data.getCoinageQuality());

            tfDiameter.setText(Formatter.convertToString(data.getPhysicalCharacteristics().getDiameter()));
            tfThickness.setText(Formatter.convertToString(data.getPhysicalCharacteristics().getThickness()));
            tfWeight.setText(Formatter.convertToString(data.getPhysicalCharacteristics().getWeight()));

            Image image = redisManager.loadImage(Coin.class, data.getId().toString());
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
                data = new Coin();
            }

            data.setName(Formatter.checkStringIsEmpty(tfItemName.getText()));
            data.setSeries(Formatter.checkStringIsEmpty(tfSeries.getText()));
            data.setCreationYear(Formatter.convertToInteger(tfCreationYear.getText()));
            data.setEnteringDate(dpEnteringDate.getValue());
            data.setWorth(Formatter.convertToDouble(tfWorth.getText()));

            data.getDenomination().setValue(Formatter.convertToInteger(tfDenominationValue.getText()));
            data.getDenomination().setUnit(Formatter.checkStringIsEmpty(tfMonetaryUnit.getText()));
            data.setEdition(Formatter.convertToLong(tfEdition.getText()));
            data.setMaterial(Formatter.checkStringIsEmpty(tfMaterial.getText()));
            data.setCoinageQuality(cbQuality.getValue());

            data.getPhysicalCharacteristics().setDiameter(Formatter.convertToDouble(tfDiameter.getText()));
            data.getPhysicalCharacteristics().setThickness(Formatter.convertToDouble(tfThickness.getText()));
            data.getPhysicalCharacteristics().setWeight(Formatter.convertToDouble(tfWeight.getText()));

            if (isNewObject) {
                mongoManager.insert(Coin.class, data);
            } else {
                mongoManager.update(Coin.class, data);
            }
            if (isNewImage) {
                redisManager.saveImage(Coin.class, data.getId().toString(), ivPicture.getImage());
            }

            goToTableScene();
        } else {
            InflateUtils.createAndShowError("Введите название монеты.");
        }
    }

    protected void goToTableScene() {
        List<Coin> content = mongoManager.getItems(Coin.class);
        TableView<Coin> table = TableManager.getCoinsTable(content);
        NavigationManager.from(ivPicture).goToTableScene(table, "Монеты");
    }
}
