package controller;

import database.BillDBConnector;
import database.ClothTypeDBConnector;
import database.CustomerDBConnector;
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
import model.Bill;
import model.Customer;
import model.Employee;
import model.OrderDetail;

import java.io.IOException;

public class BillViewController {
    private Employee employee;
    private Customer customer;
    private Bill bill;
    private int ttl;
    @FXML private Button save, edit, pay;
    @FXML private ImageView back;
    @FXML private Label total, account, description;
    @FXML private ChoiceBox<String> status;
    @FXML private TableView<OrderDetail> detailTableView;
    @FXML private TableColumn cid, type, qty;
    public void initialize() {

        save.setDisable(true);
        save.setVisible(false);

        status.setDisable(true);
        status.setVisible(false);

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

        cid.setCellValueFactory(new PropertyValueFactory<OrderDetail, Integer>("ClothTypeID"));
        type.setCellValueFactory(new PropertyValueFactory<OrderDetail, String>("Type"));
        qty.setCellValueFactory(new PropertyValueFactory<OrderDetail, Integer>("Quantity"));

        status.getItems().addAll("กำลังซัก", "รอรับกลับ", "ชำระเงินเรียบร้อย");
    }

    public void saveBtnOnAction(ActionEvent event) {
        if (status.getValue() != null) {
            BillDBConnector.updateBillStatus(bill.getBillID(), status.getValue());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText(null);
            alert.setContentText("Save completed");
            alert.showAndWait();
        }
        save.setDisable(true);
        save.setVisible(false);

        status.setDisable(true);
        status.setVisible(false);
    }

    public void payBtnOnAction(ActionEvent event) {
        if (ttl <= customer.getOwn() - customer.getSpend()) {
            CustomerDBConnector.customerSpend(customer.getId(), customer.getSpend() + ttl);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText(null);
            alert.setContentText("Spend Completed");
            alert.showAndWait();
            BillDBConnector.updateBillStatus(bill.getBillID(), "ชำระเงินเรียบร้อย");
            customer = CustomerDBConnector.getCustomer(customer.getId());
        }

        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText("Coupon not enough");
            alert.setContentText("Should buy more coupon");
            alert.showAndWait();
        }
    }

    public void editBtnOnAction(ActionEvent event) {
        if (!bill.getStatus().equals("ชำระเงินเรียบร้อย")) {
            save.setDisable(false);
            save.setVisible(true);

            status.setDisable(false);
            status.setVisible(true);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText(null);
            alert.setContentText("This bill already paid");
            alert.showAndWait();
        }
    }

    private void setupLabel() {
        if (employee != null) {
            account.setText(employee.getFirstName() + " " + employee.getLastName());
        }
        if (customer != null) {
            description.setText("Name : " + customer.getFirstName() + " " + customer.getLastName() + "\nPhone : " + customer.getPhone() + "\nAddress : " + customer.getAddress() + "\nCoupon : " + customer.getSpend() + "/" + customer.getOwn());
        }
        if (bill != null) {
            ttl = 0;
            for (OrderDetail d : bill.getDetails()) {
                ttl += d.getQuantity() * ClothTypeDBConnector.getCouponPerType(d.getType());
            }
            total.setText(String.valueOf("Total : " + ttl));
            detailTableView.getItems().addAll(bill.getDetails());
            if (!bill.getStatus().equals("รอรับกลับ"))
                pay.setDisable(true);
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.setupLabel();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.setupLabel();
    }

    public void setBill(Bill bill) {
        this.bill = bill;
        this.setupLabel();
    }
}
