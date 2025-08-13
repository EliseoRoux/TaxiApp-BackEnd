package com.centraltaxis.dto.servicio;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO de creación. Lo que el frontend envía al crear un servicio.
 * - El cliente se envía como nombre + teléfono (buscaremos/crearemos en
 * Service).
 * - El conductor se envía como ID opcional (puede venir null).
 */
public class ServicioCreateDTO {
    // Campos obligatorios del servicio
    private String origen;
    private String destino;
    private Integer nPersona;
    private LocalDate fecha;
    private Boolean eurotaxi;
    private LocalTime hora;

    // Campos de precio (pueden venir 0).
    private Double precio; 
    private Double precio10; 

    // Conductor opcional
    private Integer conductorId; // null si no se asigna

    // Datos mínimos del cliente (obligatorios)
    private String clienteNombre;
    private String clienteTelefono;

    // Constructor
    public ServicioCreateDTO() {
    }

    // Getters/Setters
    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    @JsonProperty("nPersona")
    public Integer getNPersona() {
        return nPersona;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Boolean getEurotaxi() {
        return eurotaxi;
    }

    public LocalTime getHora() {
        return hora;
    }

    public Double getPrecio() {
        return precio;
    }

    public Double getPrecio10() {
        return precio10;
    }

    public Integer getConductorId() {
        return conductorId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
    
    @JsonProperty("nPersona")
    public void setNPersona(Integer nPersona) {
        this.nPersona = nPersona;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setEurotaxi(Boolean eurotaxi) {
        this.eurotaxi = eurotaxi;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setPrecio10(Double precio10) {
        this.precio10 = precio10;
    }

    public void setConductorId(Integer conductorId) {
        this.conductorId = conductorId;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }
}
