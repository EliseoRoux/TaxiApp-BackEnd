package com.centraltaxis.mapper;

import org.springframework.stereotype.Component;

import com.centraltaxis.dto.reserva.ClienteBriefDTO;
import com.centraltaxis.dto.reserva.ConductorBriefDTO;
import com.centraltaxis.dto.reserva.ReservaCreateDTO;
import com.centraltaxis.dto.reserva.ReservaResponseDTO;
import com.centraltaxis.model.Cliente;
import com.centraltaxis.model.Conductor;
import com.centraltaxis.model.Reserva;

@Component
public class ReservaMapper {

    public Reserva toEntity(ReservaCreateDTO dto) {
        Reserva reserva = new Reserva();
        reserva.setOrigen(dto.getOrigen());
        reserva.setDestino(dto.getDestino());
        reserva.setNPersona(dto.getnPersona());
        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setHora(dto.getHora());
        reserva.setEurotaxi(dto.getEurotaxi());
        reserva.setRequisitos(dto.getRequisitos());

        // Precio puede ser null, así que lo dejamos como está
        reserva.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : 0d);
        reserva.setPrecio10(dto.getPrecio10() != null ? dto.getPrecio10() : 0d);

        // Utilizamos helper para la creacion de el conductor y cliente
        reserva.setCliente(crearCliente(dto.getClienteNombre(), dto.getClienteTelefono()));
        reserva.setConductor(crearConductor(dto.getConductorId()));

        return reserva;
    }

    public void ReservaUpdateDTO(ReservaCreateDTO dto, Reserva target) {
        target.setOrigen(dto.getOrigen());
        target.setDestino(dto.getDestino());
        target.setNPersona(dto.getnPersona());
        target.setFechaReserva(dto.getFechaReserva());
        target.setHora(dto.getHora());
        target.setEurotaxi(dto.getEurotaxi());
        target.setRequisitos(dto.getRequisitos());

        // Precio puede ser null, así que lo dejamos como está
        target.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : 0d);
        target.setPrecio10(dto.getPrecio10() != null ? dto.getPrecio10() : 0d);

        // Utilizamos helper para la creacion de el conductor y cliente
        target.setCliente(crearCliente(dto.getClienteNombre(), dto.getClienteTelefono()));
        target.setConductor(crearConductor(dto.getConductorId()));
    }

    public void applyPatch(ReservaCreateDTO dto, Reserva target) {
        if (dto.getOrigen() != null)
            target.setOrigen(dto.getOrigen());
        if (dto.getDestino() != null)
            target.setDestino(dto.getDestino());
        if (dto.getnPersona() != null)
            target.setNPersona(dto.getnPersona());
        if (dto.getFechaReserva() != null)
            target.setFechaReserva(dto.getFechaReserva());
        if (dto.getEurotaxi() != null)
            target.setEurotaxi(dto.getEurotaxi());
        if (dto.getHora() != null)
            target.setHora(dto.getHora());
        if (dto.getPrecio() != null)
            target.setPrecio(dto.getPrecio());
        if (dto.getPrecio10() != null)
            target.setPrecio10(dto.getPrecio10());

        // Cliente
        if (dto.getClienteNombre() != null || dto.getClienteTelefono() != null) {
            target.setCliente(crearCliente(dto.getClienteNombre(), dto.getClienteTelefono()));
        }

        // Conductor
        if (dto.getConductorId() != null) {
            target.setConductor(crearConductor(dto.getConductorId()));
        }
    }

    public ReservaResponseDTO toResponseDTO(Reserva reserva) {
        ReservaResponseDTO response = new ReservaResponseDTO();
        response.setOrigen(reserva.getOrigen());
        response.setDestino(reserva.getDestino());
        response.setnPersona(reserva.getNPersona());
        response.setFechaReserva(reserva.getFechaReserva());
        response.setHora(reserva.getHora());
        response.setEurotaxi(reserva.isEurotaxi());
        response.setRequisitos(reserva.getRequisitos());
        response.setPrecio(reserva.getPrecio());
        response.setPrecio10(reserva.getPrecio10());

        if (reserva.getCliente() != null) {
            response.setCliente(new ClienteBriefDTO(
                    reserva.getCliente().getIdCliente(),
                    reserva.getCliente().getNombre(),
                    reserva.getCliente().getTelefono()));
        }

        if (reserva.getConductor() != null) {
            response.setConductor(new ConductorBriefDTO(
                    reserva.getConductor().getIdConductor(),
                    reserva.getConductor().getNombre(),
                    reserva.getConductor().getTelefono()));
        }

        return response;
    }

    // ------------------------- HELPERS ----------------------

    // Helper para crear un cliente
    private Cliente crearCliente(String nombre, String telefono) {
        if (nombre == null && telefono == null) {
            return null;
        }

        // Ambos campos son obligatorios para crear un cliente
        if (nombre == null || telefono == null) {
            throw new IllegalArgumentException("Nombre y teléfono del cliente son obligatorios");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        return cliente;
    }

    // Helper para crear un conductor
    private Conductor crearConductor(Integer idConductor) {
        if (idConductor == null) {
            return null; // No asignamos conductor si es null
        }

        // Creamos conductor
        Conductor conductor = new Conductor();
        conductor.setIdConductor(idConductor);
        return conductor;
    }
}
