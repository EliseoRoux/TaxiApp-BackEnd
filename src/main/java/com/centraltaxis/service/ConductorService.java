package com.centraltaxis.service;

// Importamos la clase necesarias para el servicio Conductor
import com.centraltaxis.model.Conductor;
// Importamos el repositorio ConductorRepository para realizar operaciones CRUD
import com.centraltaxis.repository.ConductorRepository;

import java.util.List;
import java.util.Optional;

// Importamos las anotaciones necesarias para el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;

    // Metodo para guardar o actualizar un conductor
    public Conductor guardarConductor(Conductor conductor) {
        return conductorRepository.save(conductor);
    }

    // Metodo para borrar un conductor por ID
    public void eliminarConductorPorId(int id) {
        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));
        conductorRepository.delete(conductor);
    }

    // Metodo para listar todos los conductores
    public List<Conductor> listarConductores() {
        return conductorRepository.findAll();
    }

    // Metodo para buscar un conductor por ID
    public Optional<Conductor> buscarConductorPorId(int id) {
        return conductorRepository.findById(id);
    }

}
