package controller;

import database.BillDBConnector;
import database.CustomerDBConnector;
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
import model.Bill;
import model.Customer;
import model.Employee;
import utilities.CheckInput;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class CustomerPaymentController {
    private Employee employee;
    private Customer customer;
    ObservableList<Bill> bills;
    @FXML private ImageView back;
    @FXML private TextField name;
    @FXML private Button newBill, search;
    @FXML private TableView<Bill> payments;
    @FXML private TableColumn date, billID, employeeID, status;
    @FXML private Label account;

    public void initialize() {
        bills = FXCollections.observableArrayList();

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) back.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
                try {
                    stage.setScene(new Scene(loader.load()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.show();
            }
        });

        billID.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("BillID"));
        employeeID.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("EmployeeID"));
        status.setCellValueFactory(new PropertyValueFactory<Bill, String>("Status"));
        date.setCellValueFactory(new PropertyValueFactory<Bill, LocalDate>("Date"));
        payments.setItems(bills);
        newBill.setDisable(true);
    }

    public void newBtnOnAction(ActionEvent event) {
        if (customer != null) {
            Stage stage = (Stage) newBill.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/createBill.fxml"));
            try {
                stage.setScene(new Scene(loader.load(), 750, 484));
                CreateBillController createBillController = loader.getController();
                createBillController.setEmployee(employee);
                createBillController.setCustomer(customer);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void searchBtnOnAction(ActionEvent event) {
        String[] tmp = name.getText().split(" ");
        if (CheckInput.isAllCharacter(tmp)) {
            if ((customer = CustomerDBConnector.searchCustomer(tmp[0], tmp[1])) != null) {
                newBill.setDisable(false);
                bills = BillDBConnector.getCustomerBill(customer.getId());
                for (Bill b : bills) {
                    b.setDetails(OrderDetailDBConnector.getOrderDetail(b.getBillID()));
                }
                payments.setItems(bills);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Washery Laundry");
                alert.setHeaderText("NOT FOUND THIS CUSTOMER");
                alert.setContentText("Do you want to register?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Stage stage = (Stage) search.getScene().getWindow();
                    this.createCustomer(stage);
                }
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Washery Laundry");
            alert.setHeaderText("Invalid information");
            alert.setContentText("Fill customer's name again");
            alert.showAndWait();
        }
    }

    private void createCustomer(Stage stage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/newCustomer.fxml"));
        try {
            stage.setScene(new Scene(loader.load()));
            NewCustomerController newCustomerController = loader.getController();
            newCustomerController.setEmployee(employee);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.setupAccountLabel();
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.setupAccountLabel();
    }

    private void setupAccountLabel() {
        if (employee != null) {
            account.setText(employee.getFirstName() + " " + employee.getLastName());
        }
        if (customer != null) {
            name.setText(customer.getFirstName() + " " + customer.getLastName());
        }
    }

}
