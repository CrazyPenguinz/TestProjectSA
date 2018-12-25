package controller;

import database.ClothTypeDBConnector;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ClothType;
import model.Employee;
import utilities.CheckInput;

import java.io.IOException;
import java.util.ArrayList;

public class DefineCouponController {
    private Employee employee;
    private ArrayList<ClothType> types;
    @FXML private ImageView back;
    @FXML private ChoiceBox<String> clothTypes;
    @FXML private Button submit;
    @FXML private TextField coupons;
    @FXML private Label caution, account;

    public void initialize(){
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

        coupons.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (clothTypes.getValue() != null) {
                    String tmp = clothTypes.getValue();
                    for (ClothType t : types) {
                        if (t.getType().equals(tmp)) {
                            coupons.setText(String.valueOf(t.getCoupon()));
                            break;
                        }
                    }
                }
            }
        });

        types = ClothTypeDBConnector.getType();
        for (ClothType t : types) {
            clothTypes.getItems().add(t.getType());
        }
    }

    @FXML
    public void submitBtnOnAction(){
        if (clothTypes.getValue() != null) {
            if (coupons.getText() != "" || CheckInput.isInteger(coupons)) {
                int coupon = Integer.parseInt(coupons.getText());
                if (coupon >= 3 && coupon <= 10) {
                    if (coupon > 0) {
                        ClothTypeDBConnector.updateCouponPerType(clothTypes.getValue(), coupon);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Washery Laundry");
                        alert.setHeaderText(null);
                        alert.setContentText("Update Completed");
                        alert.showAndWait();
                        coupons.setText(null);
                        caution.setText(null);
                    } else {
                        caution.setText("Not enough coupons");
                    }
                }
                else {
                    caution.setText(coupon < 3? "Too cheap" : "Too expensive");
                }
            } else {
                caution.setText(coupons.getText() != ""? "Please fill coupons in the blank" : "Coupon cannot floating point");
            }
        }
        else {
            caution.setText("Please select type first");
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
