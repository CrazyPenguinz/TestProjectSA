package controller;

import model.Employee;

public class CustomerPaymentController {
    private Employee employee;

    public void setEmployee(Employee employee) {
        this.employee = employee;
        this.setupAccountLabel();
    }

    private void setupAccountLabel() {

    }
}
