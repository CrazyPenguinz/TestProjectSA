package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccountDBConnector {

    private AccountDBConnector() {

    }

    public static AccountDBConnector getInstance() {
        return new AccountDBConnector();
    }

    public Connection getConnection() {
        String connect_string = "jdbc:sqlite:data.db";

        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(connect_string);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

//    AccountDB information = new AccountDB();
//
//    public boolean login(String username, String password) {
//        if (information.getAccount(username).getPassword().equals(password)) return true;
//        return false;
//    }
//
//    public void add(String username, String name, String password) {
//        information.add(username, name, password);
//    }
}
