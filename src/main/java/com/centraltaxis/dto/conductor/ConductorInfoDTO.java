package com.centraltaxis.dto.conductor;

/** DTO pequeño para representar conductor en respuestas */
public class ConductorInfoDTO {
    private Integer idConductor;
    private String nombre;
    private String telefono;

    // getters y setters
    public Integer getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(Integer idConductor) {
        this.idConductor = idConductor;
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
