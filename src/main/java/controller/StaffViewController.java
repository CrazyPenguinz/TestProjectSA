package controller;

import database.AccountDBConnector;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.Optional;

public class StaffViewController {
    private Account account;
    @FXML private TableView<Account> accountTableView;
    @FXML private TableColumn type, firstName, lastName;
    @FXML private Button addButton, backButton, deleteButton;

    public void initialize() {
        type.setCellValueFactory(new PropertyValueFactory<Account, String>("type"));
        firstName.setCellValueFactory(new PropertyValueFactory<Account, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Account, String>("lastName"));
        accountTableView.setItems(AccountDBConnector.getAccounts());
        accountTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Account>() {
            @Override
            public void changed(ObservableValue<? extends Account> observable, Account oldValue, Account newValue) {
                deleteButton.setDisable(false);
            }
        });
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

    public void deleteBtnOnAction(ActionEvent event) throws IOException {
        if (accountTableView.getSelectionModel().getSelectedItem() != null) {
            String username = accountTableView.getSelectionModel().getSelectedItem().getUsername();
            String password = accountTableView.getSelectionModel().getSelectedItem().getPassword();
//            Optional<String> result =
            AccountDBConnector.deleteAccount(username);
            if (username.equals(account.getUsername()) && password.equals(account.getPassword())) {
                Button button = (Button) event.getSource();
                Stage stage = (Stage) button.getScene().getWindow();
                backToLogin(stage);
            }
            accountTableView.setItems(AccountDBConnector.getAccounts());
        }
    }

    private void backToLogin(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 404, 550));
        stage.show();
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
