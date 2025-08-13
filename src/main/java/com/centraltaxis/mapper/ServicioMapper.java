package com.centraltaxis.mapper;

import com.centraltaxis.dto.servicio.*;
import com.centraltaxis.model.Cliente;
import com.centraltaxis.model.Conductor;
import com.centraltaxis.model.Servicio;
import org.springframework.stereotype.Component;

/**
 * Mapper manual Entidad <-> DTO (sin MapStruct).
 * - El Service se encargará de: buscar/crear Cliente por teléfono y traer
 * Conductor por ID.
 * - Aquí sólo formamos entidades con datos mínimos y convertimos
 * entidad->respuesta.
 */
@Component
public class ServicioMapper {

    // --------- CREATE DTO -> ENTIDAD ---------
    public Servicio toEntity(ServicioCreateDTO dto) {
        Servicio s = new Servicio();
        s.setOrigen(dto.getOrigen());
        s.setDestino(dto.getDestino());
        s.setNPersona(dto.getNPersona() != null ? dto.getNPersona() : 0);
        s.setFecha(dto.getFecha());
        s.setEurotaxi(Boolean.TRUE.equals(dto.getEurotaxi()));
        s.setHora(dto.getHora());
        s.setRequisitos(null); // se puede setear desde el DTO si lo añades allí

        // Precio y precio10 pueden venir 0. Si precio10 es null, lo dejamos en 0.
        s.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : 0d);
        s.setPrecio10(dto.getPrecio10() != null ? dto.getPrecio10() : 0d);

        // Helpers para conductor y cliente
        s.setCliente(crearCliente(dto.getClienteNombre(), dto.getClienteTelefono()));
        s.setConductor(crearConductor(dto.getConductorId()));

        return s;
    }

    // --------- UPDATE DTO -> (MERGE EN ENTIDAD EXISTENTE) ---------
    public void mergeIntoEntity(ServicioUpdateDTO dto, Servicio target) {
        target.setOrigen(dto.getOrigen());
        target.setDestino(dto.getDestino());
        target.setNPersona(dto.getNPersona() != null ? dto.getNPersona() : 0);
        target.setFecha(dto.getFecha());
        target.setEurotaxi(Boolean.TRUE.equals(dto.getEurotaxi()));
        target.setHora(dto.getHora());
        target.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : 0d);
        target.setPrecio10(dto.getPrecio10() != null ? dto.getPrecio10() : 0d);
        target.setRequisitos(dto.getRequisitos());

        // Utilizamos helper para la creacion de el conductor y cliente
        target.setCliente(crearCliente(dto.getClienteNombre(), dto.getClienteTelefono()));
        target.setConductor(crearConductor(dto.getConductorId()));
    }

    // --------- PATCH DTO -> (APLICAR SÓLO NO-NULL) ---------
    public void applyPatch(ServicioPatchDTO dto, Servicio target) {
        if (dto.getOrigen() != null)
            target.setOrigen(dto.getOrigen());
        if (dto.getDestino() != null)
            target.setDestino(dto.getDestino());
        if (dto.getNPersona() != null)
            target.setNPersona(dto.getNPersona());
        if (dto.getFecha() != null)
            target.setFecha(dto.getFecha());
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
        } else if (target.getConductor() != null) {
            // Solo eliminamos si había un conductor antes y ahora recibimos null
            target.setConductor(null);
        }

    }

    // --------- ENTIDAD -> RESPONSE DTO ---------
    public ServicioResponseDTO toResponse(Servicio s) {
        ServicioResponseDTO dto = new ServicioResponseDTO();
        dto.setIdServicio(s.getIdServicio());
        dto.setOrigen(s.getOrigen());
        dto.setDestino(s.getDestino());
        dto.setNPersona(s.getNPersona());
        dto.setFecha(s.getFecha());
        dto.setRequisitos(s.getRequisitos());
        dto.setPrecio(s.getPrecio());
        dto.setPrecio10(s.getPrecio10());
        dto.setEurotaxi(s.isEurotaxi());
        dto.setHora(s.getHora());

        if (s.getCliente() != null) {
            dto.setCliente(new ClienteBriefDTO(
                    s.getCliente().getIdCliente(),
                    s.getCliente().getNombre(),
                    s.getCliente().getTelefono()));
        }

        if (s.getConductor() != null) {
            dto.setConductor(new ConductorBriefDTO(
                    s.getConductor().getIdConductor(),
                    s.getConductor().getNombre(),
                    s.getConductor().getTelefono()));
        }

        return dto;
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
