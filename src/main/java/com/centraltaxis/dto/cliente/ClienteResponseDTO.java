package com.centraltaxis.dto.cliente;

import java.time.LocalDateTime;

public class ClienteResponseDTO {
    private Integer idCliente;
    private String nombre;
    private String telefono;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Integer totalServicios;

    // Constructor por defecto
    public ClienteResponseDTO() {
    }

    // Constructor con parámetros básicos
    public ClienteResponseDTO(Integer idCliente, String nombre, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Constructor completo
    public ClienteResponseDTO(Integer idCliente, String nombre, String telefono, 
                            LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion, 
                            Integer totalServicios) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.totalServicios = totalServicios;
    }

    // Getters y Setters
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Integer getTotalServicios() {
        return totalServicios;
    }

    public void setTotalServicios(Integer totalServicios) {
        this.totalServicios = totalServicios;
    }

    @Override
    public String toString() {
        return "ClienteResponseDTO{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", totalServicios=" + totalServicios +
                '}';
    }
}