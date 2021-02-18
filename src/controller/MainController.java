package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Circle userCircle;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        userCircle.setStroke(Color.SEAGREEN);
        File userImageFile = new File("src/img/astronaut.jpg");
        Image image = new Image(userImageFile.toURI().toString(),false);
        userCircle.setFill(new ImagePattern(image));
//        userCircle.setEffect(new DropShadow(+25d,0d,+2d,Color.DARKGREEN));

    }
}
