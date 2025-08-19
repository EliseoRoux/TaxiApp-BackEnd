package com.centraltaxis.dto.cliente;

import javax.validation.constraints.*;

public class ClienteCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^(\\+?[0-9]{1,3}[-.\\s]?)?([0-9]{2,4}[-.\\s]?){2,4}[0-9]{2,4}$", 
             message = "Teléfono inválido. Ejemplos válidos: +34 666-777-888, 912 345 678, 622.33.44.55")
    @Size(max = 15, message = "El teléfono no puede exceder los 15 caracteres")
    private String telefono;

    // Constructor por defecto
    public ClienteCreateDTO() {
    }

    // Constructor con parámetros
    public ClienteCreateDTO(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
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

    @Override
    public String toString() {
        return "ClienteCreateDTO{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}