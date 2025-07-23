package com.centraltaxis.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Servicio {

    //Atributos
    private int idServicio;
    private int idConductor;
    private int idCliente;
    private String origen;
    private String destino;
    private int nPersona;
    private LocalDate fecha;
    private String requisitos;
    private double precio;
    private double precio10;
    private boolean eurotaxi;
    private LocalTime hora;

    // Constructor
    public Servicio() {
    }

    public Servicio(String origen, String destino, int nPersona, LocalDate fecha, String requisitos, double precio, double precio10, LocalTime hora, boolean eurotaxi, int idCliente, int idConductor) {
        this.origen = origen;
        this.destino = destino;
        this.nPersona = nPersona;
        this.fecha = fecha;
        this.requisitos = requisitos;
        this.precio = precio;
        this.precio10 = precio10;
        this.hora = hora;
        this.eurotaxi = eurotaxi;
        this.idCliente = idCliente;
        this.idConductor = idConductor;
        
    }

    // Getters and Setters
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    @Override
    public String toString() {
        return "Servicio{" + "idServicio=" + idServicio + ", idConductor=" + idConductor + ", idCliente=" + idCliente + ", origen=" + origen + ", destino=" + destino + ", nPersona=" + nPersona + ", fecha=" + fecha + ", requisitos=" + requisitos + ", precio=" + precio + ", precio10=" + precio10 + ", eurotaxi=" + eurotaxi + ", hora=" + hora + '}';
    }
}
