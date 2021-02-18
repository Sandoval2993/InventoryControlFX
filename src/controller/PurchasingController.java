package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class PurchasingController implements Initializable {
    @FXML
    private Button exitButton;
    @FXML
    private Button createProductButton;
    @FXML
    private BorderPane purchasingBorderPane;

    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void exitButtonOnAction(ActionEvent event){
        Stage stage = (Stage)exitButton.getScene().getWindow();
        stage.close();
    }

    public void createProductButtonOnAction(ActionEvent event){
       createProductRegistrationForm();
    }

    private void createProductRegistrationForm(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/ProductRegistration.fxml"));
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

    @FXML
    private void min(ActionEvent event){
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void close(ActionEvent event){
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

}