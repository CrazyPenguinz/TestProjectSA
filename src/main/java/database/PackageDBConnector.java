package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Package;

import java.sql.*;
import java.time.LocalDate;

public class PackageDBConnector {
    private static String myDriver = "org.sqlite.JDBC";
    private static String urlDB = "jdbc:sqlite:Database.db";

    public static void newPackage(String date, int amount, double price) {
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "insert into Package (Expire, Amount, Price) values ('" + date + "' , '" + amount + "' , '" + price + "')";
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

    public static ObservableList getPackage() {
        ObservableList<Package> packages = FXCollections.observableArrayList();
        try {
            Class.forName(myDriver);
            Connection connection = DriverManager.getConnection(urlDB);
            if (connection != null) {
                String query = "Select * from Package";
                Statement statement =  connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    if (!LocalDate.parse(resultSet.getString("Expire")).isBefore(LocalDate.now())) {
                        int amount = resultSet.getInt("Amount");
                        LocalDate date = LocalDate.parse(resultSet.getString("Expire"));
                        double price = resultSet.getDouble("Price");
                        String name = resultSet.getString("Name");
                        packages.add(new Package(amount, date, price, name));
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }
}
