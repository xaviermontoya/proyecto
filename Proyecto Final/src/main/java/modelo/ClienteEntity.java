package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class ClienteEntity {
    /*Atributos que sirven como modelo para que la base sea creada*/
    @Id
    @Column(unique = true)
    private String curp;
    @Column
    private String nombre;
    @Column
    private String apellidoPaterno;
    @Column
    private String apellidoMaterno;
    @Column
    private String fechaNacimiento;
    @Column
    private String rfc;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String telefono;
    @Column
    private String provedor;
    @Column
    private String monto;
    @Column
    private String plazo;
    @Column
    private String tipoRegistro;
    @Column
    private String comentario;

    /*Constructores*/
    public ClienteEntity() {

    }

    public ClienteEntity(String nombre, String apellidoPaterno, String apellidoMaterno, String fechaNacimiento,
                         String curp, String rfc, String email, String telefono, String provedor,
                         String monto, String plazo, String tipoRegistro, String comentario) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.rfc = rfc;
        this.email = email;
        this.telefono = telefono;
        this.provedor = provedor;
        this.monto = monto;
        this.plazo = plazo;
        this.tipoRegistro = tipoRegistro;
        this.comentario = comentario;
    }

    /*Getters y setters*/
    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getCurp() {
        return curp;
    }

    public String getRfc() {
        return rfc;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getProvedor() {
        return provedor;
    }

    public String getMonto() {
        return monto;
    }

    public String getPlazo() {
        return plazo;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /*Método toString*/
    @Override
    public String toString() {
        return "ClienteEntity{" +
                "nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", curp='" + curp + '\'' +
                ", rfc='" + rfc + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", provedor='" + provedor + '\'' +
                ", monto='" + monto + '\'' +
                ", plazo='" + plazo + '\'' +
                ", tipoRegistro='" + tipoRegistro + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }
}
