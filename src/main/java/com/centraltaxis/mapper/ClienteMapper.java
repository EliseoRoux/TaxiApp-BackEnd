package com.centraltaxis.mapper;

import com.centraltaxis.dto.cliente.ClienteCreateDTO;
import com.centraltaxis.dto.cliente.ClienteResponseDTO;
import com.centraltaxis.dto.cliente.ClienteUpdateDTO;
import com.centraltaxis.model.Cliente;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    // Convertir CreateDTO a Entidad
    public Cliente toEntity(ClienteCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre().trim());
        cliente.setTelefono(dto.getTelefono().trim());
        return cliente;
    }

    // Convertir UpdateDTO a Entidad existente
    public Cliente toEntity(ClienteUpdateDTO dto, Cliente existingCliente) {
        if (dto == null || existingCliente == null) {
            return existingCliente;
        }

        if (dto.getNombre() != null) {
            existingCliente.setNombre(dto.getNombre().trim());
        }
        if (dto.getTelefono() != null) {
            existingCliente.setTelefono(dto.getTelefono().trim());
        }

        return existingCliente;
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

    public ClienteResponseDTO toDetailedResponseDTO(Cliente cliente, Integer totalServicios) {
        ClienteResponseDTO dto = toResponseDTO(cliente);
        dto.setTotalServicios(totalServicios);
        return dto;
    }
}