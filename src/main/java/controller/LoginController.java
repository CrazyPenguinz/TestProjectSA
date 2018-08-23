package controller;

import database.AccountDBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML protected Button button;
    @FXML protected TextField username;
    @FXML protected PasswordField password;

//    public void initialize() {
//        AccountDBConnector.getInstance().add("nice", "nice", "1");
//    }
    @FXML
    public void handleLoginBtn(ActionEvent event) throws IOException, SQLException {

        Connection connection = AccountDBConnector.getInstance().getConnection();

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from Test where username" +
                " = '" + username.getText() + "' or email = '" + password + "' and password  = '" + password.getText() + "'");

        if (resultSet.next()) {
            HomeController homeController = new HomeController();
            homeController.loginWith(username.getText());
        }

//        if (accountDBConnector.login(username.getText(), password.getText())) {
//            HomeController homeController = new HomeController();
//            homeController.loginWith(username.getText());
//            Button b = (Button) event.getSource();
//            Stage stage = (Stage) b.getScene().getWindow();
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
//            try {
//                stage.setScene(new Scene(fxmlLoader.load(), 600, 400));
//                stage.show();
//            }catch (NullPointerException e) {
//                e.printStackTrace();
//            }
//        }
//        else {
//            password.clear();
//        }
    }
}
