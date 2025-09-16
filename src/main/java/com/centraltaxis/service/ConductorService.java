package com.centraltaxis.service;

import com.centraltaxis.dto.conductor.ConductorCreateDTO;
import com.centraltaxis.dto.conductor.ConductorResponseDTO;
import com.centraltaxis.dto.conductor.ConductorUpdateDTO;
import com.centraltaxis.mapper.ConductorMapper;
// Importamos la clase necesarias para el servicio Conductor
import com.centraltaxis.model.Conductor;
import com.centraltaxis.model.Reserva;
import com.centraltaxis.model.Servicio;
// Importamos el repositorio ConductorRepository para realizar operaciones CRUD
import com.centraltaxis.repository.ConductorRepository;
import com.centraltaxis.repository.ReservaRepository;
import com.centraltaxis.repository.ServicioRepository;

import java.util.List;

import javax.transaction.Transactional;

// Importamos las anotaciones necesarias para el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

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

    /**
     * Elimina un conductor por su ID.
     * Antes de eliminarlo, busca todas sus reservas asociadas y
     * las desvincula (les pone el conductor a null).
     */
    @Transactional
    public void eliminarConductorPorId(int id) {
        if (!conductorRepository.existsById(id)) {
            throw new RuntimeException("Conductor no encontrado con ID: " + id);
        }

        // --- Lógica para Reservas ---
        List<Reserva> reservasDelConductor = reservaRepository.findByConductor_IdConductor(id);
        for (Reserva reserva : reservasDelConductor) {
            reserva.setConductor(null);
        }
        reservaRepository.saveAll(reservasDelConductor);

        // Busca todos los servicios asociados al conductor
        List<Servicio> serviciosDelConductor = servicioRepository.findByConductor_IdConductor(id);

        // Desvincula el conductor de cada servicio
        for (Servicio servicio : serviciosDelConductor) {
            servicio.setConductor(null);
        }
        servicioRepository.saveAll(serviciosDelConductor); // Guarda los cambios

        // elimina al conductor
        conductorRepository.deleteById(id);
    }

    public List<ConductorResponseDTO> listarConductoresConDeuda() {
        return conductorRepository.findConductoresConDeuda()
                .stream()
                .map(conductorMapper::toResponseDTO)
                .toList();
    }

    public List<ConductorResponseDTO> conductoresSinDeuda() {
        return conductorRepository.findConductoresSinDeuda()
                .stream()
                .map(conductorMapper::toResponseDTO)
                .toList();
    }

    public ConductorResponseDTO pagarDeuda(int id) {
        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));

        conductor.setDeuda(0.0);
        Conductor actualizado = conductorRepository.save(conductor);
        return conductorMapper.toResponseDTO(actualizado);
    }

}
