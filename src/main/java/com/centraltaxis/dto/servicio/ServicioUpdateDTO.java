package com.centraltaxis.dto.servicio;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO de actualización completa (PUT). El frontend debe enviar todos los
 * campos.
 * Mismo shape que create, pero conceptualmente es "reemplazar todo".
 */
public class ServicioUpdateDTO {
    private String origen;
    private String destino;
    private Integer nPersona;
    private LocalDate fecha;
    private Boolean eurotaxi;
    private LocalTime hora;
    private String requisitos;

    private Double precio;
    private Double precio10;

    private Integer conductorId; // null para quitar conductor

    private String clienteNombre;
    private String clienteTelefono;

    // Constructor
    public ServicioUpdateDTO() {
    }

    // Getters/Setters 
    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public Integer getnPersona() {
        return nPersona;
    }

    public void setnPersona(Integer nPersona) {
        this.nPersona = nPersona;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Integer getNPersona() {
        return nPersona;
    }

    public void setNPersona(Integer nPersona) {
        this.nPersona = nPersona;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getEurotaxi() {
        return eurotaxi;
    }

    public void setEurotaxi(Boolean eurotaxi) {
        this.eurotaxi = eurotaxi;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecio10() {
        return precio10;
    }

    public void setPrecio10(Double precio10) {
        this.precio10 = precio10;
    }

    public Integer getConductorId() {
        return conductorId;
    }

    public void setConductorId(Integer conductorId) {
        this.conductorId = conductorId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getClienteTelefono() {
        return clienteTelefono;
    }

    public void setClienteTelefono(String clienteTelefono) {
        this.clienteTelefono = clienteTelefono;
    }
}
