package com.centraltaxis.service;

import com.centraltaxis.dto.reserva.ReservaCreateDTO;
import com.centraltaxis.mapper.ReservaMapper;
import com.centraltaxis.model.Cliente;
import com.centraltaxis.model.Conductor;
import com.centraltaxis.model.Reserva;
import com.centraltaxis.repository.ClienteRepository;
import com.centraltaxis.repository.ConductorRepository;
import com.centraltaxis.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConductorRepository conductorRepository;
    @Autowired
    private ReservaMapper reservaMapper;

    // --- LÓGICA DE DEUDA  ---
    private void actualizarCuentasConductor(Conductor conductor, Double precio, Double precio10, boolean esSuma) {
        if (conductor == null)
            return;

        double valorPrecio = (precio != null) ? precio : 0.0;
        double valorPrecio10 = (precio10 != null) ? precio10 : 0.0;

        double deudaActual = conductor.getDeuda() != null ? conductor.getDeuda() : 0.0;
        double dineroGeneradoActual = conductor.getDineroGenerado() != null ? conductor.getDineroGenerado() : 0.0;

        if (esSuma) {
            conductor.setDeuda(deudaActual + valorPrecio10);
            conductor.setDineroGenerado(dineroGeneradoActual + valorPrecio);
        } else {
            conductor.setDeuda(deudaActual - valorPrecio10);
            conductor.setDineroGenerado(dineroGeneradoActual - valorPrecio);
        }
        conductorRepository.save(conductor);
    }

    @Transactional
    public Reserva crearReserva(ReservaCreateDTO reservaDTO) {
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        Cliente cliente = gestionarCliente(reservaDTO.getClienteNombre(), reservaDTO.getClienteTelefono());
        reserva.setCliente(cliente);

        if (reservaDTO.getIdConductor() != null) {
            Conductor conductor = conductorRepository.findById(reservaDTO.getIdConductor())
                    .orElseThrow(() -> new RuntimeException(
                            "Conductor no encontrado con ID: " + reservaDTO.getIdConductor()));
            reserva.setConductor(conductor);
            actualizarCuentasConductor(conductor, reserva.getPrecio(), reserva.getPrecio10(), true);
        }

        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva actualizarReservaParcial(int id, ReservaCreateDTO reservaDTO) {
        Reserva reservaExistente = buscarReservaPorId(id);

        Conductor conductorAnterior = reservaExistente.getConductor();
        Double precioAnterior = reservaExistente.getPrecio();
        Double precio10Anterior = reservaExistente.getPrecio10();

        reservaMapper.applyPatch(reservaDTO, reservaExistente);

        if (reservaDTO.getClienteNombre() != null && reservaDTO.getClienteTelefono() != null) {
            Cliente cliente = gestionarCliente(reservaDTO.getClienteNombre(), reservaDTO.getClienteTelefono());
            reservaExistente.setCliente(cliente);
        }

        Integer idConductorNuevo = reservaDTO.getIdConductor();
        Conductor conductorNuevo = (idConductorNuevo != null && idConductorNuevo != 0)
                ? conductorRepository.findById(idConductorNuevo)
                        .orElseThrow(() -> new RuntimeException("Conductor no encontrado"))
                : (idConductorNuevo != null && idConductorNuevo == 0) ? null : conductorAnterior;

        reservaExistente.setConductor(conductorNuevo);

        boolean conductorCambio = !Objects.equals(conductorAnterior, conductorNuevo);
        boolean precioCambio = !Objects.equals(precioAnterior, reservaExistente.getPrecio());

        if (conductorCambio) {
            if (conductorAnterior != null) {
                actualizarCuentasConductor(conductorAnterior, precioAnterior, precio10Anterior, false);
            }
            if (conductorNuevo != null) {
                actualizarCuentasConductor(conductorNuevo, reservaExistente.getPrecio(), reservaExistente.getPrecio10(),
                        true);
            }
        } else if (precioCambio) {
            if (conductorNuevo != null) {
                double diffPrecio = reservaExistente.getPrecio() - precioAnterior;
                double diffPrecio10 = reservaExistente.getPrecio10() - precio10Anterior;
                actualizarCuentasConductor(conductorNuevo, diffPrecio, diffPrecio10, true);
            }
        }

        return reservaRepository.save(reservaExistente);
    }

    @Transactional
    public void eliminarReservaPorId(int id) {
        Reserva reserva = buscarReservaPorId(id);

        if (reserva.getConductor() != null) {
            actualizarCuentasConductor(reserva.getConductor(), reserva.getPrecio(), reserva.getPrecio10(), false);
        }

        reservaRepository.deleteById(id);
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

    public Reserva buscarReservaPorId(int id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public List<Reserva> buscarReservasPorConductor(int idConductor) {
        return reservaRepository.findByConductor_IdConductor(idConductor);
    }

    public List<Reserva> buscarReservaPorConductorFecha(int idConductor, LocalDate fechaInicio, LocalDate fechaFin) {
        return reservaRepository.findByConductorAndFechaRango(idConductor, fechaInicio, fechaFin);
    }
}