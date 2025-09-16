package com.centraltaxis.service;

import com.centraltaxis.dto.servicio.ServicioCreateDTO;
import com.centraltaxis.dto.servicio.ServicioPatchDTO;
import com.centraltaxis.mapper.ServicioMapper;
import com.centraltaxis.model.Cliente;
import com.centraltaxis.model.Conductor;
import com.centraltaxis.model.Servicio;
import com.centraltaxis.repository.ClienteRepository;
import com.centraltaxis.repository.ConductorRepository;
import com.centraltaxis.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

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
        // Mapea los datos básicos del DTO a una nueva entidad
        Servicio servicio = servicioMapper.toEntity(dto);

        // Busca y asigna el cliente (obligatorio)
        Cliente cliente = gestionarCliente(dto.getClienteNombre(), dto.getClienteTelefono());
        servicio.setCliente(cliente);

        // Busca y asigna el conductor (opcional)
        if (dto.getIdConductor() != null) {
            Conductor conductor = conductorRepository.findById(dto.getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + dto.getIdConductor()));
            servicio.setConductor(conductor);
        }

        // Guarda el servicio con las relaciones correctas
        return servicioRepository.save(servicio);
    }

    @Transactional
    public Servicio actualizarServicioParcialmente(int id, ServicioPatchDTO dto) {
        // Obtiene la entidad gestionada por JPA
        Servicio servicioExistente = buscarServicioPorId(id);

        // Aplica los cambios de los campos simples (origen, destino, etc.)
        servicioMapper.applyPatch(dto, servicioExistente);

        // Si el DTO incluye información del cliente, lo gestiona
        if (dto.getClienteNombre() != null && dto.getClienteTelefono() != null) {
            Cliente cliente = gestionarCliente(dto.getClienteNombre(), dto.getClienteTelefono());
            servicioExistente.setCliente(cliente);
        }

        // Si el DTO incluye un ID de conductor, lo asigna o desasigna
        if (dto.getIdConductor() != null) {
            if (dto.getIdConductor() == 0) { // Un valor como 0 puede significar "sin asignar"
                servicioExistente.setConductor(null);
            } else {
                Conductor conductor = conductorRepository.findById(dto.getIdConductor())
                        .orElseThrow(
                                () -> new RuntimeException("Conductor no encontrado con ID: " + dto.getIdConductor()));
                servicioExistente.setConductor(conductor);
            }
        }

        // Guarda la entidad actualizada
        return servicioRepository.save(servicioExistente);
    }

    private Cliente gestionarCliente(String nombre, String telefono) {
        if (telefono == null || nombre == null) {
            throw new IllegalArgumentException("Nombre y teléfono del cliente son obligatorios.");
        }
        Cliente cliente = clienteRepository.findByTelefono(telefono.trim());
        if (cliente == null) {
            cliente = new Cliente();
            cliente.setNombre(nombre.trim());
            cliente.setTelefono(telefono.trim());
            return clienteRepository.save(cliente);
        } else {
            if (!cliente.getNombre().equalsIgnoreCase(nombre.trim())) {
                cliente.setNombre(nombre.trim());
                return clienteRepository.save(cliente);
            }
        }
        return cliente;
    }

    // --- MÉTODOS DE BÚSQUEDA Y ELIMINACIÓN (SIN CAMBIOS) ---
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