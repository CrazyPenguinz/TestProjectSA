package controller;

import database.EmployeeDBConnector;
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
import model.Employee;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML private Button button;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label caution;

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

    public void loginBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        this.checkUsernameAndPassword(stage);
    }

    private void checkUsernameAndPassword(Stage stage) throws IOException {
        boolean loginStatus = false;
        username.getText();
        password.getText();
        Employee employee = EmployeeDBConnector.isLogin(username.getText(),password.getText());
        if (employee != null) {
            loginStatus = true;
            if (employee.getType().equals("Staff"))
                this.loginWithStaff(stage, employee);
            else if (employee.getType().equals("Manager"))
                this.loginWithManager(stage, employee);
        }
        if (!loginStatus) {
            caution.setVisible(true);
            caution.setText("Username or Password is incorrect.");
        }
    }

    private void loginWithStaff(Stage stage, Employee employee) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/customerPayment.fxml"));
        stage.setScene(new Scene(loader.load(), 574, 455));
        CustomerPaymentController customerPaymentController = loader.getController();
        customerPaymentController.setEmployee(employee);
        caution.setVisible(false);
        stage.show();
    }

    private void loginWithManager(Stage stage, Employee employee) throws IOException {
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/home.fxml"));
        stage.setScene(new Scene(loader.load(), 574, 455));
        HomeController homeController = loader.getController();
        homeController.setEmployee(employee);
        caution.setVisible(false);
        stage.show();
    }
}
