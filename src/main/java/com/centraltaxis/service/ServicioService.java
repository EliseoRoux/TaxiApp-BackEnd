package com.centraltaxis.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centraltaxis.dto.servicio.ServicioCreateDTO;
import com.centraltaxis.dto.servicio.ServicioPatchDTO;
import com.centraltaxis.mapper.ServicioMapper;
import com.centraltaxis.model.Cliente;
import com.centraltaxis.model.Conductor;
import com.centraltaxis.model.Servicio;
import com.centraltaxis.repository.ClienteRepository;
import com.centraltaxis.repository.ConductorRepository; // Importa LocalDateTime
import com.centraltaxis.repository.ServicioRepository;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConductorRepository conductorRepository;
    @Autowired
    private ServicioMapper servicioMapper;

    @Transactional
    public Servicio crearServicio(ServicioCreateDTO dto) {
        Servicio servicio = servicioMapper.toEntity(dto);
        Cliente cliente = gestionarCliente(dto.getClienteNombre(), dto.getClienteTelefono());
        servicio.setCliente(cliente);

        if (dto.getIdConductor() != null) {
            Conductor conductor = conductorRepository.findById(dto.getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + dto.getIdConductor()));
            servicio.setConductor(conductor);
        }
        return servicioRepository.save(servicio);
    }

    @Transactional
    public Servicio actualizarServicioParcialmente(int id, ServicioPatchDTO dto) {
        Servicio servicioExistente = buscarServicioPorId(id);
        servicioMapper.applyPatch(dto, servicioExistente);

        if (dto.getClienteNombre() != null && dto.getClienteTelefono() != null) {
            Cliente cliente = gestionarCliente(dto.getClienteNombre(), dto.getClienteTelefono());
            servicioExistente.setCliente(cliente);
        }

        if (dto.getIdConductor() != null) {
            if (dto.getIdConductor() == 0) {
                servicioExistente.setConductor(null);
            } else {
                Conductor conductor = conductorRepository.findById(dto.getIdConductor())
                        .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + dto.getIdConductor()));
                servicioExistente.setConductor(conductor);
            }
        }
        return servicioRepository.save(servicioExistente);
    }

    // --- MÉTODO GESTIONAR CLIENTE ---
    private Cliente gestionarCliente(String nombre, String telefono) {
        if (telefono == null || nombre == null) {
            throw new IllegalArgumentException("Nombre y teléfono del cliente son obligatorios.");
        }

        Cliente cliente = clienteRepository.findByTelefono(telefono.trim());

        if (cliente == null) {
            // Si el cliente es nuevo, asignamos ambas fechas.
            cliente = new Cliente();
            cliente.setNombre(nombre.trim());
            cliente.setTelefono(telefono.trim());
            cliente.setFechaCreacion(LocalDateTime.now()); 
            cliente.setFechaActualizacion(LocalDateTime.now()); 
            return clienteRepository.save(cliente);
        } else {
            // Si el cliente existe y el nombre ha cambiado, actualizamos la fecha.
            if (!cliente.getNombre().equalsIgnoreCase(nombre.trim())) {
                cliente.setNombre(nombre.trim());
                cliente.setFechaActualizacion(LocalDateTime.now()); 
                return clienteRepository.save(cliente);
            }
        }
        return cliente;
    }

    // --- MÉTODOS DE BÚSQUEDA Y ELIMINACIÓN  ---
    public Servicio buscarServicioPorId(int id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
    }

    public void eliminarServicioPorId(int id) {
        if (!servicioRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado con ID: " + id);
        }
        servicioRepository.deleteById(id);
    }

    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    public List<Servicio> buscarServiciosPorConductor(int idConductor) {
        return servicioRepository.findByConductor_IdConductor(idConductor);
    }

    public List<Servicio> buscarServiciosPorConductorYFechas(
            int idConductor, LocalDate fechaInicio, LocalDate fechaFin) {
        return servicioRepository.findByConductorAndFechaRango(idConductor, fechaInicio, fechaFin);
    }
}
