package com.centraltaxis.mapper;

import com.centraltaxis.dto.servicio.*;
import com.centraltaxis.model.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {

    public ServicioResponseDTO toResponse(Servicio s) {
        if (s == null)
            return null;

        ServicioResponseDTO dto = new ServicioResponseDTO();
        dto.setIdServicio(s.getIdServicio());
        dto.setOrigen(s.getOrigen());
        dto.setDestino(s.getDestino());
        dto.setNPersona(s.getNPersona());
        dto.setFecha(s.getFecha());
        dto.setRequisitos(s.getRequisitos());
        dto.setPrecio(s.getPrecio());
        dto.setPrecio10(s.getPrecio10());
        dto.setHora(s.getHora());
        dto.setEurotaxi(s.getEurotaxi() != null ? s.getEurotaxi() : false);
        dto.setMascota(s.getMascota() != null ? s.getMascota() : false);
        dto.setSilla(s.getSilla() != null ? s.getSilla() : false);
        dto.setViajeLargo(s.getViajeLargo() != null ? s.getViajeLargo() : false);

        if (s.getCliente() != null) {
            dto.setCliente(new ClienteBriefDTO(
                    s.getCliente().getIdCliente(), s.getCliente().getNombre(), s.getCliente().getTelefono()));
        }
        if (s.getConductor() != null) {
            dto.setConductor(new ConductorBriefDTO(
                    s.getConductor().getIdConductor(), s.getConductor().getNombre(), s.getConductor().getTelefono()));
        }
        return dto;
    }

    // Mapea desde un DTO de creación a una nueva entidad Servicio
    public Servicio toEntity(ServicioCreateDTO dto) {
        Servicio s = new Servicio();
        s.setOrigen(dto.getOrigen());
        s.setDestino(dto.getDestino());
        s.setNPersona(dto.getNPersona() != null ? dto.getNPersona() : 0);
        s.setFecha(dto.getFecha());
        s.setEurotaxi(dto.getEurotaxi());
        s.setMascota(dto.getMascota());
        s.setSilla(dto.getSilla());
        s.setViajeLargo(dto.getViajeLargo());
        s.setHora(dto.getHora());
        s.setRequisitos(dto.getRequisitos());
        s.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : 0.0);
        s.setPrecio10(dto.getPrecio10() != null ? dto.getPrecio10() : 0.0);
        return s;
    }

    // Aplica los cambios de un DTO de parche a una entidad existente
    public void applyPatch(ServicioPatchDTO dto, Servicio target) {
        if (dto.getOrigen() != null)
            target.setOrigen(dto.getOrigen());
        if (dto.getDestino() != null)
            target.setDestino(dto.getDestino());
        if (dto.getNPersona() != null)
            target.setNPersona(dto.getNPersona());
        if (dto.getFecha() != null)
            target.setFecha(dto.getFecha());
        if (dto.getHora() != null)
            target.setHora(dto.getHora());
        if (dto.getEurotaxi() != null)
            target.setEurotaxi(dto.getEurotaxi());
        if (dto.getMascota() != null)
            target.setMascota(dto.getMascota());
        if (dto.getSilla() != null)
            target.setSilla(dto.getSilla());
        if (dto.getViajeLargo() != null)
            target.setViajeLargo(dto.getViajeLargo());
        if (dto.getPrecio() != null)
            target.setPrecio(dto.getPrecio());
        if (dto.getPrecio10() != null)
            target.setPrecio10(dto.getPrecio10());
    }
}