package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;

import java.sql.*;

public class AccountDBConnector {
    private static String urlDB = "jdbc:mysql://localhost:3306/TestDB";
    private static String user = "mote";
    private static String pass = "1";

    public static ObservableList getAccounts() throws SQLException {
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        try {
            Connection connection = DriverManager.getConnection(urlDB, user, pass);
            if (connection != null) {
                String query = "select * from Account";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String type = resultSet.getString("Type");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    String username = resultSet.getString("Username");
                    String password = resultSet.getString("Password");
                    accounts.add(new Account(type, firstName, lastName, username, password));
                }
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return accounts;
    }

    public static Account isLogin(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(urlDB, user, pass);
            if (connection != null) {
                String query =  "select * from Account where Username='" + username + "' and Password='" + password + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                String type = resultSet.getString("Type");
                String userName = resultSet.getString("Username");
                String passWord = resultSet.getString("Password");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                return new Account(type, userName, passWord, firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
