package controller;

import database.AccountDBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Account;
import utilities.CheckInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CreateAccountController {
    private Account account;
    @FXML
    private Button backButton, registerButton;
    @FXML
    private TextField username, firstName, lastName, type;
    @FXML
    private PasswordField password;

    public void confirmBtnOnAction(ActionEvent event) throws IOException {
        List<Boolean> checkBoolean = new ArrayList<>();
        checkBoolean.add(CheckInput.isAllCharacter(firstName));
        checkBoolean.add(CheckInput.isAllCharacter(lastName));
        List<String> checkTextField = new ArrayList<>();
        checkTextField.add(firstName.getText());
        checkTextField.add(lastName.getText());
        if(CheckInput.isAllCorrectEmpty(checkTextField) && CheckInput.isAllCorrectType(checkBoolean) && CheckInput.isCorrectUsername(AccountDBConnector.getAccounts(),username)) {
            AccountDBConnector.addAccount(type.getText(), username.getText(), password.getText(), firstName.getText(), lastName.getText());
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            this.resetField();
            this.backToStaffTable(stage);
        }
    }

    public void backBtnOnAction(ActionEvent event) throws IOException {
        Button buttonBack = (Button) event.getSource();
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        this.backToStaffTable(stage);
    }

    private void backToStaffTable(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/staffView.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 574, 456));
        StaffViewController staffViewController = fxmlLoader.getController();
        staffViewController.setAccount(account);
        stage.show();
    }

    private void resetField() {
        firstName.setText("");
        lastName.setText("");
        username.setText("");
        password.setText("");
        type.setText("");
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
