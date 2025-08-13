package com.centraltaxis.dto.servicio;

/**
 * DTO pequeño para devolver datos básicos del cliente dentro de un
 * ServicioResponseDTO.
 */
public class ClienteBriefDTO {
    private int idCliente;
    private String nombre;
    private String telefono;

    public ClienteBriefDTO() {
    }

    public ClienteBriefDTO(int idCliente, String nombre, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
