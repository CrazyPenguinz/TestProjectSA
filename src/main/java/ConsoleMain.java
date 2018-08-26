import java.sql.*;

public class ConsoleMain {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String myDriver = "org.sqlite.JDBC";
        String urlDB = "jdbc:sqlite:Database.db";

        Class.forName(myDriver);
        Connection connection = DriverManager.getConnection(urlDB);
        if (connection != null) {
            String query = "select * from Login where Username == '" + "mote" + "' and Password == '" + "1" + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
//            String type = resultSet.getString("Type");
//            String userName = resultSet.getString("Username");
//            String passWord = resultSet.getString("Password");
//            String firstName = resultSet.getString("FirstName");
//            String lastName = resultSet.getString("LastName");
            System.out.println(resultSet.getString("Type") + " " + resultSet.getString("Username") + " "
                    + resultSet.getString("Password") + " " + resultSet.getString("FirstName") + " " + resultSet.getString("LastName"));
            connection.close();
        }
    }
}
