package sample;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.IntegerTextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableColumn;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

public class Controller implements Initializable{
    @FXML
    private JFXTreeTableView<Punto> treeView;
    @FXML
    private JFXTreeTableColumn<Punto, Integer> xEditable;
    @FXML
    private JFXTreeTableColumn<Punto, Integer> yEditable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setupEditableTableView();
        setupReadOnlyTableView();
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<Punto, T> column, Function<Punto, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<Punto, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void setupReadOnlyTableView(){
        setupCellValueFactory(xEditable, p -> p.x.asObject());
        setupCellValueFactory(yEditable, p -> p.y.asObject());

        final ObservableList<Punto> list = FXCollections.observableArrayList();
        list.add(new Punto(1,1));
        list.add(new Punto(2,2));
        list.add(new Punto(3,3));
        list.add(new Punto(4,4));
        list.add(new Punto(5,5));
        list.add(new Punto(6,6));
        list.add(new Punto(7,7));
        list.add(new Punto(8,8));
        list.add(new Punto(9,9));

        treeView.setRoot(new RecursiveTreeItem<Punto>(list, RecursiveTreeObject::getChildren));
        treeView.setShowRoot(false);
    }

    /*private void setupEditableTableView(){
        setupCellValueFactory(xEditable, p -> p.x.asObject());
        setupCellValueFactory(yEditable, p -> p.y.asObject());

        //Añadiendo los editores
        xEditable.setCellFactory((TreeTableColumn<Punto, Integer> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new IntegerTextFieldEditorBuilder());
        });
        xEditable.setOnEditCommit((TreeTableColumn.CellEditEvent<Punto, Integer> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().x.set(t.getNewValue());

            System.out.println(t.getNewValue());
        });

        yEditable.setCellFactory((TreeTableColumn<Punto, Integer> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new IntegerTextFieldEditorBuilder());
        });
        yEditable.setOnEditCommit((TreeTableColumn.CellEditEvent<Punto, Integer> t) -> {
            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().y.set(t.getNewValue());

            System.out.println(t.getNewValue());
        });

        final ObservableList<Punto> list = FXCollections.observableArrayList();
        list.add(new Punto(1,1));
        list.add(new Punto(2,2));
        list.add(new Punto(3,3));
        list.add(new Punto(4,4));
        list.add(new Punto(5,5));
        list.add(new Punto(6,6));
        list.add(new Punto(7,7));
        list.add(new Punto(8,8));
        list.add(new Punto(9,9));

        treeView.setRoot(new RecursiveTreeItem<Punto>(list, RecursiveTreeObject::getChildren));
        treeView.setShowRoot(false);
        treeView.setEditable(true);

    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<Punto> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(puntoProp -> {
                    final Punto punto = puntoProp.getValue();
                    return Integer.toString(punto.x.get()).contains(newVal) || Integer.toString(punto.y.get()).contains(newVal);
                });
    }*/



    static final class Punto extends RecursiveTreeObject<Punto>{
        final SimpleIntegerProperty x;
        final SimpleIntegerProperty y;

        public Punto(int x, int y){
            this.x = new SimpleIntegerProperty(x);
            this.y = new SimpleIntegerProperty(y);
        }

    }
}
