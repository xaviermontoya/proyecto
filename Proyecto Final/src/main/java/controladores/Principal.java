package controladores;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import modelo.ClienteEntity;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

public class Principal implements Initializable{

    /*Atributos*/
    @FXML
    private JFXButton search;

    @FXML
    private TextField searchInput;

    @FXML
    private VBox rootMain;

    @FXML
    private Label nombreCliente;

    @FXML
    private Label apellidoPaternoCliente;

    @FXML
    private Label apellidoMaternoCliente;

    @FXML
    private Label fechaNacimientoCliente;

    @FXML
    private Label emailCliente;

    @FXML
    private Label curpCliente;

    @FXML
    private Label rfcCliente;

    @FXML
    private TextArea comentariosCliente;

    @FXML
    private JFXTreeTableView<SolicitudForTable> tableSolicitud;

    @FXML
    private JFXTreeTableColumn<SolicitudForTable, String> provedorCliente;

    @FXML
    private JFXTreeTableColumn<SolicitudForTable, String> tipoRegistroCliente;

    @FXML
    private JFXTreeTableColumn<SolicitudForTable, Integer> montoCliente;

    @FXML
    private JFXTreeTableColumn<SolicitudForTable, Integer> plazoCliente;

    @FXML
    private JFXButton guardarComentario;

    @FXML
    private StackPane dialogAlert;

    public static EntityManager manager;
    private List<ClienteEntity> clientes;
    private ClienteEntity clienteEncontrado;
    private String criterioBusqueda;

    /*Métodos que te permiten interactuar entre ventanas*/
    @FXML
    void toProvedorWindow(ActionEvent event) throws Exception{
        VBox pane = FXMLLoader.load(getClass().getResource("/vistas/provedoresfxml.fxml"));
        rootMain.getChildren().setAll(pane);
        event.consume();
    }

    @FXML
    public void toRegisterWindow(ActionEvent eventAction) throws Exception{
        VBox pane = FXMLLoader.load(getClass().getResource("/vistas/registerfxml.fxml"));
        rootMain.getChildren().setAll(pane);
        eventAction.consume();
    }

    @FXML
    public void toViewAllWindow(ActionEvent event) throws Exception{
        VBox pane = FXMLLoader.load(getClass().getResource("/vistas/viewAllfxml.fxml"));
        rootMain.getChildren().setAll(pane);
        event.consume();
    }

    /*Método que se ejecuta al cargarse la ventana*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obtenerClientes();
        /*Asignacion de evento al boton "search"*/
        search.setOnAction(e -> {
            criterioBusqueda = searchInput.getText();
            encontrarCliente(criterioBusqueda);
            nombreCliente.setText(clienteEncontrado.getNombre());
            apellidoPaternoCliente.setText(clienteEncontrado.getApellidoPaterno());
            apellidoMaternoCliente.setText(clienteEncontrado.getApellidoMaterno());
            fechaNacimientoCliente.setText(clienteEncontrado.getFechaNacimiento());
            emailCliente.setText(clienteEncontrado.getEmail());
            curpCliente.setText(clienteEncontrado.getCurp());
            rfcCliente.setText(clienteEncontrado.getRfc());
            comentariosCliente.setText(clienteEncontrado.getComentario());
            setupReadOnlyTableView();
            searchInput.setText("");
        });
        /*Asignación de evento a boton "guardar"*/
        guardarComentario.setOnAction(e -> guardarComentarioCliente(comentariosCliente.getText()));
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<SolicitudForTable, T> column, Function<SolicitudForTable, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<SolicitudForTable, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    /*Colocar la tabla para mostrar los datos del prestamo del cliente encontrado*/
    private void setupReadOnlyTableView(){
        setupCellValueFactory(provedorCliente, p -> p.provedorForTable);
        setupCellValueFactory(tipoRegistroCliente, p -> p.tipoRegistroForTable);
        setupCellValueFactory(montoCliente,p -> p.montoForTable.asObject());
        setupCellValueFactory(plazoCliente, p -> p.plazoForTable.asObject());

        final TreeItem<SolicitudForTable> root = new RecursiveTreeItem<>(solicitudAObservable(), RecursiveTreeObject::getChildren);
        tableSolicitud.setRoot(root);
        tableSolicitud.setShowRoot(false);
    }

    /*Prepara los datos para introducirlos a la tabla*/
    private ObservableList<SolicitudForTable> solicitudAObservable(){
        ObservableList<SolicitudForTable> aux = FXCollections.observableArrayList();

        aux.add(new SolicitudForTable(clienteEncontrado.getProvedor(), clienteEncontrado.getTipoRegistro(),
                Integer.parseInt(clienteEncontrado.getMonto()), Integer.parseInt(clienteEncontrado.getPlazo())));

        return aux;
    }

    /*Hace una petición a la base de datos*/
    private void obtenerClientes(){
        clientes = (List<ClienteEntity>) manager.createQuery("FROM ClienteEntity").getResultList();
    }

    /*Encuentra el cliente buscado desde la coleccion que se hace en la petición a la base de datos*/
    private void encontrarCliente(String criterio){
        Boolean encontrado = false;
        for (ClienteEntity c: clientes) {
            if (c.getCurp().equals(criterio) || c.getTelefono().equals(criterio) || c.getEmail().equals(criterio)){
                clienteEncontrado = c;
                encontrado = true;
                break;
            }
        }

        if (encontrado) panelCliente(new Text("Cliente encontrado."));
        else panelCliente(new Text("Cliente no encontrado."));
    }

    /*Guarda el comentario del cliente al hacer click en boton guardar*/
    private void guardarComentarioCliente(String comentario){
        manager.getTransaction().begin();
        ClienteEntity c = manager.find(ClienteEntity.class, criterioBusqueda);
        c.setComentario(comentario);
        manager.getTransaction().commit();
    }

    /*Muestra ventana emergente de aviso*/
    private void panelCliente(Text t){
        JFXDialogLayout content = new JFXDialogLayout();
        Text titulo = new Text("Busqueda de cliente");
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

    /*Clase interna que sirve de molde para la construccion de la tabla*/
    static final class SolicitudForTable extends RecursiveTreeObject<SolicitudForTable>{
        final SimpleStringProperty provedorForTable;
        final SimpleStringProperty tipoRegistroForTable;
        final SimpleIntegerProperty montoForTable;
        final SimpleIntegerProperty plazoForTable;

        SolicitudForTable(String provedorForTable, String tipoRegistroForTable, Integer montoForTable, Integer plazoForTable) {
            this.provedorForTable = new SimpleStringProperty(provedorForTable);
            this.tipoRegistroForTable = new SimpleStringProperty(tipoRegistroForTable);
            this.montoForTable = new SimpleIntegerProperty(montoForTable);
            this.plazoForTable = new SimpleIntegerProperty(plazoForTable);
        }
    }
}
