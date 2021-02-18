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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button salesButton;
    @FXML
    private Button purchasingButton;
    @FXML
    private Button treasuryButton;
    @FXML
    private Button inventoriesButton;
    @FXML
    private Button thirdPartyButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button settingButton;
    @FXML
    private Button exitButton;
    @FXML
    private Pane paneTest;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void PurchasingButtonOnAction(ActionEvent event) {
        createPurchasingForm();

    }

    public void ProductListButtonOnAction(ActionEvent event) {
        createProductListForm();
    }

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void createPurchasingForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/Purchasing.fxml"));
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
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void createProductListForm() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/ProductList.fxml"));
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
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void loadFXML(ActionEvent event) throws IOException {
        paneTest.getChildren().removeAll();
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("../view/ProductList.fxml"));
        paneTest.getChildren().add(newLoadedPane);
    }

    public void loadFXML2(ActionEvent event) throws IOException {
        paneTest.getChildren().removeAll();
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("../view/ProductRegistration.fxml"));
        paneTest.getChildren().add(newLoadedPane);
    }

    @FXML
    public void min(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void max(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreen(true);
    }

    @FXML
    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
