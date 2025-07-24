package com.centraltaxis.model;
public class Conductor {
    //Atributos
    private int id;
    private String nombre;
    private String telefono;
    private double deuda;
    private double dineroGenerado;
    
    // Constructor
    public Conductor(){

    }

    public Conductor(int id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }
    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public double getDeuda() {
        return deuda;
    }

    public double getDineroGenerado() {
        return dineroGenerado;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public void setDineroGenerado(double dineroGenerado) {
        this.dineroGenerado = dineroGenerado;
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", deuda=" + deuda +
                ", dineroGenerado=" + dineroGenerado +
                '}';    
    }

}