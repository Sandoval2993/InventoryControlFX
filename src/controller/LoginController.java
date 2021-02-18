package controller;

import connection.DatabaseConnection;
import dao.UserDAO;
import dto.ProductDTO;
import dto.UserDTO;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import model.User;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
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

    private UserDAO userDAO = new UserDAO();
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
           if (validateLogin()){
               closeLoginForm();
               createMenuForm();
           }
       }else{
           loginErrorLabel.setText("Usuario o contraseña inválidos.");
       }
    }

    public void cancelButtonOnAction(ActionEvent event){
        closeLoginForm();
    }

    public boolean validateLogin() {
        User user = new User();
        Map listUserMap = user.getUserMap();
        UserDTO userDTO = (UserDTO) listUserMap.get(usernameTextField.getText());
        if (userDTO!=null){
            if (userDTO.getPassword().equals(passwordField.getText())){
                loginErrorLabel.setText("Logeado correctamente.");
                return true;
            }
        }
        loginErrorLabel.setText("Usuario o contraseña inválidos.");
        return false;
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

    @FXML
    private void close(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
