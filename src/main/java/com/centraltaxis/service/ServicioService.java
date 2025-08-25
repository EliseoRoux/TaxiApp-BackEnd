package com.centraltaxis.service;

import com.centraltaxis.model.Cliente;
import com.centraltaxis.model.Servicio;
import com.centraltaxis.model.Conductor;
import com.centraltaxis.repository.ConductorRepository;
import com.centraltaxis.repository.ServicioRepository;
import com.centraltaxis.repository.ClienteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

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

    public Servicio guardarServicio(Servicio servicio) {
        // Validar cliente obligatorio
        if (servicio.getCliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }

        String telefono = servicio.getCliente().getTelefono();
        String nombre = servicio.getCliente().getNombre();

        if (nombre == null || telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("Nombre y teléfono del cliente son obligatorios");
        }

        // Normalizar teléfono (quitar espacios)
        telefono = telefono.trim();
        servicio.getCliente().setTelefono(telefono);

        // Buscar cliente existente
        Cliente cliente = clienteRepository.findByTelefono(telefono);

        if (cliente == null) {
            // Crear nuevo cliente
            cliente = new Cliente();
            cliente.setNombre(nombre.trim());
            cliente.setTelefono(telefono);
            cliente = clienteRepository.save(cliente);
        } else {
            // Actualizar nombre si cambió
            if (!cliente.getNombre().equalsIgnoreCase(nombre.trim())) {
                cliente.setNombre(nombre.trim());
                clienteRepository.save(cliente);
            }
        }

        servicio.setCliente(cliente);

        // Verificar conductor si se asigna
        if (servicio.getConductor() != null) {
            Conductor conductor = conductorRepository.findById(servicio.getConductor().getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
            servicio.setConductor(conductor);
        } else {
            servicio.setConductor(null);
        }

        // Calcular precio10
        servicio.setPrecio10(servicio.getPrecio() * 0.10);

        // Guardar servicio
        return servicioRepository.save(servicio);
    }

    public Servicio buscarServicioPorId(int id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
    }

    @Transactional
    public Servicio actualizarServicio(int id, Servicio servicioActualizado) {
        Servicio existente = buscarServicioPorId(id);

        existente.setConductor(servicioActualizado.getConductor());
        existente.setCliente(servicioActualizado.getCliente());
        existente.setOrigen(servicioActualizado.getOrigen());
        existente.setDestino(servicioActualizado.getDestino());
        existente.setNPersona(servicioActualizado.getNPersona());
        existente.setRequisitos(servicioActualizado.getRequisitos());
        existente.setPrecio(servicioActualizado.getPrecio());
        existente.setPrecio10(servicioActualizado.getPrecio10());
        existente.setEurotaxi(servicioActualizado.isEurotaxi());
        existente.setFecha(servicioActualizado.getFecha());
        existente.setHora(servicioActualizado.getHora());

        if (existente.getConductor() != null) {
            Conductor conductor = conductorRepository.findById(existente.getConductor().getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
            existente.setConductor(conductor);
        }

        return servicioRepository.save(existente);
    }

    @Transactional
    public Servicio actualizarServicioParcialmente(int id, Map<String, Object> updates) {
        Servicio servicioExistente = buscarServicioPorId(id);

        // Manejar conductorId directo
        if (updates.containsKey("conductorId")) {
            Object conductorIdObj = updates.get("conductorId");
            if (conductorIdObj == null) {
                servicioExistente.setConductor(null);
            } else {
                Integer idConductor = (Integer) conductorIdObj;
                Conductor conductorNuevo = conductorRepository.findById(idConductor)
                        .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + idConductor));
                servicioExistente.setConductor(conductorNuevo);
            }
        }

        actualizarCampoSiExiste(updates, "origen", String.class, servicioExistente::setOrigen);
        actualizarCampoSiExiste(updates, "destino", String.class, servicioExistente::setDestino);
        actualizarCampoSiExiste(updates, "requisitos", String.class, servicioExistente::setRequisitos);
        actualizarCampoSiExiste(updates, "precio", Double.class, servicioExistente::setPrecio);
        actualizarCampoSiExiste(updates, "precio10", Double.class, servicioExistente::setPrecio10);
        actualizarCampoSiExiste(updates, "eurotaxi", Boolean.class, servicioExistente::setEurotaxi);
        actualizarCampoSiExiste(updates, "nPersona", Integer.class, servicioExistente::setNPersona);
        actualizarCampoSiExiste(updates, "fecha", LocalDate.class, servicioExistente::setFecha);
        actualizarCampoSiExiste(updates, "hora", LocalTime.class, servicioExistente::setHora);

        return servicioRepository.save(servicioExistente);
    }

    public void eliminarServicioPorId(int id) {
        Servicio servicio = buscarServicioPorId(id);
        servicioRepository.delete(servicio);
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

    public long contarServicios() {
        return servicioRepository.count();
    }

    private <T> void actualizarCampoSiExiste(Map<String, Object> updates, String campo, Class<T> tipo,
            java.util.function.Consumer<T> setter) {
        if (updates.containsKey(campo)) {
            Object valor = updates.get(campo);
            if (tipo.isInstance(valor)) {
                setter.accept(tipo.cast(valor));
            }
        }
    }
}
