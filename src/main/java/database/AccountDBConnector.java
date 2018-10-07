package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Account;

import java.sql.*;

public class AccountDBConnector {
    private static String myDriver = "org.sqlite.JDBC";
    private static String urlDB = "jdbc:sqlite:Database.db";

    public static ObservableList getAccounts() {
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
                    String username = resultSet.getString("Username");
                    String password = resultSet.getString("Password");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    accounts.add(new Account(type, username, password, firstName, lastName));
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

    public static void addAccount(String type, String username, String password, String firstName, String lastName) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "insert into Account (Type, Username, Password, FirstName, LastName) values ('" + type + "' , '" + username + "' , '" + password + "' , '" + firstName + "' , '" + lastName + "')";
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

    public static void deleteAccount(String username) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "delete from Account where Username == '" + username + "'";
                PreparedStatement p = connection.prepareStatement(query);
                p.executeUpdate();
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
