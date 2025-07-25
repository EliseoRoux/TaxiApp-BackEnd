package com.centraltaxis.model;

// Importamos las anotaciones necesarias para JPA
import javax.persistence.*;

// Indicamos que la clase Cliente es una entidad JPA
// y que se corresponde con la tabla clientes de la base de datos
@Entity
@Table(name = "cliente")
public class Cliente {
    // ---------------------------- Atributos ----------------------------

    // Indicamos que el atributo idCliente es la clave primaria de la entidad
    // y que se generará automáticamente al insertar un nuevo cliente
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int idCliente;

    // Atributos adicionales de la entidad Cliente
    // Estos atributos se corresponden con las columnas de la tabla clientes
    @Column(name = "nombre", nullable = true, length = 255)
    private String nombre;
    @Column(name = "telefono", nullable = true, length = 255)
    private String telefono;

    // ---------------------------- Constructores ----------------------------
    public Cliente() {
    }

    public Cliente(int idCliente, String nombre, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // ---------------------------- Getters y Setters ---------------------------- 
    public int getIdCliente() {
        return idCliente;
    }
    public String getNombre() {
        return nombre;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setId(int idCliente) {
        this.idCliente = idCliente;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
