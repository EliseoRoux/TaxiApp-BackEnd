package com.centraltaxis.dto.reserva;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaResponseDTO {
    private int idReserva;
    private String origen;
    private String destino;
    private Integer nPersona;
    private LocalDate fechaReserva;
    private LocalTime hora;
    private String requisitos;
    private Double precio;
    private Double precio10;
    private Boolean eurotaxi;
    private Boolean mascota;
    private Boolean silla;
    private Boolean viajeLargo;

    private ConductorBriefDTO conductor;
    private ClienteBriefDTO cliente;

    public ReservaResponseDTO() {
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

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
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

    public Integer getnPersona() {
        return nPersona;
    }

    public void setnPersona(Integer nPersona) {
        this.nPersona = nPersona;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
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