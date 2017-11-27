import controladores.Principal;
import controladores.Proveedores;
import controladores.Registro;
import controladores.VerTodo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import modelo.elementos.Proveedor;

/*-------------Aplicación de procedimiento offline--------------*
* Creado por Francisco Javier Montoya Cadena.                   */

public class Main extends Application{

    public static void main(String[] args) {
        /*Iniciar conexión con base de datos*/
        Manejador.inicializar();

        /*Asignacion del manejador a cada uno de los controladores*/
        Registro.manager = Manejador.getManager();
        VerTodo.manager = Manejador.getManager();
        Principal.manager = Manejador.getManager();
        Proveedores.manager = Manejador.getManager();

        /*Agregar proveedores y lanzar app*/
        Proveedor.agregarProveedores();
        launch(args);

        /*Cerrar gestion con base de datos*/
        Manejador.cerrarGestion();
    }

    /*Muestra la ventana principal mediante la lectura de un fxml*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/loginVivus.fxml"));
        primaryStage.setTitle("Bienvenido");
        primaryStage.setResizable(false);
        primaryStage.getIcons().addAll(new Image("/image/IconVivus.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
