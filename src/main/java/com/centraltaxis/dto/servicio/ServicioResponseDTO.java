package com.centraltaxis.dto.servicio;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServicioResponseDTO {
    private int idServicio;
    private String origen;
    private String destino;
    private int nPersona;
    private LocalDate fecha;
    private String requisitos;
    private double precio;
    private double precio10;
    private LocalTime hora;
    private Boolean eurotaxi;
    private Boolean mascota;
    private Boolean silla;
    private Boolean viajeLargo;

    private ConductorBriefDTO conductor;
    private ClienteBriefDTO cliente;

    public ServicioResponseDTO() {
    }

    public Boolean getEurotaxi() {
        return eurotaxi;
    }

    public void setEurotaxi(Boolean eurotaxi) {
        this.eurotaxi = eurotaxi;
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

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
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

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public ConductorBriefDTO getConductor() {
        return conductor;
    }

    public void setConductor(ConductorBriefDTO conductor) {
        this.conductor = conductor;
    }

    public ClienteBriefDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteBriefDTO cliente) {
        this.cliente = cliente;
    }
}