package ru.databases.client.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ru.databases.client.utils.MongoManager;
import ru.databases.client.utils.RedisManager;

import java.io.File;
import java.net.MalformedURLException;

public abstract class FormController implements Initializable {
    @FXML
    protected ImageView ivPicture;

    protected boolean isNewImage = false;
    protected final MongoManager mongoManager = MongoManager.getInstance();
    protected final RedisManager redisManager = RedisManager.getInstance();
    protected final double imageSize = 240;

    @FXML
    public void cancel() {
        goToTableScene();
    }

    @FXML
    public void onImageClicked() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Изображение (*.png, *.jpg, *.jpeg)",
                "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(imageFilter);

        File file = fileChooser.showOpenDialog(ivPicture.getScene().getWindow());
        if (file != null) {
            try {
                ivPicture.setImage(new Image(file.toURI().toURL().toExternalForm()));
                if (!isNewImage) {
                    isNewImage = true;
                    ivPicture.setFitWidth(imageSize);
                    ivPicture.setFitHeight(imageSize);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void goToTableScene();
}
