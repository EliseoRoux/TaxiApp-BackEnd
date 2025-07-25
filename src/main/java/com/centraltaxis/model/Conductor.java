package com.centraltaxis.model;

// Importamos las anotaciones necesarias para JPA
import javax.persistence.*;

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
    // Atributos adicionales de la entidad Conductor
    // Estos atributos se corresponden con las columnas de la tabla conductor
    @Column(name = "nombre", nullable = true, length = 255)
    private String nombre;
    @Column(name = "telefono", nullable = true, length = 255)
    private String telefono;
    @Column(name = "deuda", nullable = true)
    private double deuda;
    @Column(name = "dinero_generado", nullable = true)
    private double dineroGenerado;
    
    // Constructor
    public Conductor(){

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