package com.centraltaxis.dto.servicio;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServicioCreateDTO {
    private String origen;
    private String destino;
    private Integer nPersona;
    private LocalDate fecha;
    private Boolean eurotaxi;
    private LocalTime hora;
    private String requisitos;
    private Boolean mascota;
    private Boolean silla;
    private Boolean viajeLargo;
    private Double precio;
    private Double precio10;
    private String clienteNombre;
    private String clienteTelefono;
    @JsonProperty("idConductor") 
    private Integer idConductor;

    // Constructor
    public ServicioCreateDTO() {
    }

    // --- GETTER Y SETTER CORREGIDOS ---
    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
    }

    // ... (resto de getters y setters sin cambios)
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
    public Integer getNPersona() {
        return nPersona;
    }

    @JsonProperty("nPersona")
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

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public Boolean getMascota() {
        return mascota;
    }

    public void setMascota(Boolean mascota) {
        this.mascota = mascota;
    }

    public Boolean getSilla() {
        return silla;
    }

    public void setSilla(Boolean silla) {
        this.silla = silla;
    }

    public Boolean getViajeLargo() {
        return viajeLargo;
    }

    public void setViajeLargo(Boolean viajeLargo) {
        this.viajeLargo = viajeLargo;
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