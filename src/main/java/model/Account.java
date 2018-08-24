package model;

public class Account {
    private String type;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public Account(String type, String username, String password, String firstName, String lastName) {
        this.type = type;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
