package com.centraltaxis.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
    private int idReserva;
    private Conductor conductor;
    private Cliente cliente;
    private String origen;
    private String destino;
    private int nPersona;
    private LocalDate fechaReserva;
    private String requisitos;
    private double precio;
    private double precio10;
    private LocalTime hora;
    private boolean eurotaxi;

    // Constructor
    public Reserva() {
    }

    public Reserva(Cliente cliente, Conductor conductor, String origen, String destino, int nPersona, LocalDate fechaReserva,
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


    // Getters and Setters
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
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

    public int getnPersona() {
        return nPersona;
    }

    public void setnPersona(int nPersona) {
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

    public boolean getEurotaxi() {
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

   

}
