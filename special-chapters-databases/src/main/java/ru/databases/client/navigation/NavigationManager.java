package ru.databases.client.navigation;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.databases.client.models.*;
import ru.databases.client.utils.InflateUtils;
import ru.databases.client.controllers.BlankController;
import ru.databases.client.utils.MongoManager;

public class NavigationManager {
    private Stage stage;

    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    public static NavigationManager from(Node node) {
        return (NavigationManager) node.getScene().getUserData();
    }

    public void goToLoginScene() {
        final Parent root = InflateUtils.loadFxml("/fxml/login_controller.fxml");
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
        stage.setTitle("Вход");
    }

    public void goToBlankScene() {
        final Parent root = InflateUtils.loadFxml("/fxml/blank_controller.fxml");
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
        stage.setTitle(getTitle());
    }

    public void goToTableScene(TableView table, String title) {
        BlankController blankController = new BlankController(table, title);
        final Parent root = InflateUtils.loadFxmlWithCustomController("/fxml/blank_controller.fxml", blankController);
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
        stage.setTitle(getTitle());
    }

    public void goToChangeScene(Class<? extends Item> contentClass, Item contentInstance) {
        String fullClassName = contentClass.getName();
        String shortClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

        Object controller = null;
        try {
            Class controllerClass = Class.forName("ru.databases.client.controllers." + shortClassName + "Controller");
            controller = controllerClass.newInstance();
            controllerClass.getDeclaredField("data").set(controller, contentInstance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        final Parent root = InflateUtils.loadFxmlWithCustomController("/fxml/" + shortClassName + "_controller.fxml",
                controller);
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
        stage.setTitle(getTitle(contentInstance.getName(), contentClass));
    }

    public void goToCreateScene(Class<?> contentClass) {
        String fullClassName = contentClass.getName();
        String shortClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);

        final Parent root = InflateUtils.loadFxml("/fxml/" + shortClassName + "_controller.fxml");
        final Scene scene = createSceneWithThisNavigationManager(root);
        stage.setScene(scene);
        stage.setTitle(getTitle(contentClass));
    }

    private Scene createSceneWithThisNavigationManager(Parent root) {
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(root);
            scene.setUserData(this);
        } else {
            scene.setRoot(root);
        }
        return scene;
    }

    private String getTitle() {
        String userName = MongoManager.getInstance().getUser();
        return String.format("Пользователь %s", userName);
    }

    private String getTitle(String itemName, Class<?> itemClass) {
        String title = null;
        if (itemClass == Coin.class) {
            title = "Монета";
        } else if (itemClass == Stamp.class) {
            title = "Марка";
        } else if (itemClass == Book.class) {
            title = "Книга";
        } else if (itemClass == Painting.class) {
            title = "Картина";
        }
        title += " \"%s\"";
        return String.format(title, itemName);
    }

    private String getTitle(Class<?> itemClass) {
        String title = null;
        if (itemClass == Coin.class) {
            title = "Новая монета";
        } else if (itemClass == Stamp.class) {
            title = "Новая марка";
        } else if (itemClass == Book.class) {
            title = "Новая книга";
        } else if (itemClass == Painting.class) {
            title = "Новая картина";
        }
        return title;
    }
}
