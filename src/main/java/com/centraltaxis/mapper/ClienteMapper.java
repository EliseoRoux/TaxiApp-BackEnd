package com.centraltaxis.mapper;

import com.centraltaxis.dto.cliente.ClienteCreateDTO;
import com.centraltaxis.dto.cliente.ClienteResponseDTO;
import com.centraltaxis.dto.cliente.ClienteUpdateDTO;
import com.centraltaxis.model.Cliente;

import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    // Convertir CreateDTO a una nueva Entidad
    public Cliente toEntity(ClienteCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre().trim());
        cliente.setTelefono(dto.getTelefono().trim());
        // Las fechas se asignan en el servicio
        return cliente;
    }

    // Aplicar cambios de un UpdateDTO a una Entidad existente
    public void updateEntityFromDTO(ClienteUpdateDTO dto, Cliente existingCliente) {
        if (dto == null || existingCliente == null) {
            return;
        }
        if (dto.getNombre() != null && !dto.getNombre().isEmpty()) {
            existingCliente.setNombre(dto.getNombre().trim());
        }
        if (dto.getTelefono() != null && !dto.getTelefono().isEmpty()) {
            existingCliente.setTelefono(dto.getTelefono().trim());
        }
        // Las fechas se actualizan en el servicio
    }

    // Convertir Entidad a ResponseDTO 
    public ClienteResponseDTO toResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setTelefono(cliente.getTelefono());
        dto.setFechaCreacion(cliente.getFechaCreacion());
        dto.setFechaActualizacion(cliente.getFechaActualizacion());
        return dto;
    }
}