package controller;

import connection.DatabaseConnection;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import javax.xml.crypto.Data;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private Connection cnn = null;
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginErrorLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("src/img/elephant.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("src/img/padlock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }

    public void loginButtonOnAction(ActionEvent event) throws SQLException {
       if (usernameTextField.getText().isBlank()==false && passwordField.getText().isBlank()==false){
           validateLogin();
       }else{
           loginErrorLabel.setText("Usuario o contraseña inválidos.");
       }
    }

    public void cancelButtonOnAction(ActionEvent event){
        closeLoginForm();
    }

    public void validateLogin() throws SQLException {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        cnn = instance.getCnn();
        String verifyLogin = "SELECT count(1) FROM user_account WHERE username='" + usernameTextField.getText() + "' AND password='" + passwordField.getText() + "'";
       try{
           Statement statement = cnn.createStatement();
           ResultSet queryResult = statement.executeQuery(verifyLogin);

           while (queryResult.next()){
               if (queryResult.getInt(1)==1){
//                   createAccountForm();
                   createMenuForm();
                   closeLoginForm();
//                   loginErrorLabel.setText("Felicitaciones");
               }else {
                   loginErrorLabel.setText("Login inválido, trata nuevamente");
               }
           }
       }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createAccountForm(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../view/register.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createMenuForm(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/menu.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root));
            stage.show();
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX()-xOffset);
                    stage.setY(event.getScreenY()-yOffset);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    private void closeLoginForm(){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

}
