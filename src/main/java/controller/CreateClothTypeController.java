package controller;

import database.ClothTypeDBConnector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Employee;

import java.io.IOException;

public class CreateClothTypeController {
    private Employee employee;
    @FXML private ImageView back;
    @FXML private TextField type;
    @FXML private Button create;
    @FXML private Label caution, account;

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
        caution.setTextFill(Paint.valueOf("RED"));
    }

    public void createBtnOnAction(ActionEvent event) {
        caution.setText("");
        String tmp = type.getText();
        if (!tmp.equals("")) {
            if (!ClothTypeDBConnector.checkDuplicate(tmp)) {
                ClothTypeDBConnector.addNewClothType(tmp);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Washery Laundry");
                alert.setHeaderText(null);
                alert.setContentText("Add Completed");
                alert.showAndWait();
            }
            else {
                caution.setText("This type already exist");
            }
        }
        else {
            caution.setText("Invalid information");
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
