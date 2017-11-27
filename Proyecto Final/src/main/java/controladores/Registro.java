package controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import modelo.ClienteEntity;
import modelo.elementos.Proveedor;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Registro implements Initializable{
    /*Atributos*/
    @FXML
    public StackPane dialogAlert;

    @FXML
    private VBox rootRegister;

    @FXML
    private JFXTextField nombre;

    @FXML
    private JFXTextField apellidoPaterno;

    @FXML
    private JFXTextField apellidoMaterno;

    @FXML
    private JFXTextField fechaNacimiento;

    @FXML
    private JFXTextField curp;

    @FXML
    private JFXTextField rfc;

    @FXML
    private JFXTextField correoElectronico;

    @FXML
    private JFXTextField telefono;

    @FXML
    private JFXButton registrar;

    @FXML
    private JFXButton cancelar;

    @FXML
    private ComboBox<String> provedor;

    @FXML
    private ComboBox<String> monto;

    @FXML
    private ComboBox<String> plazo;

    @FXML
    private ComboBox<String> razonRegistro;

    public static EntityManager manager;

    /*Métodos que interactuan con las demas ventanas*/
    @FXML
    void toProvedorWindow(ActionEvent event) throws Exception{
        VBox paneMain = FXMLLoader.load(getClass().getResource("/vistas/provedoresfxml.fxml"));
        rootRegister.getChildren().setAll(paneMain);
        event.consume();
    }

    public void toSearchWindow(ActionEvent eventAction) throws Exception{
        VBox paneMain = FXMLLoader.load(getClass().getResource("/vistas/mainfxml.fxml"));
        rootRegister.getChildren().setAll(paneMain);
        eventAction.consume();
    }

    public void toviewAllWindow(ActionEvent event) throws Exception{
        VBox paneMain = FXMLLoader.load(getClass().getResource("/vistas/viewAllfxml.fxml"));
        rootRegister.getChildren().setAll(paneMain);
        event.consume();
    }

    /*Metodo de inicialización de ventana*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colocarPlazoYMonto();
        colocarTipoRegistro();
        colocarProvedores();

        cancelar.setOnAction(event -> {
            VBox paneMain = null;
            try {
                paneMain = FXMLLoader.load(getClass().getResource("/vistas/registerfxml.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            rootRegister.getChildren().setAll(paneMain);
        });

        registrar.setOnAction(event -> {
            registrarCliente();
            panelCliente(new Text("Cliente registrado correctamente."));
        });
    }

    /*Método que guarda el cliente en la base de datos*/
    private void registrarCliente(){
        ClienteEntity c = new ClienteEntity(nombre.getText(), apellidoPaterno.getText(), apellidoMaterno.getText(),
                fechaNacimiento.getText(), curp.getText(), rfc.getText(), correoElectronico.getText(), telefono.getText(),
                provedor.getValue(), monto.getValue(), plazo.getValue(), razonRegistro.getValue(), "");

        manager.getTransaction().begin();
        manager.persist(c);
        manager.getTransaction().commit();

    }

    /*Método que muestra ventana emergente*/
    private void panelCliente(Text t){
        JFXDialogLayout content = new JFXDialogLayout();
        Text titulo = new Text("Registro de Cliente.");
        Font fuente = new Font("Arial Bold", 15);
        titulo.setFont(fuente);
        content.setHeading(titulo);
        content.setBody(t);
        JFXDialog dialog = new JFXDialog(dialogAlert, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Hecho");
        button.setOnAction(event1 -> dialog.close());
        content.setActions(button);
        dialog.show();
    }

    /*Coloca los plazos y montos de prestamos*/
    private void colocarPlazoYMonto(){
        monto.getItems().addAll("1000","1100","1200","1300","1400","1500",
                "1600","1700","1900","2000","2100","2200","2300","2400","2500","3000");
        plazo.getItems().addAll("7","8","9","10","11","12","13","14","15","16","17","18",
                "19","20","21","22","23","24","25","26","27","28","29","30");
    }

    /*Coloca los proveedores*/
    private void colocarProvedores(){
        Proveedor[] proveedores = Proveedor.getProveedores();

        for (Proveedor p: proveedores) {
            provedor.getItems().add(p.getNombreEmpresa());
        }
    }

    /*Coloca el tipo de registro*/
    private void colocarTipoRegistro(){
        razonRegistro.getItems().addAll("Vía Telefónica", "Vía Internet");
    }
}

