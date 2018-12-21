package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bill {
    private int billID, employeeID, customerID;
    private ArrayList<OrderDetail> details;
    private String status;
    private LocalDate date;

    public void setDetails(ArrayList<OrderDetail> details) {
        this.details = details;
    }

    public Bill(int billID, int employeeID, int customerID, LocalDate date, String status) {
        this.billID = billID;
        this.employeeID = employeeID;
        this.customerID = customerID;
        this.details = null;
        this.date = date;
        this.status = status;
    }

    public int getBillID() {
        return billID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getStatus() {
        return status;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public ArrayList<OrderDetail> getDetails() {
        return details;
    }

    public LocalDate getDate() {
        return date;
    }
}
