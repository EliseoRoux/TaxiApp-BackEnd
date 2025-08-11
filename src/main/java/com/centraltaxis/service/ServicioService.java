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

    @Transactional
    // Método para guardar un servicio
    public Servicio guardarServicio(Servicio servicio) {
        // Buscamos el cliente por teléfono
        Cliente clienteExistente = clienteRepository.findByTelefono(servicio.getCliente().getTelefono());

        if (clienteExistente == null) {
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(servicio.getCliente().getNombre());
            nuevoCliente.setTelefono(servicio.getCliente().getTelefono());
            nuevoCliente = clienteRepository.save(nuevoCliente);
            servicio.setCliente(nuevoCliente);
        } else {
            servicio.setCliente(clienteExistente);
        }

        // Si se asigna conductor
        if (servicio.getConductor() != null) {
            Conductor conductor = conductorRepository.findById(servicio.getConductor().getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
            
            conductor.setDeuda(conductor.getDeuda() + servicio.getPrecio10());
            conductor.setDineroGenerado(conductor.getDineroGenerado() + servicio.getPrecio());
            servicio.setConductor(conductor);
            conductorRepository.save(conductor);
        }

        return servicioRepository.save(servicio);
    }

    // Buscar servicio por ID
    public Servicio buscarServicioPorId(int id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
    }

    // Actualizar servicio y ajustar deuda y dinero generado
    @Transactional
    public Servicio actualizarServicio(int id, Servicio servicioActualizado) {
        Servicio existente = buscarServicioPorId(id);

        Conductor conductorAnterior = existente.getConductor();

        double precio10Anterior = existente.getPrecio10() != 0 ? existente.getPrecio10() : 0;
        double precioAnterior = existente.getPrecio() != 0 ? existente.getPrecio() : 0;
        double precio10Nuevo = servicioActualizado.getPrecio10() != 0 ? servicioActualizado.getPrecio10() : 0;
        double precioNuevo = servicioActualizado.getPrecio() != 0 ? servicioActualizado.getPrecio() : 0;

        // Actualizamos datos del servicio
        existente.setConductor(servicioActualizado.getConductor());
        existente.setCliente(servicioActualizado.getCliente());
        existente.setOrigen(servicioActualizado.getOrigen());
        existente.setDestino(servicioActualizado.getDestino());
        existente.setNPersona(servicioActualizado.getNPersona());
        existente.setRequisitos(servicioActualizado.getRequisitos());
        existente.setPrecio(precioNuevo);
        existente.setPrecio10(precio10Nuevo);
        existente.setEurotaxi(servicioActualizado.isEurotaxi());
        existente.setFecha(servicioActualizado.getFecha());
        existente.setHora(servicioActualizado.getHora());

        // Si cambia el conductor, ajustar deuda y dinero generado del anterior
        if (conductorAnterior != null &&
                (existente.getConductor() == null
                        || conductorAnterior.getIdConductor() != existente.getConductor().getIdConductor())) {
            conductorAnterior.setDeuda(conductorAnterior.getDeuda() - precio10Anterior);
            conductorAnterior.setDineroGenerado(conductorAnterior.getDineroGenerado() - precioAnterior);
            conductorRepository.save(conductorAnterior);
        }

        // Ajustar deuda y dinero generado del conductor nuevo
        if (existente.getConductor() != null) {
            Conductor conductorNuevo = conductorRepository.findById(existente.getConductor().getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

            double diferenciaDeuda = precio10Nuevo - precio10Anterior;
            double diferenciaDinero = precioNuevo - precioAnterior;

            conductorNuevo.setDeuda(conductorNuevo.getDeuda() + diferenciaDeuda);
            conductorNuevo.setDineroGenerado(conductorNuevo.getDineroGenerado() + diferenciaDinero);
            conductorRepository.save(conductorNuevo);

            existente.setConductor(conductorNuevo);
        }

        return servicioRepository.save(existente);
    }

    // Actualizamos servicio parcialmente
    @Transactional
    public Servicio actualizarServicioParcialmente(int id, Map<String, Object> updates) {
        Servicio servicioExistente = buscarServicioPorId(id);

        Conductor conductorAnterior = servicioExistente.getConductor();
        double precioAnterior = servicioExistente.getPrecio() != 0 ? servicioExistente.getPrecio() : 0;
        double precio10Anterior = servicioExistente.getPrecio10() != 0 ? servicioExistente.getPrecio10() : 0;

        // Actualizamos conductor si viene en updates
        if (updates.containsKey("conductor")) {
            Object conductorObj = updates.get("conductor");
            if (conductorObj == null) {
                servicioExistente.setConductor(null);
            } else if (conductorObj instanceof Map<?, ?> conductorMap) {
                Integer idConductor = (Integer) conductorMap.get("idConductor");
                if (idConductor != null) {
                    Conductor conductorNuevo = conductorRepository.findById(idConductor)
                            .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + idConductor));
                    servicioExistente.setConductor(conductorNuevo);
                }
            }
        }

        // Actualizamos otros campos
        actualizarCampoSiExiste(updates, "origen", String.class, servicioExistente::setOrigen);
        actualizarCampoSiExiste(updates, "destino", String.class, servicioExistente::setDestino);
        actualizarCampoSiExiste(updates, "requisitos", String.class, servicioExistente::setRequisitos);
        actualizarCampoSiExiste(updates, "precio", Double.class, servicioExistente::setPrecio);
        actualizarCampoSiExiste(updates, "precio10", Double.class, servicioExistente::setPrecio10);
        actualizarCampoSiExiste(updates, "eurotaxi", Boolean.class, servicioExistente::setEurotaxi);
        actualizarCampoSiExiste(updates, "nPersona", Integer.class, servicioExistente::setNPersona);
        actualizarCampoSiExiste(updates, "fecha", LocalDate.class, servicioExistente::setFecha);
        actualizarCampoSiExiste(updates, "hora", LocalTime.class, servicioExistente::setHora);

        double precioNuevo = servicioExistente.getPrecio() != 0 ? servicioExistente.getPrecio() : 0;
        double precio10Nuevo = servicioExistente.getPrecio10() != 0 ? servicioExistente.getPrecio10() : 0;

        // Si cambia el conductor, descontamos deuda/dinero del anterior
        if (conductorAnterior != null &&
                (servicioExistente.getConductor() == null ||
                        conductorAnterior.getIdConductor() != servicioExistente.getConductor().getIdConductor())) {
            conductorAnterior.setDeuda(conductorAnterior.getDeuda() - precio10Anterior);
            conductorAnterior.setDineroGenerado(conductorAnterior.getDineroGenerado() - precioAnterior);
            conductorRepository.save(conductorAnterior);
        }

        // Si hay conductor nuevo y precios positivos, ajustamos deuda y dinero generado
        if (servicioExistente.getConductor() != null) {
            Conductor conductorActual = conductorRepository.findById(servicioExistente.getConductor().getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

            double diferenciaDeuda = precio10Nuevo - precio10Anterior;
            double diferenciaDinero = precioNuevo - precioAnterior;

            // Solo ajustamos si la diferencia no es cero y conductor asignado
            if (diferenciaDeuda != 0 || diferenciaDinero != 0) {
                conductorActual.setDeuda(conductorActual.getDeuda() + diferenciaDeuda);
                conductorActual.setDineroGenerado(conductorActual.getDineroGenerado() + diferenciaDinero);
                conductorRepository.save(conductorActual);
            }

            servicioExistente.setConductor(conductorActual);
        }

        return servicioRepository.save(servicioExistente);
    }

    // Eliminar servicio y ajustar deuda/dinero del conductor
    public void eliminarServicioPorId(int id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));

        if (servicio.getConductor() != null) {
            Conductor conductor = servicio.getConductor();
            conductor.setDeuda(conductor.getDeuda() - servicio.getPrecio10());
            conductor.setDineroGenerado(conductor.getDineroGenerado() - servicio.getPrecio());
            conductorRepository.save(conductor);
        }

        servicioRepository.delete(servicio);
    }

    public List<Servicio> listarServicios() {
        return servicioRepository.findAll();
    }

    public List<Servicio> buscarServiciosPorConductor(int idConductor) {
        return servicioRepository.findByConductor_IdConductor(idConductor);
    }

    public long contarServicios() {
        return servicioRepository.count();
    }

    // ------------------------------ HELPERS ------------------------------
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
