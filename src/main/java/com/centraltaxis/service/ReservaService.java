package com.centraltaxis.service;

import com.centraltaxis.model.Reserva;
import com.centraltaxis.repository.ReservaRepository;
import com.centraltaxis.repository.ClienteRepository;
import com.centraltaxis.model.Cliente;
import com.centraltaxis.repository.ConductorRepository;
import com.centraltaxis.model.Conductor;
import com.centraltaxis.mapper.ReservaMapper;
import com.centraltaxis.dto.reserva.ReservaCreateDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Método para crear una nueva reserva desde DTO
    @Transactional
    public Reserva crearReserva(ReservaCreateDTO reservaDTO) {
        Reserva reserva = reservaMapper.toEntity(reservaDTO);
        return guardarReserva(reserva);
    }

    // Método para guardar o actualizar una reserva (mantenido para compatibilidad)
    @Transactional
    public Reserva guardarReserva(Reserva reserva) {
        // Buscamos el cliente por el telefono
        Cliente clienteExistente = clienteRepository.findByTelefono(reserva.getCliente().getTelefono());
        
        // Si el cliente no existe, creamos un nuevo cliente
        if (clienteExistente == null) {
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(reserva.getCliente().getNombre());
            nuevoCliente.setTelefono(reserva.getCliente().getTelefono());
            nuevoCliente = clienteRepository.save(nuevoCliente);
            reserva.setCliente(nuevoCliente);
        } else {
            // Si existe, actualizamos el nombre por si ha cambiado y lo asignamos
            clienteExistente.setNombre(reserva.getCliente().getNombre());
            clienteRepository.save(clienteExistente);
            reserva.setCliente(clienteExistente);
        }

        // Manejo del conductor
        if (reserva.getConductor() != null && reserva.getConductor().getIdConductor() != 0) {
            Conductor conductor = conductorRepository.findById(reserva.getConductor().getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
            reserva.setConductor(conductor);
        } else {
            reserva.setConductor(null);
        }
        
        return reservaRepository.save(reserva);
    }

    // Método para actualizar una reserva desde DTO
    @Transactional
    public Reserva actualizarReserva(int id, ReservaCreateDTO reservaDTO) {
        Reserva reservaExistente = buscarReservaPorId(id);
        reservaMapper.ReservaUpdateDTO(reservaDTO, reservaExistente);
        return guardarReserva(reservaExistente);
    }

    // Método para actualización parcial desde DTO
    @Transactional
    public Reserva actualizarReservaParcial(int id, ReservaCreateDTO reservaDTO) {
        Reserva reservaExistente = buscarReservaPorId(id);
        reservaMapper.applyPatch(reservaDTO, reservaExistente);
        return guardarReserva(reservaExistente);
    }

    // Resto de métodos se mantienen igual...
    public Reserva buscarReservaPorId(int id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
    }

    public void eliminarReservaPorId(int id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
        reservaRepository.delete(reserva);
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public long contarReservas() {
        return reservaRepository.count();
    }

    public List<Reserva> buscarReservasPorConductor(int idConductor) {
        return reservaRepository.findByConductor_IdConductor(idConductor);
    }
}