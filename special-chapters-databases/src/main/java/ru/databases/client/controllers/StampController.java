package ru.databases.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import ru.databases.client.changelisteners.DoubleValueChangeListener;
import ru.databases.client.changelisteners.IntegerValueChangeListener;
import ru.databases.client.changelisteners.YearChangeListener;
import ru.databases.client.models.Stamp;
import ru.databases.client.options.PerforationType;
import ru.databases.client.options.PrintingType;
import ru.databases.client.utils.*;
import ru.databases.client.navigation.NavigationManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StampController extends FormController {
    @FXML
    private TextField tfItemName, tfSeries, tfCreationYear, tfWorth, tfSurname, tfName, tfPatronymic, tfDenominationValue,
            tfMonetaryUnit, tfEdition, tfTop, tfRight, tfBottom, tfLeft;
    @FXML
    private DatePicker dpEnteringDate;
    @FXML
    private ComboBox<PrintingType> cbPrinting;
    @FXML
    private ComboBox<PerforationType> cbPerforation;
    @FXML
    private Button bOk;

    public Stamp data;

    private void setChangeListeners() {
        tfCreationYear.textProperty().addListener(new YearChangeListener(tfCreationYear));
        tfWorth.textProperty().addListener(new DoubleValueChangeListener(tfWorth));
        tfDenominationValue.textProperty().addListener(new IntegerValueChangeListener(tfDenominationValue));
        tfEdition.textProperty().addListener(new IntegerValueChangeListener(tfEdition));

        tfTop.textProperty().addListener(new DoubleValueChangeListener(tfTop));
        tfRight.textProperty().addListener(new DoubleValueChangeListener(tfRight));
        tfBottom.textProperty().addListener(new DoubleValueChangeListener(tfBottom));
        tfLeft.textProperty().addListener(new DoubleValueChangeListener(tfLeft));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChangeListeners();
        InflateUtils.fillComboBox(cbPrinting, PrintingType.values());
        InflateUtils.fillComboBox(cbPerforation, PerforationType.values());
        if (!mongoManager.isWritingNecessary()) {
            bOk.setDisable(true);
        }

        if (data != null) {
            tfItemName.setText(Formatter.checkStringIsNull(data.getName()));
            tfSeries.setText(Formatter.checkStringIsNull(data.getSeries()));
            tfCreationYear.setText(Formatter.convertToString(data.getCreationYear()));
            dpEnteringDate.setValue(data.getEnteringDate());
            tfWorth.setText(Formatter.convertToString(data.getWorth()));

            tfSurname.setText(Formatter.checkStringIsNull(data.getArtist().getSurname()));
            tfName.setText(Formatter.checkStringIsNull(data.getArtist().getName()));
            tfPatronymic.setText(Formatter.checkStringIsNull(data.getArtist().getPatronymic()));

            tfDenominationValue.setText(Formatter.convertToString(data.getDenomination().getValue()));
            tfMonetaryUnit.setText(Formatter.checkStringIsNull(data.getDenomination().getUnit()));
            tfEdition.setText(Formatter.convertToString(data.getEdition()));
            cbPrinting.setValue(data.getPrintingType());
            cbPerforation.setValue(data.getPerforationType());
            tfTop.setText(Formatter.convertToString(data.getGauges().getTop()));
            tfRight.setText(Formatter.convertToString(data.getGauges().getRight()));
            tfBottom.setText(Formatter.convertToString(data.getGauges().getBottom()));
            tfLeft.setText(Formatter.convertToString(data.getGauges().getLeft()));

            Image image = redisManager.loadImage(Stamp.class, data.getId().toString());
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
                data = new Stamp();
            }

            data.setName(Formatter.checkStringIsEmpty(tfItemName.getText()));
            data.setSeries(Formatter.checkStringIsEmpty(tfSeries.getText()));
            data.setCreationYear(Formatter.convertToInteger(tfCreationYear.getText()));
            data.setEnteringDate(dpEnteringDate.getValue());
            data.setWorth(Formatter.convertToDouble(tfWorth.getText()));

            data.getArtist().setSurname(Formatter.checkStringIsEmpty(tfSurname.getText()));
            data.getArtist().setName(Formatter.checkStringIsEmpty(tfName.getText()));
            data.getArtist().setPatronymic(Formatter.checkStringIsEmpty(tfPatronymic.getText()));

            data.getDenomination().setValue(Formatter.convertToInteger(tfDenominationValue.getText()));
            data.getDenomination().setUnit(Formatter.checkStringIsEmpty(tfMonetaryUnit.getText()));
            data.setEdition(Formatter.convertToLong(tfEdition.getText()));
            data.setPrintingType(cbPrinting.getValue());
            data.setPerforationType(cbPerforation.getValue());

            data.getGauges().setTop(Formatter.convertToDouble(tfTop.getText()));
            data.getGauges().setRight(Formatter.convertToDouble(tfRight.getText()));
            data.getGauges().setBottom(Formatter.convertToDouble(tfBottom.getText()));
            data.getGauges().setLeft(Formatter.convertToDouble(tfLeft.getText()));

            if (isNewObject) {
                mongoManager.insert(Stamp.class, data);
            } else {
                mongoManager.update(Stamp.class, data);
            }
            if (isNewImage) {
                redisManager.saveImage(Stamp.class, data.getId().toString(), ivPicture.getImage());
            }

            goToTableScene();
        } else {
            InflateUtils.createAndShowError("Введите название марки.");
        }
    }

    protected void goToTableScene() {
        List<Stamp> content = mongoManager.getItems(Stamp.class);
        TableView<Stamp> table = TableManager.getStampsTable(content);
        NavigationManager.from(ivPicture).goToTableScene(table, "Марки");
    }
}
