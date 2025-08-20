package com.centraltaxis.dto.conductor;

/** DTO pequeño para representar conductor en respuestas */
public class ConductorResponseDTO {
    private Integer idConductor;
    private String nombre;
    private String telefono;
    private Double deuda;
    private Double dineroGenerado;

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
        return "ConductorResponseDTO [idConductor=" + idConductor + ", nombre=" + nombre + ", telefono=" + telefono
                + ", deuda=" + deuda + ", dineroGenerado=" + dineroGenerado + ", getIdConductor()=" + getIdConductor()
                + ", getNombre()=" + getNombre() + ", getTelefono()=" + getTelefono() + ", getDeuda()=" + getDeuda()
                + ", getDineroGenerado()=" + getDineroGenerado() + ", getClass()=" + getClass() + ", hashCode()="
                + hashCode() + ", toString()=" + super.toString() + "]";
    }
}
