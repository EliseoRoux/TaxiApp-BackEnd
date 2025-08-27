package com.centraltaxis.dto.conductor;

/** DTO pequeño para representar conductor en respuestas */
public class ConductorResponseDTO {
    private Integer idConductor;
    private String nombre;
    private String telefono;
    private Double deuda;
    private Double dineroGenerado;
    private int asiento;
    private int sillaBebe;
    private boolean eurotaxi;

    // Constructor
    public ConductorResponseDTO() {}

    public ConductorResponseDTO(Integer idConductor, String nombre, String telefono, Double deuda, Double dineroGenerado, int asiento, int sillaBebe, boolean eurotaxi) {
        this.idConductor = idConductor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.deuda = deuda;
        this.dineroGenerado = dineroGenerado;
        this.asiento = asiento;
        this.sillaBebe = sillaBebe;
        this.eurotaxi = eurotaxi;
    }

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

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public int getSillaBebe() {
        return sillaBebe;
    }

    public void setSillaBebe(int sillaBebe) {
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
        return "ConductorResponseDTO [idConductor=" + idConductor + ", nombre=" + nombre + ", telefono=" + telefono
                + ", deuda=" + deuda + ", dineroGenerado=" + dineroGenerado + ", asiento=" + asiento + ", sillaBebe="
                + sillaBebe + ", eurotaxi=" + eurotaxi + ", getIdConductor()=" + getIdConductor() + ", getNombre()="
                + getNombre() + ", getTelefono()=" + getTelefono() + ", getDeuda()=" + getDeuda()
                + ", getDineroGenerado()=" + getDineroGenerado() + ", getAsiento()=" + getAsiento()
                + ", getSillaBebe()=" + getSillaBebe() + ", isEurotaxi()=" + isEurotaxi() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

    
}
