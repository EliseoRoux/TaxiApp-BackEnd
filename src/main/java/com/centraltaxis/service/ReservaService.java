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
import java.util.List;

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

    @Transactional
    public Reserva crearReserva(ReservaCreateDTO reservaDTO) {
        Reserva reserva = reservaMapper.toEntity(reservaDTO);

        Cliente cliente = gestionarCliente(reservaDTO.getClienteNombre(), reservaDTO.getClienteTelefono());
        reserva.setCliente(cliente);

        // --- LÓGICA CORREGIDA ---
        if (reservaDTO.getIdConductor() != null) {
            Conductor conductor = conductorRepository.findById(reservaDTO.getIdConductor())
                    .orElseThrow(() -> new RuntimeException(
                            "Conductor no encontrado con ID: " + reservaDTO.getIdConductor()));
            reserva.setConductor(conductor);
        }

        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva actualizarReservaParcial(int id, ReservaCreateDTO reservaDTO) {
        Reserva reservaExistente = buscarReservaPorId(id);
        reservaMapper.applyPatch(reservaDTO, reservaExistente);

        if (reservaDTO.getClienteNombre() != null && reservaDTO.getClienteTelefono() != null) {
            Cliente cliente = gestionarCliente(reservaDTO.getClienteNombre(), reservaDTO.getClienteTelefono());
            reservaExistente.setCliente(cliente);
        }

        // --- LÓGICA CORREGIDA ---
        if (reservaDTO.getIdConductor() != null) {
            if (reservaDTO.getIdConductor() == 0) {
                reservaExistente.setConductor(null);
            } else {
                Conductor conductor = conductorRepository.findById(reservaDTO.getIdConductor())
                        .orElseThrow(() -> new RuntimeException(
                                "Conductor no encontrado con ID: " + reservaDTO.getIdConductor()));
                reservaExistente.setConductor(conductor);
            }
        }

        return reservaRepository.save(reservaExistente);
    }

    @Transactional
    public Reserva actualizarReserva(int id, ReservaCreateDTO reservaDTO) {
        // Esta lógica es prácticamente la misma que la parcial en este caso
        return actualizarReservaParcial(id, reservaDTO);
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
        } else {
            if (!cliente.getNombre().equalsIgnoreCase(nombre.trim())) {
                cliente.setNombre(nombre.trim());
            }
        }
        return clienteRepository.save(cliente);
    }

    // --- MÉTODOS DE BÚSQUEDA Y ELIMINACIÓN (SIN CAMBIOS) ---
    public Reserva buscarReservaPorId(int id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
    }

    public void eliminarReservaPorId(int id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada con ID: " + id);
        }
        reservaRepository.deleteById(id);
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