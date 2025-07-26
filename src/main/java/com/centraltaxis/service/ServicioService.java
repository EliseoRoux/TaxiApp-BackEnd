package com.centraltaxis.service;

import com.centraltaxis.model.Servicio;
import com.centraltaxis.repository.ServicioRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    // Método para guardar o actualizar un servicio
    public Servicio guardarServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    // Método para buscar un servicio por ID
    public Optional<Servicio> buscarServicioPorId(int id) {
        return servicioRepository.findById(id);
    }

    // Metodo para buscar todos los servicios por conductor
    public List<Servicio> buscarServiciosPorConductor(int idConductor) {
        return servicioRepository.findByConductorId(idConductor);
    }

    // Método para eliminar un servicio por ID
    public void eliminarServicioPorId(int id) {
        // Comprobamos si el servicio existe antes de eliminarlo
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
        servicioRepository.delete(servicio);
    }

    // Método para Listar todos los servicios
    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    // Método para contar el número total de servicios
    public long contarServicios() {
        return servicioRepository.count();
    }

}
