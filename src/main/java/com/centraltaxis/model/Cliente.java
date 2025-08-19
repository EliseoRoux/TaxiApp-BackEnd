package com.centraltaxis.model;

import java.time.LocalDateTime;

// Importamos las anotaciones necesarias para JPA
import javax.persistence.*;
// Importamos las anotaciones necesarias para la validación
import javax.validation.constraints.*;

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
    // Añadimos las anotaciones JPA y de validación necesarias
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @Pattern(regexp = "^(\\+?[0-9]{1,3}[-.\\s]?)?([0-9]{2,4}[-.\\s]?){2,4}[0-9]{2,4}$", message = "Teléfono inválido. Ejemplos válidos: +34 666-777-888, 912 345 678, 622.33.44.55")
    @NotBlank(message = "El telefono es obligatorio")
    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

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

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
