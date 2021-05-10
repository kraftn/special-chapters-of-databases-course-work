package ru.databases.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import ru.databases.client.models.*;
import ru.databases.client.navigation.NavigationManager;
import ru.databases.client.utils.*;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BlankController implements Initializable {
    @FXML
    private BorderPane bpLayout;
    @FXML
    private Label lTitle;

    private TableView tvMainTable = null;
    private String title;

    public BlankController() {}

    public BlankController(TableView table, String title) {
        this.tvMainTable = table;
        this.title = title;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (tvMainTable != null) {
            lTitle.setText(title);
            bpLayout.setCenter(tvMainTable);
        }
    }

    @FXML
    public void showCoinsTable() {
        List<Coin> content = MongoManager.getInstance().getItems(Coin.class);
        NavigationManager.from(bpLayout).goToTableScene(TableManager.getCoinsTable(content), "Монеты");
    }

    @FXML
    public void showStampsTable() {
        List<Stamp> content = MongoManager.getInstance().getItems(Stamp.class);
        NavigationManager.from(bpLayout).goToTableScene(TableManager.getStampsTable(content), "Марки");
    }

    @FXML
    public void showBooksTable() {
        List<Book> content = MongoManager.getInstance().getItems(Book.class);
        NavigationManager.from(bpLayout).goToTableScene(TableManager.getBooksTable(content), "Книги");
    }

    @FXML
    public void showPaintingsTable() {
        List<Painting> content = MongoManager.getInstance().getItems(Painting.class);
        NavigationManager.from(bpLayout).goToTableScene(TableManager.getPaintingsTable(content), "Картины");
    }

    @FXML
    public void findItemsByDate() {
        Dialog<FindItemsByDateController.ResultDialog> dialog = new Dialog<>();
        FindItemsByDateController controller = new FindItemsByDateController(dialog);
        Parent layout = InflateUtils.loadFxmlWithCustomController("/fxml/find_items_by_date_controller.fxml", controller);
        dialog.getDialogPane().setContent(layout);
        Optional<FindItemsByDateController.ResultDialog> resultDialog = dialog.showAndWait();

        if (resultDialog.isPresent()){
            if (null == resultDialog.get().getBeginDate() || null == resultDialog.get().getEndDate()){
                InflateUtils.createAndShowError("Укажите все параметры поиска.");
            } else {
                List<Item> content = MongoManager.getInstance().findItemsByDate(resultDialog.get().getBeginDate(),
                        resultDialog.get().getEndDate());
                TableView<Item> table = TableManager.getItemsTable(content);
                NavigationManager.from(bpLayout).goToTableScene(table, "Найденные предметы");
            }
        }
    }

    @FXML
    public void findItemsByWorth() {
        Dialog<FindItemsByNumberController.DoublesResultDialog> dialog = new Dialog<>();
        FindItemsByNumberController controller = new FindItemsByNumberController(dialog, "Найти предметы по стоимости",
                false);
        Parent layout = InflateUtils.loadFxmlWithCustomController("/fxml/find_items_by_number_controller.fxml", controller);
        dialog.getDialogPane().setContent(layout);
        controller.setListeners();
        Optional<FindItemsByNumberController.DoublesResultDialog> resultDialog = dialog.showAndWait();

        if (resultDialog.isPresent()){
            if (null == resultDialog.get().getBegin() || null == resultDialog.get().getEnd()){
                InflateUtils.createAndShowError("Укажите все параметры поиска.");
            } else {
                List<Item> content = MongoManager.getInstance().findItemsByWorth(resultDialog.get().getBegin(),
                        resultDialog.get().getEnd());
                TableView<Item> table = TableManager.getItemsTable(content);
                NavigationManager.from(bpLayout).goToTableScene(table, "Найденные предметы");
            }
        }
    }

    @FXML
    public void calculateWorth() {
        Double coinsWorth = MongoManager.getInstance().calculateTotalWorth(Coin.class);
        Double stampsWorth = MongoManager.getInstance().calculateTotalWorth(Stamp.class);
        Double booksWorth = MongoManager.getInstance().calculateTotalWorth(Book.class);
        Double paintingsWorth = MongoManager.getInstance().calculateTotalWorth(Painting.class);

        String message = "Стоимость коллекций представлена в рублях.\nМонеты: %s\nМарки: %s\nКниги: %s\nКартины: %s";
        InflateUtils.createAndShowInformation(String.format(message, Formatter.convertToString(coinsWorth),
                Formatter.convertToString(stampsWorth), Formatter.convertToString(booksWorth),
                Formatter.convertToString(paintingsWorth)));
    }

    @FXML
    public void findCoinsByEdition() {
        Dialog<FindItemsByNumberController.IntegersResultDialog> dialog = new Dialog<>();
        FindItemsByNumberController controller = new FindItemsByNumberController(dialog, "Найти монеты по тиражу",
                true);
        Parent layout = InflateUtils.loadFxmlWithCustomController("/fxml/find_items_by_number_controller.fxml", controller);
        dialog.getDialogPane().setContent(layout);
        controller.setListeners();
        Optional<FindItemsByNumberController.IntegersResultDialog> resultDialog = dialog.showAndWait();

        if (resultDialog.isPresent()){
            if (null == resultDialog.get().getBegin() || null == resultDialog.get().getEnd()){
                InflateUtils.createAndShowError("Укажите все параметры поиска.");
            } else {
                List<Coin> content = MongoManager.getInstance().findItemsByEdition(Coin.class,
                        resultDialog.get().getBegin(), resultDialog.get().getEnd());
                TableView<Coin> table = TableManager.getCoinsTable(content);
                NavigationManager.from(bpLayout).goToTableScene(table, "Найденные монеты");
            }
        }
    }

    @FXML
    public void findCoinsBySeries() {
        Optional<String> resultDialog = showTextInputDialog("Найти монеты из одной серии", "Серия монет");

        if (resultDialog.isPresent()){
            if (resultDialog.get().isEmpty()){
                InflateUtils.createAndShowError("Укажите серию монет.");
            } else {
                List<Coin> content = MongoManager.getInstance().findItemsBySeries(Coin.class, resultDialog.get());
                TableView<Coin> table = TableManager.getCoinsTable(content);
                NavigationManager.from(bpLayout).goToTableScene(table, "Найденные монеты");
            }
        }
    }

    @FXML
    public void findStampsByEdition() {
        Dialog<FindItemsByNumberController.IntegersResultDialog> dialog = new Dialog<>();
        FindItemsByNumberController controller = new FindItemsByNumberController(dialog, "Найти марки по тиражу",
                true);
        Parent layout = InflateUtils.loadFxmlWithCustomController("/fxml/find_items_by_number_controller.fxml", controller);
        dialog.getDialogPane().setContent(layout);
        controller.setListeners();
        Optional<FindItemsByNumberController.IntegersResultDialog> resultDialog = dialog.showAndWait();

        if (resultDialog.isPresent()){
            if (null == resultDialog.get().getBegin() || null == resultDialog.get().getEnd()){
                InflateUtils.createAndShowError("Укажите все параметры поиска.");
            } else {
                List<Stamp> content = MongoManager.getInstance().findItemsByEdition(Stamp.class,
                        resultDialog.get().getBegin(), resultDialog.get().getEnd());
                TableView<Stamp> table = TableManager.getStampsTable(content);
                NavigationManager.from(bpLayout).goToTableScene(table, "Найденные марки");
            }
        }
    }

    @FXML
    public void findStampsBySeries() {
        Optional<String> resultDialog = showTextInputDialog("Найти марки из одной серии", "Серия марок");

        if (resultDialog.isPresent()){
            if (resultDialog.get().isEmpty()){
                InflateUtils.createAndShowError("Укажите серию марок.");
            } else {
                List<Stamp> content = MongoManager.getInstance().findItemsBySeries(Stamp.class, resultDialog.get());
                TableView<Stamp> table = TableManager.getStampsTable(content);
                NavigationManager.from(bpLayout).goToTableScene(table, "Найденные марки");
            }
        }
    }

    @FXML
    public void findBooksByEdition() {
        Dialog<FindItemsByNumberController.IntegersResultDialog> dialog = new Dialog<>();
        FindItemsByNumberController controller = new FindItemsByNumberController(dialog, "Найти книги по тиражу",
                true);
        Parent layout = InflateUtils.loadFxmlWithCustomController("/fxml/find_items_by_number_controller.fxml", controller);
        dialog.getDialogPane().setContent(layout);
        controller.setListeners();
        Optional<FindItemsByNumberController.IntegersResultDialog> resultDialog = dialog.showAndWait();

        if (resultDialog.isPresent()){
            if (null == resultDialog.get().getBegin() || null == resultDialog.get().getEnd()){
                InflateUtils.createAndShowError("Укажите все параметры поиска.");
            } else {
                List<Book> content = MongoManager.getInstance().findItemsByEdition(Book.class,
                        resultDialog.get().getBegin(), resultDialog.get().getEnd());
                TableView<Book> table = TableManager.getBooksTable(content);
                NavigationManager.from(bpLayout).goToTableScene(table, "Найденные книги");
            }
        }
    }

    @FXML
    public void findBooksByAuthor() {
        Dialog<Person> dialog = new Dialog<>();
        FindItemsByPersonController controller = new FindItemsByPersonController(dialog, "Найти книги одного автора");
        Parent layout = InflateUtils.loadFxmlWithCustomController("/fxml/find_items_by_person_controller.fxml", controller);
        dialog.getDialogPane().setContent(layout);
        Optional<Person> resultDialog = dialog.showAndWait();

        if (resultDialog.isPresent()){
            if (null == resultDialog.get().getName() && null == resultDialog.get().getSurname() && null == resultDialog.get().getPatronymic()){
                InflateUtils.createAndShowError("Укажите параметры поиска.");
            } else {
                List<Book> content = MongoManager.getInstance().findItemsByAuthor(Book.class, resultDialog.get());
                TableView<Book> table = TableManager.getBooksTable(content);
                NavigationManager.from(bpLayout).goToTableScene(table, "Найденные книги");
            }
        }
    }

    @FXML
    public void findPaintingsByArtist() {
        Dialog<Person> dialog = new Dialog<>();
        FindItemsByPersonController controller = new FindItemsByPersonController(dialog, "Найти картины одного художника");
        Parent layout = InflateUtils.loadFxmlWithCustomController("/fxml/find_items_by_person_controller.fxml", controller);
        dialog.getDialogPane().setContent(layout);
        Optional<Person> resultDialog = dialog.showAndWait();

        if (resultDialog.isPresent()){
            if (null == resultDialog.get().getName() && null == resultDialog.get().getSurname() && null == resultDialog.get().getPatronymic()){
                InflateUtils.createAndShowError("Укажите параметры поиска.");
            } else {
                List<Painting> content = MongoManager.getInstance().findItemsByArtist(Painting.class, resultDialog.get());
                TableView<Painting> table = TableManager.getPaintingsTable(content);
                NavigationManager.from(bpLayout).goToTableScene(table, "Найденные картины");
            }
        }
    }

    @FXML
    public void close() {
        MongoManager.close();
        RedisManager.close();
        NavigationManager.from(bpLayout).goToLoginScene();
    }

    private static Optional<String> showTextInputDialog(String title, String contentText) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText("Укажите необходимые параметры поиска:");
        dialog.setContentText(contentText);
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("ОК",
                ButtonBar.ButtonData.OK_DONE), new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE));

        return dialog.showAndWait();
    }
}
