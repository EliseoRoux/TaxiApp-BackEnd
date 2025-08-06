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
        // Buscamos el cliente por el telefono
        Cliente clienteExistente = clienteRepository.findByTelefono(servicio.getCliente().getTelefono());
        // Si el cliente no existe, creamos un nuevo cliente
        if (clienteExistente == null) {
            Cliente nuevoCliente = new Cliente();
            // Asignamos los datos del servicio al nuevo cliente
            nuevoCliente.setNombre(servicio.getCliente().getNombre());
            nuevoCliente.setTelefono(servicio.getCliente().getTelefono());
            // Guardamos el nuevo cliente
            nuevoCliente = clienteRepository.save(nuevoCliente);
            // Asignamos el nuevo cliente al servicio
            servicio.setCliente(nuevoCliente);
        } else {
            // Si existe lo asignamos a el servicio
            servicio.setCliente(clienteExistente);
        }

        // Ahora buscamos si el conductor lo han asignado o es null
        // Si lo han asignado lo insertamos al servicio
        if (servicio.getConductor() != null) {
            // Ya que no es null lo buscamos
            Conductor conductor = conductorRepository.findById(servicio.getConductor().getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
                // Y lo asignamos al servicio
                servicio.setConductor(conductor);
        }
        // Guardamos el servicio
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
