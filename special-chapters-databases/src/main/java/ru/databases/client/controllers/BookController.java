package ru.databases.client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import ru.databases.client.changelisteners.DoubleValueChangeListener;
import ru.databases.client.changelisteners.IntegerValueChangeListener;
import ru.databases.client.changelisteners.YearChangeListener;
import ru.databases.client.models.Book;
import ru.databases.client.options.BookFormat;
import ru.databases.client.utils.*;
import ru.databases.client.navigation.NavigationManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookController extends FormController {
    @FXML
    private TextField tfItemName, tfCreationYear, tfWorth, tfSurname, tfName, tfPatronymic, tfPublicationPlace,
            tfPublishingHouse, tfEdition, tfPagesNumber, tfBinding;
    @FXML
    private DatePicker dpEnteringDate;
    @FXML
    private ComboBox<BookFormat> cbFormat;
    @FXML
    private Button bOk;

    public Book data;

    private void setChangeListeners() {
        tfCreationYear.textProperty().addListener(new YearChangeListener(tfCreationYear));
        tfWorth.textProperty().addListener(new DoubleValueChangeListener(tfWorth));
        tfEdition.textProperty().addListener(new IntegerValueChangeListener(tfEdition));
        tfPagesNumber.textProperty().addListener(new IntegerValueChangeListener(tfPagesNumber));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setChangeListeners();
        InflateUtils.fillComboBox(cbFormat, BookFormat.values());
        if (!mongoManager.isWritingNecessary()) {
            bOk.setDisable(true);
        }

        if (data != null) {
            tfItemName.setText(Formatter.checkStringIsNull(data.getName()));
            tfCreationYear.setText(Formatter.convertToString(data.getCreationYear()));
            dpEnteringDate.setValue(data.getEnteringDate());
            tfWorth.setText(Formatter.convertToString(data.getWorth()));

            tfSurname.setText(Formatter.checkStringIsNull(data.getAuthor().getSurname()));
            tfName.setText(Formatter.checkStringIsNull(data.getAuthor().getName()));
            tfPatronymic.setText(Formatter.checkStringIsNull(data.getAuthor().getPatronymic()));

            tfPublicationPlace.setText(Formatter.checkStringIsNull(data.getPublicationPlace()));
            tfPublishingHouse.setText(Formatter.checkStringIsNull(data.getPublishingHouse()));
            tfEdition.setText(Formatter.convertToString(data.getEdition()));
            tfPagesNumber.setText(Formatter.convertToString(data.getPagesNumber()));

            tfBinding.setText(Formatter.checkStringIsNull(data.getBinding()));
            cbFormat.setValue(data.getFormat());

            Image image = redisManager.loadImage(Book.class, data.getId().toString());
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
                data = new Book();
            }

            data.setName(Formatter.checkStringIsEmpty(tfItemName.getText()));
            data.setCreationYear(Formatter.convertToInteger(tfCreationYear.getText()));
            data.setEnteringDate(dpEnteringDate.getValue());
            data.setWorth(Formatter.convertToDouble(tfWorth.getText()));

            data.getAuthor().setSurname(Formatter.checkStringIsEmpty(tfSurname.getText()));
            data.getAuthor().setName(Formatter.checkStringIsEmpty(tfName.getText()));
            data.getAuthor().setPatronymic(Formatter.checkStringIsEmpty(tfPatronymic.getText()));

            data.setPublicationPlace(Formatter.checkStringIsEmpty(tfPublicationPlace.getText()));
            data.setPublishingHouse(Formatter.checkStringIsEmpty(tfPublishingHouse.getText()));
            data.setEdition(Formatter.convertToLong(tfEdition.getText()));
            data.setPagesNumber(Formatter.convertToInteger(tfPagesNumber.getText()));

            data.setBinding(Formatter.checkStringIsEmpty(tfBinding.getText()));
            data.setFormat(cbFormat.getValue());

            if (isNewObject) {
                mongoManager.insert(Book.class, data);
            } else {
                mongoManager.update(Book.class, data);
            }
            if (isNewImage) {
                redisManager.saveImage(Book.class, data.getId().toString(), ivPicture.getImage());
            }

            goToTableScene();
        } else {
            InflateUtils.createAndShowError("Введите название книги.");
        }
    }

    protected void goToTableScene() {
        List<Book> content = mongoManager.getItems(Book.class);
        TableView<Book> table = TableManager.getBooksTable(content);
        NavigationManager.from(ivPicture).goToTableScene(table, "Книги");
    }
}
