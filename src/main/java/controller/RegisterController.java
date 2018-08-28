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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RegisterController {
    private Account account;
    @FXML
    private Button button;
    @FXML
    private TextField username, firstname, lastname, type;
    @FXML
    private PasswordField password;

    public void handleRegisterBtnAction(ActionEvent event) throws IOException, SQLException {
        List<Boolean> checkBoolean = new ArrayList<>();
        checkBoolean.add(CheckInput.isAllCharacter(firstname));
        checkBoolean.add(CheckInput.isAllCharacter(lastname));
        List<String> checkTextField = new ArrayList<>();
        checkTextField.add(firstname.getText());
        checkTextField.add(lastname.getText());
        if(CheckInput.isAllCorrectEmpty(checkTextField) && CheckInput.isAllCorrectType(checkBoolean) && CheckInput.isCorrectUsername(AccountDBConnector.getAccounts(),username)) {
            AccountDBConnector.addAccount(type.getText(), username.getText(), password.getText(), firstname.getText(), lastname.getText());
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            this.resetField();
            this.backToHome(stage);
        }
    }

    public void handleBackBtn(ActionEvent event) throws IOException {
        Button buttonBack = (Button) event.getSource();
        Stage stage = (Stage) buttonBack.getScene().getWindow();
        this.backToHome(stage);
    }

    private void backToHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 574, 456));
        HomeController homeController = fxmlLoader.getController();
        homeController.setAccount(account);
        stage.show();
    }

    private void resetField() {
        firstname.setText("");
        lastname.setText("");
        username.setText("");
        password.setText("");
        type.setText("");
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
