package gui;

import javafx.scene.control.Alert;

import java.lang.reflect.Field;

public class Util {


    public static void showAlert(Alert.AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.show();

    }
}
