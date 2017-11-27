package controladores;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Entrada {
    @FXML
    private JFXTextField name;
    @FXML
    private JFXPasswordField password;

    /*Evento de boton "entrar", si los datos son correctos,
    * entraras a la aplicación*/
    @FXML
    private void action(ActionEvent event) throws Exception{
        if (!name.getText().isEmpty() && !password.getText().isEmpty()
                || name.getText().equals("Javier Montoya") || name.getText().equals("Alejandro Hernández")){
            Parent mainWindow = FXMLLoader.load(getClass().getResource("/vistas/mainfxml.fxml"));
            Scene mainScene = new Scene(mainWindow);
            Stage stage = new Stage();
            ((Node)event.getSource()).getScene().getWindow().hide();
            stage.setScene(mainScene);
            stage.getIcons().addAll(new Image("/image/IconVivus.png"));
            stage.setResizable(false);
            stage.setTitle("Main");
            stage.show();
        }
    }
}
