package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Account;

import java.io.IOException;

public class HomeController {
    private Account account;
    @FXML private Button button;
    @FXML private Label accountLabel;


    public void logoutBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 404, 550));
        if (account != null) account = null;
        stage.show();
    }

    public void staffBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/staffView.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 600, 456));
        StaffViewController staffViewController = fxmlLoader.getController();
        staffViewController.setAccount(account);
        stage.show();
    }

    public void orderBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/order.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 404, 550));
        OrderController orderController = fxmlLoader.getController();
        orderController.setAccount(account);
        stage.show();
    }

    public void setUpAccountLabel() {
        if (account != null) {
            accountLabel.setText(account.getType() + " : " + account.getFirstName() + " " + account.getLastName());
            accountLabel.disabledProperty();
        }
    }

    public void setAccount(Account account) {
        this.account = account;
        this.setUpAccountLabel();
    }
}
