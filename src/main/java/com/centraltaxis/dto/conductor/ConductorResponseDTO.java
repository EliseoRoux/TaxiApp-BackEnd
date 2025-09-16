package com.centraltaxis.dto.conductor;

/** DTO para representar un conductor en las respuestas de la API. */
public class ConductorResponseDTO {
    private Integer idConductor;
    private String nombre;
    private String telefono;
    private Double deuda;
    private Double dineroGenerado;
    private Integer asiento;
    private Integer sillaBebe;
    private Boolean eurotaxi;

    // Constructor por defecto
    public ConductorResponseDTO() {
    }

    // Constructor con todos los parámetros
    public ConductorResponseDTO(Integer idConductor, String nombre, String telefono, Double deuda,
            Double dineroGenerado, Integer asiento, Integer sillaBebe, Boolean eurotaxi) {
        this.idConductor = idConductor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.deuda = deuda;
        this.dineroGenerado = dineroGenerado;
        this.asiento = asiento;
        this.sillaBebe = sillaBebe;
        this.eurotaxi = eurotaxi;
    }

    // --- Getters y Setters  ---
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

    public Boolean getEurotaxi() {
        return eurotaxi;
    }

    public void setEurotaxi(Boolean eurotaxi) {
        this.eurotaxi = eurotaxi;
    }
}