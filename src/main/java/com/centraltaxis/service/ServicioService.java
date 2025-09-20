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
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
// Importamos la clase Logger para registrar eventos
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ServicioService {
    // Instancia estática del Logger para esta clase
    private static final Logger log = LoggerFactory.getLogger(ServicioService.class);

    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConductorRepository conductorRepository;
    @Autowired
    private ServicioMapper servicioMapper;

    // --- LÓGICA DE DEUDA ---
    @Transactional
    private void actualizarCuentasConductor(Conductor conductor, Double precio, Double precio10, boolean esSuma) {
        if (conductor == null) {
            return;
        }
        log.debug("Actualizando cuentas del conductor ID: {}. Es suma: {}", conductor.getIdConductor(), esSuma);

        double valorPrecio = (precio != null) ? precio : 0.0;
        double valorPrecio10 = (precio10 != null) ? precio10 : 0.0;

        double deudaActual = conductor.getDeuda() != null ? conductor.getDeuda() : 0.0;
        double dineroGeneradoActual = conductor.getDineroGenerado() != null ? conductor.getDineroGenerado() : 0.0;

        if (esSuma) {
            conductor.setDeuda(deudaActual + valorPrecio10); // La deuda es con el 10%
            conductor.setDineroGenerado(dineroGeneradoActual + valorPrecio); // El dinero generado es el precio base
        } else {
            conductor.setDeuda(deudaActual - valorPrecio10);
            conductor.setDineroGenerado(dineroGeneradoActual - valorPrecio);
        }
        conductorRepository.save(conductor);
        log.debug("Cuentas actualizadas. Nueva deuda: {}, Nuevo dinero generado: {}",
                conductor.getDeuda(), conductor.getDineroGenerado());
    }

    @Transactional
    public Servicio crearServicio(ServicioCreateDTO dto) {
        log.info("Iniciando la creación de un servicio para el cliente: {}", dto.getClienteNombre());

        try {
            Servicio servicio = servicioMapper.toEntity(dto);
            Cliente cliente = gestionarCliente(dto.getClienteNombre(), dto.getClienteTelefono());
            servicio.setCliente(cliente);

            if (dto.getIdConductor() != null) {
                Conductor conductor = conductorRepository.findById(dto.getIdConductor())
                        .orElseThrow(
                                () -> new RuntimeException("Conductor no encontrado con ID: " + dto.getIdConductor()));
                servicio.setConductor(conductor);
                actualizarCuentasConductor(conductor, servicio.getPrecio(), servicio.getPrecio10(), true);
            }
            Servicio nuevoServicio = servicioRepository.save(servicio);
            log.info("Servicio creado exitosamente con ID: {}", nuevoServicio.getIdServicio());
            return nuevoServicio;
        } catch (Exception e) {
            log.error("Error al crear el servicio: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Servicio actualizarServicioParcialmente(int id, ServicioPatchDTO dto) {
        log.info("Iniciando la actualización parcial del servicio con ID: {}", id);

        log.debug("Datos recibidos para la actualización: {}", dto.toString());

        Servicio servicioExistente = buscarServicioPorId(id);

        Conductor conductorAnterior = servicioExistente.getConductor();
        Double precioAnterior = servicioExistente.getPrecio();
        Double precio10Anterior = servicioExistente.getPrecio10();

        servicioMapper.applyPatch(dto, servicioExistente);

        if (dto.getClienteNombre() != null && dto.getClienteTelefono() != null) {
            Cliente cliente = gestionarCliente(dto.getClienteNombre(), dto.getClienteTelefono());
            servicioExistente.setCliente(cliente);
        }

        Integer idConductorNuevo = dto.getIdConductor();
        Conductor conductorNuevo = (idConductorNuevo != null && idConductorNuevo != 0)
                ? conductorRepository.findById(idConductorNuevo)
                        .orElseThrow(() -> new RuntimeException("Conductor no encontrado"))
                : (idConductorNuevo != null && idConductorNuevo == 0) ? null : conductorAnterior;

        servicioExistente.setConductor(conductorNuevo);

        boolean conductorCambio = !Objects.equals(conductorAnterior, conductorNuevo);
        boolean precioCambio = !Objects.equals(precioAnterior, servicioExistente.getPrecio());

        if (conductorCambio) {
            if (conductorAnterior != null) {
                actualizarCuentasConductor(conductorAnterior, precioAnterior, precio10Anterior, false);
            }
            if (conductorNuevo != null) {
                actualizarCuentasConductor(conductorNuevo, servicioExistente.getPrecio(),
                        servicioExistente.getPrecio10(), true);
            }
        } else if (precioCambio) {
            if (conductorNuevo != null) {
                double diffPrecio = servicioExistente.getPrecio() - precioAnterior;
                double diffPrecio10 = servicioExistente.getPrecio10() - precio10Anterior;
                actualizarCuentasConductor(conductorNuevo, diffPrecio, diffPrecio10, true);
            }
        }
        Servicio servicioActualizado = servicioRepository.save(servicioExistente);
        log.info("Servicio con ID: {} actualizado exitosamente.", id);
        return servicioActualizado;
    }

    @Transactional
    public void eliminarServicioPorId(int id) {
        log.info("Iniciando la eliminación del servicio con ID: {}", id);
        Servicio servicio = buscarServicioPorId(id);

        if (servicio.getConductor() != null) {
            actualizarCuentasConductor(servicio.getConductor(), servicio.getPrecio(), servicio.getPrecio10(), false);
        } else {
            log.warn("El servicio con ID: {} no tiene un conductor asociado.", id);
        }

        servicioRepository.deleteById(id);
        log.info("Servicio con ID: {} eliminado exitosamente.", id);
    }

    @Transactional
    private Cliente gestionarCliente(String nombre, String telefono) {
        if (telefono == null || nombre == null) {
            throw new IllegalArgumentException("Nombre y teléfono del cliente son obligatorios.");
        }
        Cliente cliente = clienteRepository.findByTelefono(telefono.trim());
        if (cliente == null) {
            cliente = new Cliente();
            cliente.setNombre(nombre.trim());
            cliente.setTelefono(telefono.trim());
            cliente.setFechaCreacion(LocalDateTime.now());
            cliente.setFechaActualizacion(LocalDateTime.now());
        } else {
            if (!cliente.getNombre().equalsIgnoreCase(nombre.trim())) {
                cliente.setNombre(nombre.trim());
                cliente.setFechaActualizacion(LocalDateTime.now());
            }
        }
        return clienteRepository.save(cliente);
    }

    public Servicio buscarServicioPorId(int id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
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
