package controller;

public class HomeController {
    String selectedAccount;

    public void loginWith(String username) {
        selectedAccount = username;
    }
}
