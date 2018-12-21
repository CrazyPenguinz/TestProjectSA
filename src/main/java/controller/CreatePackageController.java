package controller;

import database.PackageDBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Employee;
import utilities.CheckInput;

import java.io.IOException;
import java.time.LocalDate;

public class CreatePackageController {
    private Employee employee;
    @FXML private Button create;
    @FXML private ImageView back;
    @FXML private TextField amount, price;
    @FXML private DatePicker expire;
    @FXML private Label cautionDate, cautionAmount, cautionPrice, account;

    public void initialize() {
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) back.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
                try {
                    stage.setScene(new Scene(loader.load()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HomeController homeController = loader.getController();
                homeController.setEmployee(employee);
                stage.show();
            }
        });

        amount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cautionAmount.setText("");
            }
        });

        price.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cautionPrice.setText("");
            }
        });

        expire.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cautionDate.setText("");
            }
        });

        cautionDate.setTextFill(Paint.valueOf("RED"));
        cautionPrice.setTextFill(Paint.valueOf("RED"));
        cautionAmount.setTextFill(Paint.valueOf("RED"));
    }

    public void createBtnOnAction(ActionEvent event) {
        LocalDate tmpExpire = expire.getValue();
        String tmpAmount = amount.getText();
        String tmpPrice = price.getText();
        boolean allCorrect = true;
        if (!CheckInput.isAllNumber(amount)) {
            cautionAmount.setText("Wrong amount");
            allCorrect = false;
        }
        if (!CheckInput.isAllNumber(price)) {
            cautionPrice.setText("Wrong price");
            allCorrect = false;
        }
        if (tmpExpire == null || tmpExpire.isBefore(LocalDate.now())) {
            cautionDate.setText("Wrong date");
            allCorrect = false;
        }

        if (allCorrect) {
            PackageDBConnector.newPackage(String.valueOf(tmpExpire), Integer.valueOf(tmpAmount), Double.parseDouble(tmpPrice));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText(null);
            alert.setContentText("Add Completed");
            alert.showAndWait();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText("Please fill every field with correctly information");
            alert.setContentText("Fill information again");
            alert.showAndWait();
        }

    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.setupLabel();
    }

    private void setupLabel() {
        if (employee != null) {
            account.setText(employee.getFirstName() + " " + employee.getLastName());
        }
    }
}
