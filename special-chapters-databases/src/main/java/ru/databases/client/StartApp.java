package ru.databases.client;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.databases.client.navigation.NavigationManager;
import ru.databases.client.utils.MongoManager;
import ru.databases.client.utils.RedisManager;

public class StartApp extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMaximized(true);
        NavigationManager navigationManager = new NavigationManager(primaryStage);
        navigationManager.goToLoginScene();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        MongoManager.close();
        RedisManager.close();
    }
}
