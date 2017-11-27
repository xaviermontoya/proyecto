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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
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

public class VerTodo implements Initializable{
    /*Atributos*/
    @FXML
    public VBox rootViewAll;

    @FXML
    private StackPane dialogAlert;

    @FXML
    private JFXTreeTableView<ClienteForTableView> clientesView;

    @FXML
    private JFXTreeTableColumn<ClienteForTableView, String> nombreClienteTable;

    @FXML
    private JFXTreeTableColumn<ClienteForTableView, String> fechaClienteTable;

    @FXML
    private JFXTreeTableColumn<ClienteForTableView, Integer> montoClienteTable;

    @FXML
    private JFXTreeTableColumn<ClienteForTableView, Integer> plazoClienteTable;

    @FXML
    private JFXTreeTableColumn<ClienteForTableView, String> provedorCliente;


    public static EntityManager manager;

    /*Métodos que interactuan con las demas ventanas*/
    @FXML
    void toProvedorWindow(ActionEvent event) throws Exception{
        VBox pane = FXMLLoader.load(getClass().getResource("/vistas/provedoresfxml.fxml"));
        rootViewAll.getChildren().setAll(pane);
        event.consume();
    }

    public void toSearch(ActionEvent event) throws Exception{
        VBox pane = FXMLLoader.load(getClass().getResource("/vistas/mainfxml.fxml"));
        rootViewAll.getChildren().setAll(pane);
        event.consume();
    }

    public void toRegister(ActionEvent event) throws Exception{
        VBox pane = FXMLLoader.load(getClass().getResource("/vistas/registerfxml.fxml"));
        rootViewAll.getChildren().setAll(pane);
        event.consume();
    }

    /*Método de incialización*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupReadOnlyTableView();
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ClienteForTableView, T> column, Function<ClienteForTableView, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClienteForTableView, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    /*Colocar datos en tabla*/
    private void setupReadOnlyTableView(){
        setupCellValueFactory(nombreClienteTable, p -> p.nombreCliente);
        setupCellValueFactory(fechaClienteTable, p -> p.fechaCliente);
        setupCellValueFactory(montoClienteTable, p -> p.montoCliente.asObject());
        setupCellValueFactory(plazoClienteTable, p -> p.plazoCliente.asObject());
        setupCellValueFactory(provedorCliente, p -> p.provedorCliente);

        final TreeItem<ClienteForTableView> root = new RecursiveTreeItem<>(aObservable(), RecursiveTreeObject::getChildren);
        clientesView.setRoot(root);
        clientesView.setShowRoot(false);
    }

    private ObservableList<ClienteForTableView> aObservable(){
        List<ClienteEntity> clientes = (List<ClienteEntity>) manager.createQuery("FROM ClienteEntity").getResultList();
        ObservableList<ClienteForTableView> clientesObservable = FXCollections.observableArrayList();
        ClienteEntity clienteMayor = obtenerClienteMayor(clientes);
        panelCliente(new Text("Nombre: " + clienteMayor.getNombre() + "\nMonto: " + clienteMayor.getMonto() +
        "\nPlazo: " + clienteMayor.getPlazo()));

        for (ClienteEntity c: clientes) {
            clientesObservable.add(new ClienteForTableView(c.getNombre(), c.getFechaNacimiento(),
                    Integer.parseInt(c.getMonto()), Integer.parseInt(c.getPlazo()), c.getProvedor()));
        }

        return clientesObservable;
    }

    /*Obtiene el cliente con mayor monto de prestamos*/
    private ClienteEntity obtenerClienteMayor(List<ClienteEntity> l){
        int posicion = 0;
        int aux = Integer.parseInt(l.get(0).getMonto());

        for (int i = 0; i < l.size(); i++) {
            if (Integer.parseInt(l.get(i).getMonto()) > aux){
                aux = Integer.parseInt(l.get(i).getMonto());
                posicion = i;
            }
        }

        return l.get(posicion);
    }

    /*Muestra ventana emergente*/
    private void panelCliente(Text t){
        JFXDialogLayout content = new JFXDialogLayout();
        Text titulo = new Text("Cliente con prestamo mayor: ");
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

    /*Clase interna que sirve de modelo para la tabla*/
    static final class ClienteForTableView extends RecursiveTreeObject<ClienteForTableView>{
        final SimpleStringProperty nombreCliente;
        final SimpleStringProperty fechaCliente;
        final SimpleIntegerProperty montoCliente;
        final SimpleIntegerProperty plazoCliente;
        final SimpleStringProperty provedorCliente;

        ClienteForTableView(String nombreCliente, String fechaCliente, Integer montoCliente,
                                   Integer plazoCliente, String provedorCliente) {
            this.nombreCliente = new SimpleStringProperty(nombreCliente);
            this.fechaCliente = new SimpleStringProperty(fechaCliente);
            this.montoCliente = new SimpleIntegerProperty(montoCliente);
            this.plazoCliente = new SimpleIntegerProperty(plazoCliente);
            this.provedorCliente = new SimpleStringProperty(provedorCliente);
        }
    }
}
