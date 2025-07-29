package com.centraltaxis.service;

import com.centraltaxis.model.Cliente;
import com.centraltaxis.model.Servicio;
import com.centraltaxis.model.Conductor;
import com.centraltaxis.repository.ConductorRepository;
import com.centraltaxis.repository.ServicioRepository;
import com.centraltaxis.repository.ClienteRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConductorRepository conductorRepository;

    // Método para guardar o actualizar un servicio
    public Servicio guardarServicio(Servicio servicio) {
        // Valida y asigna el cliente
        Cliente cliente = clienteRepository.findById(servicio.getCliente().getIdCliente())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        servicio.setCliente(cliente);
        // Si el conductor es nulo, lo dejamos como null
        if (servicio.getConductor() != null) {
            // Si el conductor no es nulo, buscamos el conductor por ID
            // y lo asignamos al servicio
            Conductor conductor = conductorRepository.findById(servicio.getConductor().getIdConductor())
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
            servicio.setConductor(conductor);
        } else {
            servicio.setConductor(null);
        }
        return servicioRepository.save(servicio);
    }
    

    // Método para buscar un servicio por ID
    public Servicio buscarServicioPorId(int id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
    }

    // Metodo para buscar todos los servicios por conductor
    public List<Servicio> buscarServiciosPorConductor(int idConductor) {
        return servicioRepository.findByConductor_IdConductor(idConductor);
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
