package database;

import model.Customer;

import java.sql.*;

public class CustomerDBConnector {
    private static String myDriver = "org.sqlite.JDBC";
    private static String urlDB = "jdbc:sqlite:Database.db";
    
    public static Customer searchCustomer(String firstName, String lastName) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select * from Customer where FirstName = '" + firstName + "' and LastName = '" + lastName + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt("CustomerID");
                    String fn = resultSet.getString("FirstName");
                    String ln = resultSet.getString("LastName");
                    String addr = resultSet.getString("Address");
                    String phone = resultSet.getString("Phone");
                    int own = resultSet.getInt("OwnCoupon");
                    int sp = resultSet.getInt("SpendCoupon");
                    connection.close();
                    return new Customer(id, fn, ln, addr, phone, own, sp);
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
                String query = "insert into Customer (FirstName, LastName, Phone, Address, OwnCoupon, SpendCoupon) values('" + fn + "' , '" + ln + "' , '" + phone + "' , '" + addr + "' , ' 0 ' , ' 0 ')";
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

    public static void customerSpend(int id, int spend) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Update Customer set SpendCoupon = '" + spend + "' where CustomerID = '" + id + "'";
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

    public static void buyPackage(int id, int own) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Update Customer set OwnCoupon = '" + own + "' where CustomerID = '" + id + "'";
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

    public static Customer getCustomer(int cid) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select * from Customer where CustomerID = '" + cid + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt("CustomerID");
                    String fn = resultSet.getString("FirstName");
                    String ln = resultSet.getString("LastName");
                    String addr = resultSet.getString("Address");
                    String phone = resultSet.getString("Phone");
                    int own = resultSet.getInt("OwnCoupon");
                    int sp = resultSet.getInt("SpendCoupon");
                    connection.close();
                    return new Customer(id, fn, ln, addr, phone, own, sp);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
