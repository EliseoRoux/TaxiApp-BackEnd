package com.centraltaxis.dto.conductor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ConductorCreateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El telefono es obligatorio")
    @Pattern(regexp = "^(\\+?[0-9]{1,3}[-.\\s]?)?([0-9]{2,4}[-.\\s]?){2,4}[0-9]{2,4}$", message = "Teléfono inválido. Ejemplos válidos: +34 666-777-888, 912 345 678, 622.33.44.55")
    @Size(max = 15, message = "El teléfono no puede exceder los 15 caracteres")
    private String telefono;

    private Integer asiento;

    private Integer sillaBebe;

    private boolean eurotaxi;

    public ConductorCreateDTO() {
    }

    public ConductorCreateDTO(String nombre, String telefono, Integer asiento, Integer sillaBebe, boolean eurotaxi) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.asiento = asiento;
        this.sillaBebe = sillaBebe;
        this.eurotaxi = eurotaxi;
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

    public Integer getAsiento() {
        return asiento;
    }

    public void setAsiento(Integer asiento) {
        this.asiento = asiento;
    }

    public Integer getSillaBebe() {
        return sillaBebe;
    }

    public void setSillaBebe(Integer sillaBebe) {
        this.sillaBebe = sillaBebe;
    }

    public boolean isEurotaxi() {
        return eurotaxi;
    }

    public void setEurotaxi(boolean eurotaxi) {
        this.eurotaxi = eurotaxi;
    }

    @Override
    public String toString() {
        return "ConductorCreateDTO [nombre=" + nombre + ", telefono=" + telefono + ", asiento=" + asiento
                + ", sillaBebe=" + sillaBebe + ", eurotaxi=" + eurotaxi + ", getNombre()=" + getNombre()
                + ", getTelefono()=" + getTelefono() + ", getAsiento()=" + getAsiento() + ", getSillaBebe()="
                + getSillaBebe() + ", isEurotaxi()=" + isEurotaxi() + ", getClass()=" + getClass() + ", hashCode()="
                + hashCode() + ", toString()=" + super.toString() + "]";
    }

    

}
