package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
