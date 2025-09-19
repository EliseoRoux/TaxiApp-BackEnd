package com.centraltaxis.mapper;

import org.springframework.stereotype.Component;

import com.centraltaxis.dto.servicio.ClienteBriefDTO;
import com.centraltaxis.dto.servicio.ConductorBriefDTO;
import com.centraltaxis.dto.servicio.ServicioCreateDTO;
import com.centraltaxis.dto.servicio.ServicioPatchDTO;
import com.centraltaxis.dto.servicio.ServicioResponseDTO;
import com.centraltaxis.model.Servicio;

@Component
public class ServicioMapper {

    public ServicioResponseDTO toResponse(Servicio s) {
        if (s == null) {
            return null;
        }

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

        // Boolean.TRUE.equals(null) devuelve false.
        dto.setEurotaxi(Boolean.TRUE.equals(s.getEurotaxi()));
        dto.setMascota(Boolean.TRUE.equals(s.getMascota()));
        dto.setSilla(Boolean.TRUE.equals(s.getSilla()));
        dto.setViajeLargo(Boolean.TRUE.equals(s.getViajeLargo()));

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

    public Servicio toEntity(ServicioCreateDTO dto) {
        Servicio s = new Servicio();
        s.setOrigen(dto.getOrigen());
        s.setDestino(dto.getDestino());
        s.setFecha(dto.getFecha());
        s.setHora(dto.getHora());
        s.setRequisitos(dto.getRequisitos());

        // Para Integer y Double, comprobamos el null y asignamos.
        if (dto.getNPersona() != null) {
            s.setNPersona(dto.getNPersona());
        } else {
            s.setNPersona(0);
        }

        if (dto.getPrecio() != null) {
            s.setPrecio(dto.getPrecio());
        } else {
            s.setPrecio(0.0);
        }

        if (dto.getPrecio10() != null) {
            s.setPrecio10(dto.getPrecio10());
        } else {
            s.setPrecio10(0.0);
        }

        s.setEurotaxi(Boolean.TRUE.equals(dto.getEurotaxi()));
        s.setMascota(Boolean.TRUE.equals(dto.getMascota()));
        s.setSilla(Boolean.TRUE.equals(dto.getSilla()));
        s.setViajeLargo(Boolean.TRUE.equals(dto.getViajeLargo()));

        return s;
    }

    public void applyPatch(ServicioPatchDTO dto, Servicio target) {
        // En este método, solo actualizamos si el valor en el DTO no es nulo.
        if (dto.getOrigen() != null) {
            target.setOrigen(dto.getOrigen());
        }
        if (dto.getDestino() != null) {
            target.setDestino(dto.getDestino());
        }
        if (dto.getNPersona() != null) {
            target.setNPersona(dto.getNPersona());
        }
        if (dto.getFecha() != null) {
            target.setFecha(dto.getFecha());
        }
        if (dto.getHora() != null) {
            target.setHora(dto.getHora());
        }
        if (dto.getEurotaxi() != null) {
            target.setEurotaxi(dto.getEurotaxi());
        }
        if (dto.getMascota() != null) {
            target.setMascota(dto.getMascota());
        }
        if (dto.getSilla() != null) {
            target.setSilla(dto.getSilla());
        }
        if (dto.getViajeLargo() != null) {
            target.setViajeLargo(dto.getViajeLargo());
        }
        if (dto.getPrecio() != null) {
            target.setPrecio(dto.getPrecio());
        }
        if (dto.getPrecio10() != null) {
            target.setPrecio10(dto.getPrecio10());
        }
    }
}
