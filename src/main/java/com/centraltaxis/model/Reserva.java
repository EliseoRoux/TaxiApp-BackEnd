package com.centraltaxis.model;

import java.time.LocalDate;
import java.time.LocalTime;
// Importamos las anotaciones necesarias para JPA
import javax.persistence.*;

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
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    // Atributos adicionales de la entidad Reserva
    @Column(name = "origen", nullable = false, length = 255)
    private String origen;
    @Column(name = "destino", nullable = false, length = 255)
    private String destino;
    @Column(name = "n_persona", nullable = false)
    private int nPersona;
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;
    @Column(name = "requisitos", nullable = true, length = 255)
    private String requisitos;
    @Column(name = "precio", nullable = false)
    private double precio;
    @Column(name = "precio_10", nullable = true)
    private double precio10;
    @Column(name = "hora", nullable = false)
    private LocalTime hora;
    @Column(name = "eurotaxi", nullable = false)
    private boolean eurotaxi;

    // ---------------------------- Constructores ----------------------------

    public Reserva() {
    }

    public Reserva(Cliente cliente, Conductor conductor, String origen, String destino, int nPersona,
            LocalDate fechaReserva,
            String requisitos, double precio, double precio10, LocalTime hora, boolean eurotaxi) {

        this.cliente = cliente;
        if (conductor != null) {
            this.conductor = conductor;
        } else {
            this.conductor = null; // Permite que el conductor sea nulo
        }
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

    public int getNPersona() {
        return nPersona;
    }

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
