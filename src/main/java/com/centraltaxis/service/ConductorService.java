package com.centraltaxis.service;

import com.centraltaxis.dto.conductor.ConductorCreateDTO;
import com.centraltaxis.dto.conductor.ConductorResponseDTO;
import com.centraltaxis.dto.conductor.ConductorUpdateDTO;
import com.centraltaxis.mapper.ConductorMapper;
// Importamos la clase necesarias para el servicio Conductor
import com.centraltaxis.model.Conductor;
// Importamos el repositorio ConductorRepository para realizar operaciones CRUD
import com.centraltaxis.repository.ConductorRepository;

import java.util.List;

// Importamos las anotaciones necesarias para el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired 
    private ConductorMapper conductorMapper;

    public ConductorResponseDTO crearConductor(ConductorCreateDTO dto) {
        Conductor conductor = conductorMapper.toEntity(dto);
        Conductor guardado = conductorRepository.save(conductor);
        return conductorMapper.toResponseDTO(guardado);
    }

    public ConductorResponseDTO actualizarConductor(int id, ConductorUpdateDTO dto) {
        Conductor conductor = conductorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));
        
        conductorMapper.updateEntity(dto, conductor); 
        Conductor actualizado = conductorRepository.save(conductor);
        return conductorMapper.toResponseDTO(actualizado);
    }

    public ConductorResponseDTO buscarConductorPorId(int id) {
        Conductor conductor = conductorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));
        return conductorMapper.toResponseDTO(conductor);
    }

    public List<ConductorResponseDTO> listarConductores() {
        return conductorRepository.findAll()
            .stream()
            .map(conductorMapper::toResponseDTO)
            .toList();
    }

    public void eliminarConductorPorId(int id) {
        Conductor conductor = conductorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));
        conductorRepository.delete(conductor);
    }
}
