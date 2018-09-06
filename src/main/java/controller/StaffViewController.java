package controller;

import database.AccountDBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Account;

import java.io.IOException;

public class StaffViewController {
    private Account account;
    @FXML private TableView<Account> accountTableView;
    @FXML private TableColumn type, firstName, lastName;
    @FXML private Button addButton, backButton;

    public void initialize() {
        type.setCellValueFactory(new PropertyValueFactory<Account, String>("type"));
        firstName.setCellValueFactory(new PropertyValueFactory<Account, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Account, String>("lastName"));
        accountTableView.setItems(AccountDBConnector.getAccounts());
    }

    public void backBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        this.backToHome(stage);
    }

    public void addBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        this.goToCreateAccount(stage);
    }

    private void backToHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 574, 456));
        HomeController homeController = fxmlLoader.getController();
        homeController.setAccount(account);
        stage.show();
    }

    private void goToCreateAccount(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/createAccount.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 541, 400));
        CreateAccountController createAccountController = fxmlLoader.getController();
        createAccountController.setAccount(account);
        stage.show();
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
