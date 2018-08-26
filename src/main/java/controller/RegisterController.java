package controller;

import database.AccountDBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

//import utilities.CheckInput;

public class RegisterController {
    @FXML
    private Button button;
    @FXML
    private TextField userName,firstNameInput,lastNameInput,type;
    @FXML
    private PasswordField passwordID;
    @FXML
    protected void handleRegisBtnAction(ActionEvent e) throws IOException, SQLException {
        Alert ConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to Create Account "
                + firstNameInput.getText() + " " + lastNameInput.getText() + " ?", ButtonType.OK, ButtonType.CANCEL);
        ConfirmationAlert.setHeaderText("");
        Optional optional = ConfirmationAlert.showAndWait();
        if(optional.get() == ButtonType.OK)
        {
//            List<Boolean> checkBoolean = new ArrayList<>();
//            checkBoolean.add(CheckInput.isAllCharacter(firstNameInput));
//            checkBoolean.add(CheckInput.isAllCharacter(lastNameInput));
//            List<String> checkTextField = new ArrayList<>();
//            checkTextField.add(firstNameInput.getText());
//            checkTextField.add(lastNameInput.getText());
//            if(CheckInput.isAllCorrectEmpty(checkTextField) && CheckInput.isAllCorrectType(checkBoolean) && CheckInput.isCorrectUsername(AccountDBConnector.getAccounts(),userName))
//            {
                AccountDBConnector.saveAccount(type.getText(),userName.getText(),passwordID.getText(),firstNameInput.getText(),lastNameInput.getText());
//                Alert informationAlert = new Alert(Alert.AlertType.INFORMATION,"Saved");
                firstNameInput.setText("");
                lastNameInput.setText("");
                userName.setText("");
                passwordID.setText("");
                type.setText("");
//            }
//            else
//            {
//                Alert errorAlert = new Alert(Alert.AlertType.ERROR,"Could not create account");
//                errorAlert.showAndWait();
//            }
        }
    }

//    @FXML
//    private void handleBackBtn(ActionEvent event) throws IOException {
//        Button buttonBack = (Button) event.getSource();
//        Stage stage = (Stage) buttonBack.getScene().getWindow();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
//        stage.setScene(new Scene(fxmlLoader.load(), 541, 400));
//
//    }
}
