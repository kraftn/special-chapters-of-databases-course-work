package ru.databases.client.controllers;

import com.mongodb.MongoSecurityException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.databases.client.navigation.NavigationManager;
import ru.databases.client.utils.InflateUtils;
import ru.databases.client.utils.MongoManager;

public class LoginController {
    @FXML
    TextField tfLogin;
    @FXML
    PasswordField pfPassword;

    @FXML
    private void enter() {
        String loginName = tfLogin.getText();
        String password = pfPassword.getText();
        if (loginName.isEmpty() || password.isEmpty()) {
            InflateUtils.createAndShowError("Введите логин и пароль.");
            return;
        }

        try {
            MongoManager.getInstance(loginName, password).getRoleName();
        }
        catch (MongoSecurityException e) {
            MongoManager.close();
            InflateUtils.createAndShowError("Неверный логин или пароль!");
            return;
        }

        NavigationManager.from(tfLogin).goToBlankScene();
    }
}
