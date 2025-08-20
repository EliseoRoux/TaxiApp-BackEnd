package com.centraltaxis.dto.conductor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ConductorUpdateDTO {

    @Size(max = 15, message = "El teléfono no puede exceder los 15 caracteres")
    private String nombre;

    @Pattern(regexp = "^(\\+?[0-9]{1,3}[-.\\s]?)?([0-9]{2,4}[-.\\s]?){2,4}[0-9]{2,4}$", message = "Teléfono inválido. Ejemplos válidos: +34 666-777-888, 912 345 678, 622.33.44.55")
    @Size(max = 15, message = "El teléfono no puede exceder los 15 caracteres")
    private String telefono;

    @Digits(integer = 10, fraction = 2, message = "La deuda debe ser un número válido")
    private Double deuda;

    @Digits(integer = 10, fraction = 2, message = "El dinero generado debe ser un número válido")
    private Double dineroGenerado;

    // Constructor
    public ConductorUpdateDTO() {
    }

    public ConductorUpdateDTO(String nombre, String telefono, Double deuda, Double dineroGenerado) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.deuda = deuda;
        this.dineroGenerado = dineroGenerado;
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

    public Double getDeuda() {
        return deuda;
    }

    public void setDeuda(Double deuda) {
        this.deuda = deuda;
    }

    public Double getDineroGenerado() {
        return dineroGenerado;
    }

    public void setDineroGenerado(Double dineroGenerado) {
        this.dineroGenerado = dineroGenerado;
    }

    @Override
    public String toString() {
        return "ConductorUpdateDTO [nombre=" + nombre + ", telefono=" + telefono + ", deuda=" + deuda
                + ", dineroGenerado=" + dineroGenerado + ", getNombre()=" + getNombre() + ", getTelefono()="
                + getTelefono() + ", getClass()=" + getClass() + ", getDeuda()=" + getDeuda() + ", getDineroGenerado()="
                + getDineroGenerado() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}
