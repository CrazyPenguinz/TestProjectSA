package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Bill;

import java.sql.*;
import java.time.LocalDate;

public class BillDBConnector {
    private static String myDriver = "org.sqlite.JDBC";
    private static String urlDB = "jdbc:sqlite:Database.db";

    public static void addBill(int eid, String date, String status, int cid) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "insert into Bill (EmployeeID, Date, Status, CustomerID) values ('" + eid + "' , '" + date + "' , '" + status + "' , '" + cid + "')";
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

    public static int getBillID(int cid, String status) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select BillID from Bill where CustomerID = '" + cid + "' and Status = '" + status + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int bid = resultSet.getInt("BillID");
                    connection.close();
                    return bid;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static ObservableList getCustomerBill(int customerID) {
        ObservableList<Bill> bills = FXCollections.observableArrayList();
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select * from Bill where CustomerID = '" + customerID + "' Order by BillID DESC";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    if (!resultSet.getString("Status").equals("ชำระเงินเรียบร้อย")) {
                        int bid = resultSet.getInt("BillID");
                        int eid = resultSet.getInt("EmployeeID");
                        int cid = resultSet.getInt("CustomerID");
                        LocalDate date = LocalDate.parse(resultSet.getString("Date"));
                        String status = resultSet.getString("Status");
                        bills.add(new Bill(bid, eid, cid, date, status));
                    }
                }
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public static void updateBillStatus(int billID, String status) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Update Bill set Status = '" + status + "' where BillID = '" + billID + "'";
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

    public static boolean checkBillCreatedByEmployee(int employeeID) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select * from Bill where EmployeeID = '" + employeeID + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    return false;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
