package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Employee;

import java.io.IOException;

public class HomeController {
    private Employee employee;
    @FXML private Button button;
    @FXML private Label accountLabel;


    public void logoutBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 404, 550));
        if (employee != null) employee = null;
        stage.show();
    }

    public void employeeBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/staffView.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 600, 456));
        EmployeeViewController employeeViewController = fxmlLoader.getController();
        employeeViewController.setEmployee(employee);
        stage.show();
    }

    public void clothTypeBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/createClothType.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 530, 291));
        CreateClothTypeController createClothTypeController = fxmlLoader.getController();
        createClothTypeController.setEmployee(employee);
        stage.show();
    }

    public void packageBtnOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/createPackage.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 520, 291));
        CreatePackageController createPackageController = fxmlLoader.getController();
        createPackageController.setEmployee(employee);
        stage.show();
    }

    public void defineCouponOnAction(ActionEvent event) throws IOException {
        Button button = (Button) event.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/defineCouponPerClothType.fxml"));
        stage.setScene(new Scene(fxmlLoader.load(), 520, 291));
        DefineCouponController defineCouponController = fxmlLoader.getController();
        defineCouponController.setEmployee(employee);
    }

    private void setUpAccountLabel() {
        if (employee != null) {
            accountLabel.setText(employee.getType() + " : " + employee.getFirstName() + " " + employee.getLastName());
            accountLabel.disabledProperty();
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.setUpAccountLabel();
    }
}
