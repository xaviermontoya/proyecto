package modelo.elementos;

public class Proveedor {
    /*Atributos*/
    private static Proveedor[] proveedores;

    private String nombreEmpresa;
    private String nombreRepresentante;
    private String apellidoRepresentante;
    private Integer solicitudes;

    /*Constructor*/
    private Proveedor(String nombreEmpresa, String nombreRepresentante, String apellidoRepresentante, Integer solicitudes) {
        this.nombreEmpresa = nombreEmpresa;
        this.nombreRepresentante = nombreRepresentante;
        this.apellidoRepresentante = apellidoRepresentante;
        this.solicitudes = solicitudes;
    }

    /*Agregar información de los proveedores disponbles*/
    public static void agregarProveedores(){
        proveedores = new Proveedor[3];

        proveedores[0] = new Proveedor("BBVA Bancomer", "Erika", "Acosta", 0);
        proveedores[1] = new Proveedor("Banorte", "Daniel", "Rivero", 0);
        proveedores[2] = new Proveedor("Banamex", "Juan Carlos", "Montoya", 0);
    }

    /*Getters*/
    public static Proveedor[] getProveedores() {
        return proveedores;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getNombreRepresentante() {
        return nombreRepresentante;
    }

    public String getApellidoRepresentante() {
        return apellidoRepresentante;
    }
}
