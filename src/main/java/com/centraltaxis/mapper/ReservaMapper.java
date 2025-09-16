package com.centraltaxis.mapper;

import org.springframework.stereotype.Component;
import com.centraltaxis.dto.reserva.ClienteBriefDTO;
import com.centraltaxis.dto.reserva.ConductorBriefDTO;
import com.centraltaxis.dto.reserva.ReservaCreateDTO;
import com.centraltaxis.dto.reserva.ReservaResponseDTO;
import com.centraltaxis.model.Reserva;

@Component
public class ReservaMapper {

    public ReservaResponseDTO toResponseDTO(Reserva reserva) {
        if (reserva == null)
            return null;

        ReservaResponseDTO response = new ReservaResponseDTO();
        response.setIdReserva(reserva.getIdReserva());
        response.setOrigen(reserva.getOrigen());
        response.setDestino(reserva.getDestino());
        response.setnPersona(reserva.getNPersona());
        response.setFechaReserva(reserva.getFechaReserva());
        response.setHora(reserva.getHora());
        response.setRequisitos(reserva.getRequisitos());
        response.setPrecio(reserva.getPrecio());
        response.setPrecio10(reserva.getPrecio10());
        response.setEurotaxi(reserva.getEurotaxi() != null ? reserva.getEurotaxi() : false);
        response.setMascota(reserva.getMascota() != null ? reserva.getMascota() : false);
        response.setSilla(reserva.getSilla() != null ? reserva.getSilla() : false);
        response.setViajeLargo(reserva.getViajeLargo() != null ? reserva.getViajeLargo() : false);

        if (reserva.getCliente() != null) {
            response.setCliente(new ClienteBriefDTO(
                    reserva.getCliente().getIdCliente(), reserva.getCliente().getNombre(),
                    reserva.getCliente().getTelefono()));
        }
        if (reserva.getConductor() != null) {
            response.setConductor(new ConductorBriefDTO(
                    reserva.getConductor().getIdConductor(), reserva.getConductor().getNombre(),
                    reserva.getConductor().getTelefono()));
        }
        return response;
    }

    // Mapea desde un DTO a una nueva entidad, sin crear entidades asociadas.
    public Reserva toEntity(ReservaCreateDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setOrigen(dto.getOrigen());
        reserva.setDestino(dto.getDestino());
        reserva.setNPersona(dto.getnPersona());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setHora(dto.getHora());
        reserva.setEurotaxi(dto.getEurotaxi());
        reserva.setMascota(dto.getMascota());
        reserva.setSilla(dto.getSilla());
        reserva.setViajeLargo(dto.getViajeLargo());
        reserva.setRequisitos(dto.getRequisitos());
        reserva.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : 0d);
        reserva.setPrecio10(dto.getPrecio10() != null ? dto.getPrecio10() : 0d);
        return reserva;
    }

    // Aplica los cambios de un DTO a una entidad existente.
    public void applyPatch(ReservaCreateDTO dto, Reserva target) {
        if (dto.getOrigen() != null)
            target.setOrigen(dto.getOrigen());
        if (dto.getDestino() != null)
            target.setDestino(dto.getDestino());
        if (dto.getnPersona() != null)
            target.setNPersona(dto.getnPersona());
        if (dto.getFechaReserva() != null)
            target.setFechaReserva(dto.getFechaReserva());
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