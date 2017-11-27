import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class Manejador {
    /*Atributos*/
    private static EntityManager manager;
    private static EntityManagerFactory factory;

    /*Inicializa la conexión con base de datos*/
    static void inicializar(){
        factory = Persistence.createEntityManagerFactory("p");
        manager = factory.createEntityManager();
    }

    /*Cierra la conexion con base de datos*/
    static void cerrarGestion(){
        manager.close();
        factory.close();
    }

    /*Get del manejador*/
    static EntityManager getManager() {
        return manager;
    }
}
