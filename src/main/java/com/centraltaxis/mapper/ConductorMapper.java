package com.centraltaxis.mapper;

import com.centraltaxis.dto.conductor.ConductorCreateDTO;
import com.centraltaxis.dto.conductor.ConductorResponseDTO;
import com.centraltaxis.dto.conductor.ConductorUpdateDTO;
import com.centraltaxis.model.Conductor;
import org.springframework.stereotype.Component;

@Component
public class ConductorMapper {

    public Conductor toEntity(ConductorCreateDTO dto) {
        Conductor conductor = new Conductor();
        conductor.setNombre(dto.getNombre());
        conductor.setTelefono(dto.getTelefono());
        conductor.setDeuda(0.0);
        conductor.setDineroGenerado(0.0);
        conductor.setAsiento(dto.getAsiento());
        conductor.setSillaBebe(dto.getSillaBebe());
        conductor.setEurotaxi(dto.isEurotaxi()); 
        return conductor;
    }

    public void updateEntity(ConductorUpdateDTO dto, Conductor existente) {
        if (dto.getNombre() != null)
            existente.setNombre(dto.getNombre());
        if (dto.getTelefono() != null)
            existente.setTelefono(dto.getTelefono());
        if (dto.getDeuda() != null)
            existente.setDeuda(dto.getDeuda());
        if (dto.getDineroGenerado() != null)
            existente.setDineroGenerado(dto.getDineroGenerado());
        if (dto.getAsiento() != null)
            existente.setAsiento(dto.getAsiento());
        if (dto.getSillaBebe() != null)
            existente.setSillaBebe(dto.getSillaBebe());
        if (dto.getEurotaxi() != null)
            existente.setEurotaxi(dto.getEurotaxi());
    }

    public ConductorResponseDTO toResponseDTO(Conductor conductor) {
        if (conductor == null) {
            return null;
        }
        // Creamos el DTO y le asignamos los valores.
        ConductorResponseDTO dto = new ConductorResponseDTO();
        dto.setIdConductor(conductor.getIdConductor());
        dto.setNombre(conductor.getNombre());
        dto.setTelefono(conductor.getTelefono());
        dto.setDeuda(conductor.getDeuda());
        dto.setDineroGenerado(conductor.getDineroGenerado());

        // Si el valor de la entidad es nulo, le asignamos un valor por defecto (0 o
        // false).
        // Si no es nulo, usamos el valor que tiene.
        dto.setAsiento(conductor.getAsiento() != null ? conductor.getAsiento() : 0);
        dto.setSillaBebe(conductor.getSillaBebe() != null ? conductor.getSillaBebe() : 0);
        dto.setEurotaxi(conductor.getEurotaxi() != null ? conductor.getEurotaxi() : false);

        return dto;
    }
}