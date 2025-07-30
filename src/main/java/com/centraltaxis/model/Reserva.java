package com.centraltaxis.model;

import java.time.LocalDate;
import java.time.LocalTime;
// Importamos las anotaciones necesarias para JPA
import javax.persistence.*;
// Importamos las anotaciones necesarias para la validación
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;

// Indicamos que la clase Reserva es una entidad JPA
// y que se corresponde con la tabla reservas de la base de datos
@Entity
@Table(name = "reserva")
public class Reserva {
    // ---------------------------- Atributos ----------------------------

    // Indicamos que el atributo idReserva es la clave primaria de la entidad
    // y que se generará automáticamente al insertar una nueva reserva
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private int idReserva;

    // Claves foraneas para relacionar con otras entidades
    // Relacionamos la entidad Reserva con la entidad Conductor indicando la
    // cardinalidad y la clave foranea
    @ManyToOne
    @JoinColumn(name = "id_conductor", nullable = true)
    private Conductor conductor;
    // Relacionamos la entidad Reserva con la entidad Cliente indicando la
    // cardinalidad y la clave foranea
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = true)
    private Cliente cliente;

    // Atributos adicionales de la entidad Reserva
    @Size(max = 255, message = "El origen no puede exceder los 255 caracteres")
    @NotNull(message = "El origen es obligatorio")
    @Column(nullable = false, length = 255)
    private String origen;

    @Size(max = 255, message = "El destino no puede exceder los 255 caracteres")
    @NotNull(message = "El destino es obligatorio")
    @Column(nullable = false, length = 255)
    private String destino;

    @Column(name = "n_persona", nullable = false)
    private int nPersona;

    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Size(max = 255, message = "Los requisitos no pueden exceder los 255 caracteres")
    @Column(nullable = true, length = 255)
    private String requisitos;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser un número positivo")
    @Column(nullable = true)
    private double precio;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio 10 debe ser un número positivo")
    @Column(name = "precio_10", nullable = true)
    private double precio10;

    @NotNull(message = "La hora es obligatoria")
    @Column(nullable = false)
    private LocalTime hora;

    @NotNull(message = "Indicar si es eurotaxi")
    @Column(nullable = false)
    private boolean eurotaxi;

    // ---------------------------- Constructores ----------------------------

    public Reserva() {
    }

    public Reserva(Cliente cliente, Conductor conductor, String origen, String destino, int nPersona,
            LocalDate fechaReserva,
            String requisitos, double precio, double precio10, LocalTime hora, boolean eurotaxi) {

        this.cliente = cliente;
        this.conductor = conductor;
        this.origen = origen;
        this.destino = destino;
        this.nPersona = nPersona;
        this.fechaReserva = fechaReserva;
        this.requisitos = requisitos;
        this.precio = precio;
        this.precio10 = precio10;
        this.hora = hora;
        this.eurotaxi = eurotaxi;
    }

    // ---------------------------- Getters y Setters ----------------------------
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Conductor getConductor() {
        if (conductor == null) {
            return null; // Permite que el conductor sea nulo
        }
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @JsonProperty("nPersona")
    public int getNPersona() {
        return nPersona;
    }

    @JsonProperty("nPersona")
    public void setNPersona(int nPersona) {
        this.nPersona = nPersona;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecio10() {
        return precio10;
    }

    public void setPrecio10(double precio10) {
        this.precio10 = precio10;
    }

    public boolean isEurotaxi() {
        return eurotaxi;
    }

    public void setEurotaxi(boolean eurotaxi) {
        this.eurotaxi = eurotaxi;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", conductor=" + (conductor != null ? conductor.getIdConductor() : "Conductor no asignado") +
                ", cliente=" + cliente.getIdCliente() +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", nPersona=" + nPersona +
                ", fechaReserva=" + fechaReserva +
                ", requisitos='" + requisitos + '\'' +
                ", precio=" + precio +
                ", precio10=" + precio10 +
                ", hora=" + hora +
                ", eurotaxi=" + eurotaxi +
                '}';
    }

}
