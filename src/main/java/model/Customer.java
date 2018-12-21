package model;

public class Customer {
    private int id, own, spend;
    private String firstName, lastName, address, phone;

    public Customer(int id, String firstName, String lastName, String address, String phone, int own, int spend) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.own = own;
        this.spend = spend;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getOwn() {
        return own;
    }

    public int getSpend() {
        return spend;
    }
}
