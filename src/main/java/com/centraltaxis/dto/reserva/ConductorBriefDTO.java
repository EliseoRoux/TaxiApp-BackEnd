package com.centraltaxis.dto.reserva;

/**
 * DTO pequeño para devolver datos básicos del conductor dentro de un
 * ServicioResponseDTO.
 * Importante: no exponemos deuda/dinero aquí (se vería en una ficha de
 * conductor).
 */
public class ConductorBriefDTO {
    private int idConductor;
    private String nombre;
    private String telefono;

    public ConductorBriefDTO() {
    }

    public ConductorBriefDTO(int idConductor, String nombre, String telefono) {
        this.idConductor = idConductor;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
