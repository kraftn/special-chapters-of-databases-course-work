package ru.databases.client.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.util.Callback;

import ru.databases.client.models.*;
import ru.databases.client.navigation.NavigationManager;
import ru.databases.client.options.BookFormat;
import ru.databases.client.options.CoinageQuality;
import ru.databases.client.options.PerforationType;
import ru.databases.client.options.PrintingType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class TableManager {
    private static final String NO_CONTENT_TEXT = "Пусто";

    private TableManager() {}

    public static TableView<Coin> getCoinsTable(List<Coin> content) {
        TableView<Coin> table = new TableView<>();
        table.setPlaceholder(new Label(NO_CONTENT_TEXT));

        TableColumn<Coin, String> nameColumn = getColumn("Название", "name");
        TableColumn<Coin, String> seriesColumn = getColumn("Серия", "series");
        TableColumn<Coin, Integer> editionColumn = getColumn("Тираж", "edition");
        TableColumn<Coin, Integer> creationYearColumn = getColumn("Год выпуска", "creationYear");
        TableColumn<Coin, String> enteringDateColumn = getDateColumn("Дата поступления в коллекцию");

        TableColumn<Coin, Integer> denominationValueColumn = new TableColumn<>("Номинал");
        denominationValueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Coin, Integer> param) {
                Denomination denomination = param.getValue().getDenomination();
                if (denomination.getValue() != null) {
                    return new SimpleIntegerProperty(denomination.getValue()).asObject();
                } else {
                    return null;
                }
            }
        });

        TableColumn<Coin, String> monetaryUnitColumn = new TableColumn<>("Денежная единица номинала");
        monetaryUnitColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Coin, String> param) {
                Denomination denomination = param.getValue().getDenomination();
                return new SimpleStringProperty(denomination.getUnit());

            }
        });

        TableColumn<Coin, String> coinageQualityColumn = new TableColumn<>("Качество чеканки");
        coinageQualityColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Coin, String> param) {
                CoinageQuality coinageQuality = param.getValue().getCoinageQuality();
                if (coinageQuality != null) {
                    return new SimpleStringProperty(coinageQuality.toString());
                } else {
                    return new SimpleStringProperty();
                }
            }
        });

        TableColumn<Coin, String> materialColumn = new TableColumn<>("Материал");
        materialColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Coin, String> param) {
                return new SimpleStringProperty(param.getValue().getMaterial());
            }
        });

        TableColumn<Coin, String> diameterColumn = new TableColumn<>("Диаметр, мм");
        diameterColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Coin, String> param) {
                return new SimpleStringProperty(Formatter.convertToString(param.getValue().getPhysicalCharacteristics().getDiameter()));
            }
        });

        TableColumn<Coin, String> thicknessColumn = new TableColumn<>("Толщина, мм");
        thicknessColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Coin, String> param) {
                return new SimpleStringProperty(Formatter.convertToString(param.getValue().getPhysicalCharacteristics().getThickness()));
            }
        });

        TableColumn<Coin, String> weightColumn = new TableColumn<>("Масса, г");
        weightColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Coin, String> param) {
                return new SimpleStringProperty(Formatter.convertToString(param.getValue().getPhysicalCharacteristics().getWeight()));
            }
        });

        TableColumn<Coin, String> worthColumn = new TableColumn<>("Стоимость, рубли");
        worthColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Coin, String> param) {
                return new SimpleStringProperty(Formatter.convertToString(param.getValue().getWorth()));
            }
        });

        table.getColumns().addAll(nameColumn, seriesColumn, denominationValueColumn, monetaryUnitColumn,
                creationYearColumn, editionColumn, coinageQualityColumn, materialColumn, diameterColumn, thicknessColumn,
                weightColumn, enteringDateColumn, worthColumn);

        setContextMenuToTable(table, Coin.class);
        setContent(table, content);
        table.getSelectionModel().select(0);

        return table;
    }

    public static TableView<Stamp> getStampsTable(List<Stamp> content) {
        TableView<Stamp> table = new TableView<>();
        table.setPlaceholder(new Label(NO_CONTENT_TEXT));

        TableColumn<Stamp, String> nameColumn = getColumn("Название", "name");
        TableColumn<Stamp, String> seriesColumn = getColumn("Серия", "series");
        TableColumn<Stamp, Integer> creationYearColumn = getColumn("Год выпуска", "creationYear");
        TableColumn<Stamp, Integer> editionColumn = getColumn("Тираж", "edition");
        TableColumn<Stamp, String> enteringDateColumn = getDateColumn("Дата поступдения в коллекцию");

        TableColumn<Stamp, Integer> denominationValueColumn = new TableColumn<>("Номинал");
        denominationValueColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stamp, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Stamp, Integer> param) {
                Denomination denomination = param.getValue().getDenomination();
                if (denomination.getValue() != null) {
                    return new SimpleIntegerProperty(denomination.getValue()).asObject();
                } else {
                    return null;
                }
            }
        });

        TableColumn<Stamp, String> monetaryUnitColumn = new TableColumn<>("Денежная единица номинала");
        monetaryUnitColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stamp, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stamp, String> param) {
                Denomination denomination = param.getValue().getDenomination();
                return new SimpleStringProperty(denomination.getUnit());
            }
        });

        TableColumn<Stamp, String> artistColumn = new TableColumn<>("Художник");
        artistColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stamp, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stamp, String> param) {
                return new SimpleStringProperty(param.getValue().getArtist().toString());
            }
        });

        TableColumn<Stamp, String> printingColumn = new TableColumn<>("Печать");
        printingColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stamp, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stamp, String> param) {
                PrintingType printingType = param.getValue().getPrintingType();
                if (printingType != null) {
                    return new SimpleStringProperty(printingType.toString());
                } else {
                    return new SimpleStringProperty();
                }
            }
        });

        TableColumn<Stamp, String> perforationColumn = new TableColumn<>("Перфорация");
        perforationColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stamp, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stamp, String> param) {
                PerforationType perforationType = param.getValue().getPerforationType();
                if (perforationType != null) {
                    return new SimpleStringProperty(perforationType.toString());
                } else {
                    return new SimpleStringProperty();
                }
            }
        });

        TableColumn<Stamp, String> gaugesColumn = new TableColumn<>("Зубцовка");
        gaugesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stamp, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stamp, String> param) {
                Gauges gauges = param.getValue().getGauges();

                StringBuilder res = new StringBuilder();
                res.append(":").append(Formatter.convertToString(gauges.getTop()));
                res.append(":").append(Formatter.convertToString(gauges.getRight()));
                res.append(":").append(Formatter.convertToString(gauges.getBottom()));
                res.append(":").append(Formatter.convertToString(gauges.getLeft()));

                return new SimpleStringProperty(res.substring(1));
            }
        });

        TableColumn<Stamp, String> worthColumn = new TableColumn<>("Стоимость, рубли");
        worthColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stamp, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Stamp, String> param) {
                return new SimpleStringProperty(Formatter.convertToString(param.getValue().getWorth()));
            }
        });

        table.getColumns().addAll(nameColumn, seriesColumn, denominationValueColumn, monetaryUnitColumn, artistColumn,
                creationYearColumn, editionColumn, printingColumn, perforationColumn, gaugesColumn, enteringDateColumn,
                worthColumn);

        setContextMenuToTable(table, Stamp.class);
        setContent(table, content);
        table.getSelectionModel().select(0);

        return table;
    }

    public static TableView<Book> getBooksTable(List<Book> content) {
        TableView<Book> table = new TableView<>();
        table.setPlaceholder(new Label(NO_CONTENT_TEXT));

        TableColumn<Book, String> nameColumn = getColumn("Название", "name");
        TableColumn<Book, Integer> creationYearColumn = getColumn("Год выпуска", "creationYear");
        TableColumn<Book, Integer> editionColumn = getColumn("Тираж", "edition");
        TableColumn<Book, String> publicationPlaceColumn = getColumn("Место издания", "publicationPlace");
        TableColumn<Book, String> publishingHouseColumn = getColumn("Издательство", "publishingHouse");
        TableColumn<Book, String> bindingColumn = getColumn("Переплёт", "binding");
        TableColumn<Book, Integer> pagesNumberColumn = getColumn("Количество страниц", "pagesNumber");
        TableColumn<Book, String> enteringDateColumn = getDateColumn("Дата поступдения в коллекцию");

        TableColumn<Book, String> authorColumn = new TableColumn<>("Автор");
        authorColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> param) {
                return new SimpleStringProperty(param.getValue().getAuthor().toString());
            }
        });

        TableColumn<Book, String> formatColumn = new TableColumn<>("Формат");
        formatColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> param) {
                BookFormat bookFormat = param.getValue().getFormat();
                if (bookFormat != null) {
                    return new SimpleStringProperty(bookFormat.toString());
                } else {
                    return new SimpleStringProperty();
                }
            }
        });

        TableColumn<Book, String> worthColumn = new TableColumn<>("Стоимость, рубли");
        worthColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Book, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Book, String> param) {
                return new SimpleStringProperty(Formatter.convertToString(param.getValue().getWorth()));
            }
        });

        table.getColumns().addAll(nameColumn, authorColumn, creationYearColumn, editionColumn, publicationPlaceColumn,
                publishingHouseColumn, bindingColumn, formatColumn, pagesNumberColumn, enteringDateColumn, worthColumn);

        setContextMenuToTable(table, Book.class);
        setContent(table, content);
        table.getSelectionModel().select(0);

        return table;
    }

    public static TableView<Painting> getPaintingsTable(List<Painting> content) {
        TableView<Painting> table = new TableView<>();
        table.setPlaceholder(new Label(NO_CONTENT_TEXT));

        TableColumn<Painting, String> nameColumn = getColumn("Название", "name");
        TableColumn<Painting, Integer> centuryColumn = getColumn("Век", "century");
        TableColumn<Painting, String> enteringDateColumn = getDateColumn("Дата поступления в коллекцию");
        TableColumn<Painting, String> genreColumn = getColumn("Жанр", "genre");
        TableColumn<Painting, String> styleColumn = getColumn("Стиль", "style");
        TableColumn<Painting, String> techniqueColumn = getColumn("Техника", "technique");

        TableColumn<Painting, String> artistColumn = new TableColumn<>("Художник");
        artistColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Painting, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Painting, String> param) {
                return new SimpleStringProperty(param.getValue().getArtist().toString());
            }
        });

        TableColumn<Painting, String> sizeColumn = new TableColumn<>("Размер, см");
        sizeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Painting, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Painting, String> param) {
                Painting.Size size = param.getValue().getSize();
                if (size.getWidth() != null || size.getHeight() != null) {
                    String res = Formatter.convertToString(size.getWidth()) + " x " + Formatter.convertToString(size.getHeight());
                    return new SimpleStringProperty(res);
                } else {
                    return new SimpleStringProperty();
                }

            }
        });

        TableColumn<Painting, String> worthColumn = new TableColumn<>("Стоимость, рубли");
        worthColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Painting, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Painting, String> param) {
                return new SimpleStringProperty(Formatter.convertToString(param.getValue().getWorth()));
            }
        });

        table.getColumns().addAll(nameColumn, artistColumn, centuryColumn, genreColumn, styleColumn, techniqueColumn,
                sizeColumn, enteringDateColumn, worthColumn);

        setContextMenuToTable(table, Painting.class);
        setContent(table, content);
        table.getSelectionModel().select(0);

        return table;
    }

    public static TableView<Item> getItemsTable(List<Item> content) {
        TableView<Item> table = new TableView<>();
        table.setPlaceholder(new Label(NO_CONTENT_TEXT));

        TableColumn<Item, String> typeColumn = new TableColumn<>("Тип");
        typeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                Item item = param.getValue();
                Class<?> itemClass = item.getClass();
                String res = null;
                if (Coin.class == itemClass) {
                    res = "Монета";
                } else if (Stamp.class == itemClass) {
                    res = "Марка";
                } else if (Book.class == itemClass) {
                    res = "Книга";
                } else if (Painting.class == itemClass) {
                    res = "Картина";
                }
                return new SimpleStringProperty(res);
            }
        });
        TableColumn<Item, String> nameColumn = getColumn("Название", "name");
        TableColumn<Item, String> seriesColumn = new TableColumn<>("Серия");
        seriesColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                Item item = param.getValue();
                Class<?> itemClass = item.getClass();
                String res = null;
                if (Coin.class == itemClass) {
                    res = ((Coin) item).getSeries();
                } else if (Stamp.class == itemClass) {
                    res = ((Stamp) item).getSeries();
                }
                return new SimpleStringProperty(res);
            }
        });
        TableColumn<Item, String> worthColumn = new TableColumn<>("Стоимость, рубли");
        worthColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> param) {
                return new SimpleStringProperty(Formatter.convertToString(param.getValue().getWorth()));
            }
        });

        table.getColumns().addAll(typeColumn, nameColumn, seriesColumn, worthColumn);
        setContent(table, content);
        return table;
    }

    private static <T extends Item> void setContent(TableView<T> table, List<T> content) {
        ObservableList<T> observableList = FXCollections.observableArrayList(content);
        table.setItems(observableList);
    }

    private static <T extends Item> void setContextMenuToTable(TableView<T> table, Class<T> contentClass) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItemCreate = new MenuItem("Создать");
        MenuItem menuItemChange = new MenuItem("Выбрать");
        MenuItem menuItemDelete = new MenuItem("Удалить");

        menuItemChange.setOnAction(event -> {
            NavigationManager.from(table).goToChangeScene(contentClass, table.getSelectionModel().getSelectedItem());
        });

        menuItemCreate.setOnAction(event -> {
            NavigationManager.from(table).goToCreateScene(contentClass);
        });

        menuItemDelete.setOnAction(event -> {
            Optional<ButtonType> res = InflateUtils.createAndShowConfirmation("Действительно удалить выбранный предмет?");
            if (res.isPresent() && res.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE)) {
                RedisManager.getInstance().deleteImage(contentClass, table.getSelectionModel().getSelectedItem().getId().toString());
                MongoManager.getInstance().delete(contentClass, table.getSelectionModel().getSelectedItem());
                table.getItems().remove(table.getSelectionModel().getSelectedItem());
            }
        });

        if (MongoManager.getInstance().isWritingNecessary()) {
            contextMenu.getItems().addAll(menuItemCreate, menuItemChange, menuItemDelete);
        } else {
            contextMenu.getItems().addAll(menuItemChange);
        }

        table.setContextMenu(contextMenu);
        table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                if (table.getItems().size() == 0) {
                    menuItemChange.setDisable(true);
                    menuItemDelete.setDisable(true);
                } else {
                    menuItemChange.setDisable(false);
                    menuItemDelete.setDisable(false);
                }
            }
        });
    }

    private static <T extends Item, S> TableColumn<T, S> getColumn(String text, String fieldName) {
        TableColumn<T, S> column = new TableColumn<>(text);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        return column;
    }

    private static <T extends Item> TableColumn<T, String> getDateColumn(String text) {
        TableColumn<T, String> column = new TableColumn<>(text);
        column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<T, String> param) {
                LocalDate enteringDate = param.getValue().getEnteringDate();
                if (enteringDate != null) {
                    String dateString = enteringDate.format(DateTimeFormatter.ofPattern("dd-LL-yyyy"));
                    return new SimpleStringProperty(dateString);
                } else {
                    return new SimpleStringProperty();
                }
            }
        });
        return column;
    }
}
