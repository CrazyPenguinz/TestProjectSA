package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Employee;

import java.sql.*;

public class EmployeeDBConnector {
    private static String myDriver = "org.sqlite.JDBC";
    private static String urlDB = "jdbc:sqlite:Database.db";

    public static ObservableList getAccounts() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "select * from Employee";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String type = resultSet.getString("Type");
                    int id = resultSet.getInt("EmployeeID");
                    String username = resultSet.getString("Username");
                    String password = resultSet.getString("Password");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    employees.add(new Employee(type, id, username, password, firstName, lastName));
                }
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static boolean checkNameDuplicate(String fn, String ln) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select * from Employee where FirstName = '" + fn + "' and LastName = '" + ln + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    connection.close();
                    return true;
                }
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkUsernameDuplicate(String username) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select * from Employee where Username = '" + username + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    connection.close();
                    return true;
                }
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Employee isLogin(String username, String password) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query =  "select * from Employee where Username = '" + username + "' and Password = '" + password + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    String type = resultSet.getString("Type");
                    int id = resultSet.getInt("EmployeeID");
                    String userName = resultSet.getString("Username");
                    String passWord = resultSet.getString("Password");
                    String firstName = resultSet.getString("FirstName");
                    String lastName = resultSet.getString("LastName");
                    connection.close();
                    return new Employee(type, id, userName, passWord, firstName, lastName);
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
                String query = "insert into Employee (Type, Username, Password, FirstName, LastName) values ('" + type + "' , '" + username + "' , '" + password + "' , '" + firstName + "' , '" + lastName + "')";
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
                String query = "delete from Employee where Username == '" + username + "'";
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
