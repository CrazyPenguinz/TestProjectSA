package controller;

import database.CustomerDBConnector;
import database.PackageDBConnector;
import javafx.collections.ObservableList;
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
import model.Package;

import java.io.IOException;
import java.util.Optional;

public class SalePackageController {
    private Employee employee;
    private Customer customer;
    ObservableList<Package> packageObservableList;
    @FXML private ImageView back;
    @FXML private ChoiceBox<String> packages;
    @FXML private Button button;
    @FXML private Label account;

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
                customerPaymentController.setCustomer(customer);
                stage.show();
            }
        });

        packageObservableList = PackageDBConnector.getPackage();
        for (Package p : packageObservableList) {
            packages.getItems().add(p.getName());
        }
    }

    public void saleBtnOnAction(ActionEvent event) {
        if (packages.getValue() != null) {
            for (Package p : packageObservableList) {
                if (packages.getValue().equals(p.getName())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Washery Laundry");
                    alert.setHeaderText("Purchasing package");
                    alert.setContentText("This package is " + p.getPrice() + " Baht\nAre you sure to confirm to buy this package?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        CustomerDBConnector.buyPackage(customer.getId(), customer.getOwn() + p.getAmount());
                        customer = CustomerDBConnector.getCustomer(customer.getId());
                    }
                }
            }
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private void setupLabel() {
        if (employee != null) {
            account.setText(employee.getFirstName() + " " + employee.getLastName());
        }
    }
}
