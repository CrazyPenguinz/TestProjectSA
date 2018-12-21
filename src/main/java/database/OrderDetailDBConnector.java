package database;

import javafx.collections.ObservableList;
import model.OrderDetail;

import java.sql.*;
import java.util.ArrayList;

public class OrderDetailDBConnector {
    private static String myDriver = "org.sqlite.JDBC";
    private static String urlDB = "jdbc:sqlite:Database.db";

    public static void addNewDetail(int billID, ObservableList<OrderDetail> details) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                for (OrderDetail d : details) {
                    String query = "insert into OrderDetail (BillID, ClothID, Type, Count) values ('" + billID + "' , '" + d.getClothTypeID() + "' , '" + d.getType() + "' , '" + d.getQuantity() + "')";
                    PreparedStatement p = connection.prepareStatement(query);
                    p.executeUpdate();
                }
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList getOrderDetail(int billID) {
        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select * from OrderDetail where BillID = '" + billID + "'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int clothID = resultSet.getInt("ClothID");
                    String type = resultSet.getString("Type");
                    int count = resultSet.getInt("Count");
                    orderDetails.add(new OrderDetail(clothID, type, count));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }
}
