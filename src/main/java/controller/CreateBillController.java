package controller;

import database.BillDBConnector;
import database.ClothTypeDBConnector;
import database.OrderDetailDBConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.ClothType;
import model.Customer;
import model.Employee;
import model.OrderDetail;
import utilities.CheckInput;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CreateBillController {
    private Employee employee;
    private Customer customer;
    private ArrayList<ClothType> clothTypes;
    private ObservableList<OrderDetail> orderDetails;
    @FXML private ImageView back;
    @FXML private ChoiceBox<String> types;
    @FXML private TextField amount;
    @FXML private Button button;
    @FXML private Label account, coupon;
    @FXML private TableView<OrderDetail> details;
    @FXML private TableColumn clothID, type, qty;

    public void initialize() {
        orderDetails = FXCollections.observableArrayList();
        clothTypes = ClothTypeDBConnector.getType();
        for (ClothType c : clothTypes) {
            types.getItems().add(c.getType());
        }

        clothID.setCellValueFactory(new PropertyValueFactory<OrderDetail, Integer>("ClothTypeID"));
        type.setCellValueFactory(new PropertyValueFactory<OrderDetail, String>("Type"));
        qty.setCellValueFactory(new PropertyValueFactory<OrderDetail, Double>("Quantity"));
        details.setItems(orderDetails);

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

        amount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                amount.setText("");
            }
        });
    }

    public void addBtnOnAction(ActionEvent event) {
        if (!amount.getText().equals("") && CheckInput.isAllNumber(amount) && CheckInput.isInteger(amount) && Integer.parseInt(amount.getText()) > 0 && Integer.parseInt(amount.getText()) <= 100) {
            for (ClothType c : clothTypes) {
                if (types.getValue().equals(c.getType())) {
                    orderDetails.add(new OrderDetail(c.getId(), c.getType(), Integer.parseInt(amount.getText())));
                    details.setItems(orderDetails);
                    break;
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText("Invalid amount");
            alert.setContentText("Fill amount again");
            alert.showAndWait();
        }
    }

    public void saveBtnOnAction(ActionEvent event) {
        if (orderDetails.size() != 0) {
            BillDBConnector.addBill(employee.getId(), String.valueOf(LocalDate.now()), "กำลังรับ", customer.getId());
            int tmp = BillDBConnector.getBillID(customer.getId(), "กำลังรับ");
            OrderDetailDBConnector.addNewDetail(tmp, orderDetails);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText(null);
            alert.setContentText("Create complete");
            alert.showAndWait();
            BillDBConnector.updateBillStatus(tmp, "ตรวจสอบผ้า");
            Stage stage = (Stage) button.getScene().getWindow();
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
    }

    public void deleteBtnOnAction(ActionEvent event) {
        OrderDetail tmp = details.getSelectionModel().getSelectedItem();
        if (tmp != null) {
            orderDetails.remove(tmp);
            details.setItems(orderDetails);
        }
    }

    private void setupLabel() {
        if (employee != null) {
            account.setText(employee.getFirstName() + " " + employee.getLastName());
        }
        if (customer != null) {
            coupon.setText(customer.getFirstName() + " : have " + (customer.getOwn() - customer.getSpend()) + " coupon(s)");
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.setupLabel();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;

    }
}
