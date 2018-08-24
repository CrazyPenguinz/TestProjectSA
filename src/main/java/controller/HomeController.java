package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Account;

import java.io.IOException;

public class HomeController {
    Account account;
    @FXML Button button;

    public void loginWith(Account account) {
        this.account = account;
    }

    public void handleLogoutBtn(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        this.backToLogin(stage);
    }

    private void backToLogin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 404, 550));
        if (account != null) account = null;
        stage.show();
    }
}
