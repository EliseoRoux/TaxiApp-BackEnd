package com.centraltaxis.dto.cliente;

/** DTO pequeño para representar cliente en respuestas */
public class ClienteInfoDTO {
    private Integer idCliente;
    private String nombre;
    private String telefono;

    // getters and setters

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

}
