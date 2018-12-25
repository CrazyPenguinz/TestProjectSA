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
import model.*;

import java.io.IOException;
import java.util.ArrayList;

public class BillViewController {
    private Employee employee;
    private Customer customer;
    private Bill bill;
    private int ttl;
    private ArrayList<ClothType> types = new ArrayList<>();
    @FXML private Button save, edit, pay;
    @FXML private ImageView back;
    @FXML private Label total, account, description;
    @FXML private ChoiceBox<String> status;
    @FXML private TableView<OrderDetail> detailTableView;
    @FXML private TableView<ClothType> coupons;
    @FXML private TableColumn price, type, qty;
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

        price.setCellValueFactory(new PropertyValueFactory<ClothType, Integer>("Coupon"));
        type.setCellValueFactory(new PropertyValueFactory<OrderDetail, String>("Type"));
        qty.setCellValueFactory(new PropertyValueFactory<OrderDetail, Integer>("Quantity"));

        status.getItems().addAll("กำลังซัก", "รอรับกลับ");
    }

    public void saveBtnOnAction(ActionEvent event) {
        if (status.getValue() != null) {
            bill.setStatus(status.getValue());
            BillDBConnector.updateBillStatus(bill.getBillID(), status.getValue());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText(null);
            alert.setContentText("Save completed");
            alert.showAndWait();
            setupLabel();
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
            alert.setContentText("Paid Completed");
            alert.showAndWait();
            BillDBConnector.updateBillStatus(bill.getBillID(), "ชำระเงินเรียบร้อย");
            bill.setStatus("ชำระเงินเรียบร้อย");
            customer = CustomerDBConnector.getCustomer(customer.getId());
            setupLabel();
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

            status.setValue(bill.getStatus());
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
                types.add(ClothTypeDBConnector.getType(d.getType()));
            }
            total.setText(String.valueOf("Total : " + ttl));
            detailTableView.getItems().clear();
            detailTableView.getItems().addAll(bill.getDetails());
            coupons.getItems().clear();
            coupons.getItems().addAll(types);
            if (!bill.getStatus().equals("รอรับกลับ"))
                pay.setDisable(true);
            else if (bill.getStatus().equals("รอรับกลับ"))
                pay.setDisable(false);
            if (bill.getStatus().equals("ชำระเงินเรียบร้อย")) {
                edit.setDisable(true);
                pay.setDisable(true);
            }
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
