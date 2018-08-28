package controller;

import database.AccountDBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Account;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML protected Button button;
    @FXML protected TextField username;
    @FXML protected PasswordField password;
    @FXML protected Label caution;

    public void initialize() throws SQLException {
        caution.setVisible(false);
        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    TextField userName  = (TextField) event.getSource();
                    Stage stage = (Stage) userName.getScene().getWindow();
                    try {
                        checkUsernameAndPassword(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    PasswordField passwordField  = (PasswordField) event.getSource();
                    Stage stage = (Stage) passwordField.getScene().getWindow();
                    try {
                        checkUsernameAndPassword(stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void handleLoginBtn(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        this.checkUsernameAndPassword(stage);
    }

    private void checkUsernameAndPassword(Stage stage) throws IOException {
        boolean loginStatus = false;
        username.getText();
        password.getText();
        Account account = AccountDBConnector.isLogin(username.getText(),password.getText());
        if (account != null) {
            loginStatus = true;
            this.loginToHome(stage, account);
        }
        if (!loginStatus) {
            caution.setVisible(true);
            caution.setText("Username or Password is incorrect.");
        }
    }

    private void loginToHome(Stage stage, Account account) throws IOException {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/home.fxml"));
        stage.setScene(new Scene(loader.load(), 574, 456));
        HomeController homeController = loader.getController();
        homeController.setAccount(account);
        caution.setVisible(false);
        stage.show();
    }
}
