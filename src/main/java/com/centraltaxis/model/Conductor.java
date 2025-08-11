package com.centraltaxis.model;

// Importamos las anotaciones necesarias para JPA
import javax.persistence.*;
// Importamos las anotaciones necesarias para la validación
import javax.validation.constraints.*;

// Indicamos que la clase Conductor es una entidad JPA
// y que se corresponde con la tabla conductor de la base de datos
@Entity
@Table(name = "conductor")
public class Conductor {
    // ---------------------------- Atributos ----------------------------

    // Indicamos que el atributo id es la clave primaria de la entidad
    // y que se generará automáticamente al insertar un nuevo conductor
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "id_conductor")
    private int idConductor;
    // Añadimos las anotaciones JPA y de validación necesarias
    // Atributos adicionales de la entidad Conductor
    // Estos atributos se corresponden con las columnas de la tabla conductor
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Pattern(regexp = "^(\\+?[0-9\\s\\-\\.]{7,15})$", message = "Teléfono inválido. Use +prefix, números y guiones/espacios")
    @Column(nullable = true, length = 15)
    private String telefono;

    @Digits(integer = 10, fraction = 2, message = "La deuda debe ser un número válido")
    @Column(nullable = true)
    private Double deuda;

    @Digits(integer = 10, fraction = 2, message = "El dinero generado debe ser un número válido")
    @Column(name = "dinero_generado", nullable = true)
    private Double dineroGenerado;

    // Constructor
    public Conductor() {

    }

    public Conductor(int idConductor, String nombre, String telefono) {
        this.idConductor = idConductor;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Getters
    public int getIdConductor() {
        return idConductor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public double getDeuda() {
        return deuda;
    }

    public double getDineroGenerado() {
        return dineroGenerado;
    }

    // Setters
    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public void setDineroGenerado(double dineroGenerado) {
        this.dineroGenerado = dineroGenerado;
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "id=" + idConductor +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", deuda=" + deuda +
                ", dineroGenerado=" + dineroGenerado +
                '}';
    }

}