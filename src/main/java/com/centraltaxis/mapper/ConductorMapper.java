package com.centraltaxis.mapper;

import com.centraltaxis.dto.conductor.ConductorCreateDTO;
import com.centraltaxis.dto.conductor.ConductorResponseDTO;
import com.centraltaxis.dto.conductor.ConductorUpdateDTO;
import com.centraltaxis.model.Conductor;
import org.springframework.stereotype.Component;

@Component
public class ConductorMapper {

    /**
     * DTO → Entidad (al crear un conductor)
     * - No seteamos el ID porque la DB lo genera sola.
     * - Deuda y dinero generado empiezan en 0.0 por defecto.
     */
    public Conductor toEntity(ConductorCreateDTO dto) {
        Conductor conductor = new Conductor();
        conductor.setNombre(dto.getNombre());
        conductor.setTelefono(dto.getTelefono());
        conductor.setDeuda(0.0);
        conductor.setDineroGenerado(0.0);
        conductor.setAsiento(dto.getAsiento() != null ? dto.getAsiento() : 0);
        conductor.setSillaBebe(dto.getSillaBebe() != null ? dto.getSillaBebe() : 0);
        conductor.setEurotaxi(Boolean.TRUE.equals(dto.isEurotaxi()));
        return conductor;
    }

    /**
     * DTO → Entidad (al actualizar un conductor existente)
     * - Aquí recibimos la entidad existente y solo actualizamos campos
     * que vengan en el DTO.
     */
    public void updateEntity(ConductorUpdateDTO dto, Conductor existente) {
        if (dto.getNombre() != null) {
            existente.setNombre(dto.getNombre());
        }
        if (dto.getTelefono() != null) {
            existente.setTelefono(dto.getTelefono());
        }
        if (dto.getDeuda() != null) {
            existente.setDeuda(dto.getDeuda());
        }
        if (dto.getDineroGenerado() != null) {
            existente.setDineroGenerado(dto.getDineroGenerado());
        }
        if (dto.getAsiento() != null) {
            existente.setAsiento(dto.getAsiento());
        }
        if (dto.getSillaBebe() != null) {
            existente.setSillaBebe(dto.getSillaBebe());
        }
        if (dto.getEurotaxi() != null) {
            existente.setEurotaxi(Boolean.TRUE.equals(dto.getEurotaxi()));
        }

    }

    /**
     * Entidad → DTO (para devolver al frontend)
     */
    public ConductorResponseDTO toResponseDTO(Conductor conductor) {
        return new ConductorResponseDTO(
                conductor.getIdConductor(),
                conductor.getNombre(),
                conductor.getTelefono(),
                conductor.getDeuda(),
                conductor.getDineroGenerado(),
                conductor.getAsiento(),
                conductor.getSillaBebe(),
                conductor.isEurotaxi());
    }
}
