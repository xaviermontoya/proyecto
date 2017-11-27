package controladores;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
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
import javafx.scene.layout.VBox;
import modelo.elementos.Proveedor;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class Proveedores implements Initializable{

    /*Atributos*/
    @FXML
    private VBox rootProvedores;

    @FXML
    private JFXTreeTableView<ProvedoresForTable> provedoresView;

    @FXML
    private JFXTreeTableColumn<ProvedoresForTable, String> nombreEmpresa;

    @FXML
    private JFXTreeTableColumn<ProvedoresForTable, String> nombreRepresentante;

    @FXML
    private JFXTreeTableColumn<ProvedoresForTable, String> apellidoRepresentante;

    @FXML
    private JFXTreeTableColumn<ProvedoresForTable, Integer> numeroSolicitudes;

    public static EntityManager manager;

    /*Metodos que interactuan con las demas ventanas*/
    @FXML
    void toRegister(ActionEvent event) throws Exception{
        VBox pane = FXMLLoader.load(getClass().getResource("/vistas/registerfxml.fxml"));
        rootProvedores.getChildren().setAll(pane);
        event.consume();
    }

    @FXML
    void toSerach(ActionEvent event) throws Exception{
        VBox pane = FXMLLoader.load(getClass().getResource("/vistas/mainfxml.fxml"));
        rootProvedores.getChildren().setAll(pane);
        event.consume();
    }

    @FXML
    void toViewAll(ActionEvent event) throws Exception{
        VBox pane = FXMLLoader.load(getClass().getResource("/vistas/viewAllfxml.fxml"));
        rootProvedores.getChildren().setAll(pane);
        event.consume();
    }

    /*Método de incialización*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupReadOnlyTableView();
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ProvedoresForTable, T> column, Function<ProvedoresForTable, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProvedoresForTable, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    /*mostrar tabla de datos*/
    private void setupReadOnlyTableView(){
        setupCellValueFactory(nombreEmpresa, p -> p.nombreEmpresa);
        setupCellValueFactory(nombreRepresentante, p -> p.nombreRepresentante);
        setupCellValueFactory(apellidoRepresentante, p -> p.apellidoRepresentante);
        setupCellValueFactory(numeroSolicitudes, p -> p.numeroSolicitudes.asObject());

        final TreeItem<ProvedoresForTable> root = new RecursiveTreeItem<>(aObservableList(), RecursiveTreeObject::getChildren);
        provedoresView.setRoot(root);
        provedoresView.setShowRoot(false);
    }

    /*Método que prepara la información de los proveedores para mostrarlos en la tabla.*/
    private ObservableList<ProvedoresForTable> aObservableList(){
        Proveedor[] proveedores = Proveedor.getProveedores();
        Integer[] solicitudes = new Integer[proveedores.length];
        ObservableList<ProvedoresForTable> proveedoresObservable = FXCollections.observableArrayList();
        int count = 0;

        for (int i = 0; i < proveedores.length; i++) {
            solicitudes[i] = manager.createQuery("from ClienteEntity c where c.provedor = '" + proveedores[i].getNombreEmpresa()+"'").getResultList().size();
        }

        for (Proveedor p: proveedores) {
            proveedoresObservable.add(new ProvedoresForTable(p.getNombreEmpresa(),p.getNombreRepresentante(),p.getApellidoRepresentante(),solicitudes[count]));
            count++;
        }

        return proveedoresObservable;
    }

    /*Clase interna que sirve como molde para la tabla*/
    static final class ProvedoresForTable extends RecursiveTreeObject<ProvedoresForTable>{
        final SimpleStringProperty nombreEmpresa;
        final SimpleStringProperty nombreRepresentante;
        final SimpleStringProperty apellidoRepresentante;
        final SimpleIntegerProperty numeroSolicitudes;

        ProvedoresForTable(String nombreEmpresa, String nombreRepresentante, String apellidoRepresentante, int numeroSolicitudes) {
            this.nombreEmpresa = new SimpleStringProperty(nombreEmpresa);
            this.nombreRepresentante = new SimpleStringProperty(nombreRepresentante);
            this.apellidoRepresentante = new SimpleStringProperty(apellidoRepresentante);
            this.numeroSolicitudes = new SimpleIntegerProperty(numeroSolicitudes);
        }
    }

}
