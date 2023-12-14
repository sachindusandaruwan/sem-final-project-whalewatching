package lk.ijse.whaleWatching.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.whaleWatching.AppInitializer;
import lk.ijse.whaleWatching.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class LoginPageFormController {
    @FXML
    void btnlogInAction(ActionEvent event) throws IOException, SQLException {
        System.out.println("button press");

        String UserName=txtUserName.getText();
        String Password=txtPassword.getText();

        try {

            boolean isCheck = UserModel.getUser(UserName, Password);
            if (isCheck) {

                AppInitializer.stageLogIn.close();

               // new Alert(Alert.AlertType.CONFIRMATION,"LOGIN SUCCESSFULLY").show();

                //open homepage

                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/homePageForm.fxml"));

                Scene scene = new Scene(rootNode);

                Stage stage = new Stage();
                stage.setTitle("home page");

                stage.setScene(scene);
                stage.show();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"INCORRECT USER NAME OR PASSWORD").show();
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    @FXML
    private AnchorPane root;


    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnSignOnAction(ActionEvent event) throws IOException {

        Parent rootNaode=FXMLLoader.load(this.getClass().getResource("/view/signupPageForm.fxml"));
        Scene scene=new Scene(rootNaode);
        Stage stage=new Stage();
        stage.setTitle("SignUp page");

        stage.setScene(scene);
        stage.show();

    }
}
