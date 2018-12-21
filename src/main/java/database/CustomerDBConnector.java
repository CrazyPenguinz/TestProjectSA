package database;

import model.Customer;

import java.sql.*;

public class CustomerDBConnector {
    private static String myDriver = "org.sqlite.JDBC";
    private static String urlDB = "jdbc:sqlite:Database.db";
    
    public static Customer searchCustomer(String firstName, String lastname) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select * from Customer where FirstName = '" + firstName + "' and LastName = '" + lastname + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt("CustomerID");
                    String fn = resultSet.getString("FirstName");
                    String ln = resultSet.getString("LastName");
                    String addr = resultSet.getString("Address");
                    String phone = resultSet.getString("Phone");
                    connection.close();
                    return new Customer(id, fn, ln, addr, phone);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addNewCustomer(String fn, String ln, String phone, String addr) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "insert into Customer (FirstName, LastName, Phone, Address) values('" + fn + "' , '" + ln + "' , '" + phone + "' , '" + addr + "')";
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
