package view;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

import java.util.Optional;

public class AlertBox {
    Alert alert = new Alert(null);

    public AlertBox() {
        alert.initStyle(StageStyle.TRANSPARENT);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("values/style.css");
        dialogPane.getStyleClass().add("myDialog");
        dialogPane.getStyleClass().add("borderPane");
    }

    public Alert getAlertCreate(String gloss){
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Registro");
        alert.setHeaderText("Registro exitoso");
        alert.setContentText("Se realizó el registro de " + gloss);
        return alert;
    }
    public Alert getAlertDelete(String gloss) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Eliminación");
        alert.setHeaderText("Eliminación en curso.");
        alert.setContentText("Confirma que desea eliminar el item " + gloss);
        return alert;
    }
    
    public Alert getAlertInformationDelete(){
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Eliminación");
        alert.setHeaderText("Eliminación exitosa.");
        alert.setContentText(null);
        return alert;
    }

    public Alert getAlertError(){
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Error");
        alert.setHeaderText("Operación fallida.");
        alert.setContentText("No se pudo realizar operación");
        return alert;
    }

    public Alert getAlertUpdate(){
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Actualización");
        alert.setHeaderText("Actualización exitosa");
        alert.setContentText("Los datos fueron acualizados satisfactoriamente.");
        return alert;
    }

}

