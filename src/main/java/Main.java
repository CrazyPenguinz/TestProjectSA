import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Washery Laundry");
        primaryStage.setScene(new Scene(root, 404, 550));
        primaryStage.show();

//        try {
//            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TestDB", "mote", "1");
//            if (myConn != null) {



//            }
//        } catch (Exception exc) {
//            exc.printStackTrace();
//        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
