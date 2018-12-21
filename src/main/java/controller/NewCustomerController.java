package controller;

import database.CustomerDBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Customer;
import model.Employee;
import utilities.CheckInput;

import java.io.IOException;

public class NewCustomerController {
    private Employee employee;
    private Customer customer;
    @FXML private TextField firstName, lastName, phone;
    @FXML private TextArea address;
    @FXML private Button create;
    @FXML private Label caution, account;
    @FXML private ImageView back;

    public void initialize() {
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) back.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/customerPayment.fxml"));
                try {
                    stage.setScene(new Scene(loader.load()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                CustomerPaymentController customerPaymentController = loader.getController();
                customerPaymentController.setEmployee(employee);
                if (customer != null) {
                    customerPaymentController.setCustomer(customer);
                }
                stage.show();
            }
        });

    }

    public void createBtnOnAction(ActionEvent event) {
        String fn = firstName.getText();
        String ln = lastName.getText();
        String ph = phone.getText();
        String addr = address.getText();
        String warning = "";
        if (fn.equals("")) warning += "First name field cannot blank\n";
        else if (!CheckInput.isAllCharacter(firstName)) warning += "First name allow only character\n";
        if (ln.equals("")) warning += "Last name field cannot blank\n";
        else if (!CheckInput.isAllCharacter(lastName)) warning += "Last name allow only character\n";
        if (ph.equals("")) warning += "Phone field cannot blank\n";
        else if (!CheckInput.isAllNumber(phone)) warning += "Phone allow only digit (0-9)\n";

        if (warning.equals("")) {
            CustomerDBConnector.addNewCustomer(fn, ln, ph, addr);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText(null);
            alert.setContentText("Add Completed");
            alert.showAndWait();
            customer = CustomerDBConnector.searchCustomer(fn, ln);
        }
        else {
            caution.setText(warning);
        }
    }

    private void setupLabel() {
        if (employee != null) {
            account.setText(employee.getFirstName() + " " + employee.getLastName());
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.setupLabel();
    }
}
