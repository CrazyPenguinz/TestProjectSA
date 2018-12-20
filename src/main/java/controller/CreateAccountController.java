package controller;

import database.EmployeeDBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Employee;
import utilities.CheckInput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CreateAccountController {
    private Employee employee;
    @FXML
    private Button backButton, registerButton;
    @FXML
    private TextField username, firstName, lastName;
    @FXML
    private PasswordField password;
    @FXML
    private ChoiceBox<String> roleBox = new ChoiceBox<>();

    public void initialize() {
        roleBox.getItems().addAll("Staff", "Manager");
        roleBox.setValue("Staff");
    }

    public void confirmBtnOnAction(ActionEvent event) throws IOException {
        List<Boolean> checkBoolean = new ArrayList<>();
        checkBoolean.add(CheckInput.isAllCharacter(firstName));
        checkBoolean.add(CheckInput.isAllCharacter(lastName));
        List<String> checkTextField = new ArrayList<>();
        checkTextField.add(firstName.getText());
        checkTextField.add(lastName.getText());
        if(CheckInput.isAllCorrectEmpty(checkTextField) && CheckInput.isAllCorrectType(checkBoolean) && CheckInput.isCorrectUsername(EmployeeDBConnector.getAccounts(),username)) {
            EmployeeDBConnector.addAccount(roleBox.getValue(), username.getText(), password.getText(), firstName.getText(), lastName.getText());
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            this.resetField();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText(null);
            alert.setContentText("Add Completed");
            alert.showAndWait();
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
        EmployeeViewController employeeViewController = fxmlLoader.getController();
        employeeViewController.setEmployee(employee);
        stage.show();
    }

    private void resetField() {
        firstName.setText("");
        lastName.setText("");
        username.setText("");
        password.setText("");
        roleBox.setValue("");
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
