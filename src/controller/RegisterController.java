package controller;

import connection.DatabaseConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import javax.xml.crypto.Data;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RegisterController implements Initializable{
    private Connection cnn = null;
    @FXML
    private ImageView shieldImageView;
    @FXML
    private Button closeButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField setPasswordTextfield;
    @FXML
    private PasswordField confirmPasswordTextfield;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField usernameTextField;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        File shieldFile = new File("src/img/shield.png");
        Image shieldImage = new Image(shieldFile.toURI().toString());
        shieldImageView.setImage(shieldImage);
    }

    public void registerButtonOnAction(ActionEvent event){
        if (setPasswordTextfield.getText().equals(confirmPasswordTextfield.getText())){
            registerUser();
            confirmPasswordLabel.setText("");
        } else {
            confirmPasswordLabel.setText("Las contraseñas no coinciden");
        }
    }

    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void registerUser(){
        DatabaseConnection instance = DatabaseConnection.getInstance();
        cnn = instance.getCnn();
        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordTextfield.getText();

        String insertFields = "INSERT INTO user_account(lastname,firstname,username,password) VALUES ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;

        try{
            Statement statement = cnn.createStatement();
            statement.executeUpdate(insertToRegister);

            registrationMessageLabel.setText("Usuario registrado exitosamente");
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
}
