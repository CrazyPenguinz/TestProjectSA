package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;

import java.sql.*;

public class AccountDBConnector {
    private static String myDriver = "org.sqlite.JDBC";
    private static String urlDB = "jdbc:sqlite:Database.db";

    public static ObservableList getAccounts() throws SQLException {
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public static Account isLogin(String username, String password) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query =  "select * from Account where Username = '" + username + "' and Password = '" + password + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    String type = resultSet.getString("Type");
                    String userName = resultSet.getString("Username");
                    String passWord = resultSet.getString("Password");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    connection.close();
                    return new Account(type, userName, passWord, firstName, lastName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveAccount(String type, String username,String password, String firstname,String lastname) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "insert into Login (Type, Username, Password, Firstname, Lastname) values ('" + type + "' , '" + username + "' , '" + password + "' , '" + firstname + "' , '" + lastname + "')";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
            }
            connection.close();
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
