package com.centraltaxis.dto.cliente;

import javax.validation.constraints.NotBlank;

public class ClienteCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El nombre es obligatorio")
    private String telefono;

    // Constructor
    public ClienteCreateDTO() {

    }

    // Getters y Setters
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
