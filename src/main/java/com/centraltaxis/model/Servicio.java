package com.centraltaxis.model;

import java.time.LocalDate;
import java.time.LocalTime;
// Importamos las anotaciones necesarias para JPA
import javax.persistence.*;

// Indicamos que la clase Servicio es una entidad JPA
// y que se corresponde con la tabla servicios de la base de datos
@Entity
@Table(name = "servicio")
public class Servicio {
    // ---------------------------- Atributos ----------------------------

    // Indicamos que el atributo idServicio es la clave primaria de la entidad
    // y que se generará automáticamente al insertar un nuevo servicio
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private int idServicio;

    // Claves foraneas para relacionar con otras entidades
    // Relacionamos la entidad Servicio con la entidad Conductor indicando la
    // cardinalidad y la clave foranea
    @ManyToOne
    @JoinColumn(name = "id_conductor", nullable = true)
    private Conductor conductor;

    // Relacionamos la entidad Servicio con la entidad Cliente indicando la
    // cardinalidad y la clave foranea
    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    // Atributos adicionales de la entidad Servicio
    @Column(name = "origen", nullable = false, length = 255)
    private String origen;
    @Column(name = "destino", nullable = false, length = 255)
    private String destino;
    @Column(name = "n_persona", nullable = false)
    private int nPersona;
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    @Column(name = "requisitos", nullable = true, length = 255)
    private String requisitos;
    @Column(name = "precio", nullable = false)
    private double precio;
    @Column(name = "precio_10", nullable = true)
    private double precio10;
    @Column(name = "eurotaxi", nullable = false)
    private boolean eurotaxi;
    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    // ---------------------------- Constructores ----------------------------
    public Servicio() {
    }

    public Servicio(Conductor conductor, Cliente cliente, String origen, String destino, int nPersona, LocalDate fecha,
            String requisitos, double precio, double precio10, LocalTime hora, boolean eurotaxi) {
        this.origen = origen;
        this.destino = destino;
        this.nPersona = nPersona;
        this.fecha = fecha;
        this.requisitos = requisitos;
        this.precio = precio;
        this.precio10 = precio10;
        this.hora = hora;
        this.eurotaxi = eurotaxi;
        if (conductor != null) {
            this.conductor = conductor;
        } else {
            this.conductor = null; // Permite que el conductor sea nulo
        }
        this.cliente = cliente;

    }

    // ---------------------------- Getters y Setters ----------------------------
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public Conductor getConductor() {
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
        return "Servicio{" +
                "idServicio=" + idServicio +
                ", conductor=" + (conductor != null ? conductor.getIdConductor() : "Conductor no asignado") +
                ", cliente=" + cliente.getIdCliente() +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", nPersona=" + nPersona +
                ", fecha=" + fecha +
                ", requisitos='" + requisitos + '\'' +
                ", precio=" + precio +
                ", precio10=" + precio10 +
                ", eurotaxi=" + eurotaxi +
                ", hora=" + hora +
                '}';
    }
}
