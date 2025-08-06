package com.centraltaxis.service;

// Importamos las clases necesarias para el servicio Reserva
import com.centraltaxis.model.Reserva;
import com.centraltaxis.repository.ReservaRepository;
import com.centraltaxis.repository.ClienteRepository;
import com.centraltaxis.model.Cliente;
import com.centraltaxis.repository.ConductorRepository;
import com.centraltaxis.model.Conductor;

import java.util.List;

// Importamos las anotaciones necesarias para el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ConductorRepository conductorRepository;

    // Método para guardar o actualizar una reserva
    public Reserva guardarReserva(Reserva reserva) {
        // Buscamos el cliente por el telefono
        Cliente clienteExistente = clienteRepository.findByTelefono(reserva.getCliente().getTelefono());
        // Si el cliente no existe, creamos un nuevo cliente
        if (clienteExistente == null) {
            Cliente nuevoCliente = new Cliente();
            // Asignamos los datos de la reserva al nuevo cliente
            nuevoCliente.setNombre(reserva.getCliente().getNombre());
            nuevoCliente.setTelefono(reserva.getCliente().getTelefono());
            // Guardamos el nuevo cliente
            nuevoCliente = clienteRepository.save(nuevoCliente);
            // Asignamos el nuevo cliente a la reserva
            reserva.setCliente(nuevoCliente);
        } else {
            // Si existe lo asignamos a la reserva
            reserva.setCliente(clienteExistente);
        }

        // Ahora buscamos si el conductor lo han asignado o es null
        // Si lo han asignado lo insertamos a la reserva
        if (reserva.getConductor() != null) {
            // Ya que no es null lo buscamos
            Conductor conductor = conductorRepository.findById(reserva.getConductor().getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
            // Y lo asignamos a la reserva
            reserva.setConductor(conductor);
        }
        // Guardamos la reserva
        return reservaRepository.save(reserva);
    }

    // Método para buscar una reserva por ID
    public Reserva buscarReservaPorId(int id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
    }

    // Método para eliminar una reserva por ID
    public void eliminarReservaPorId(int id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
        reservaRepository.delete(reserva);
    }

    // Método para listar todas las reservas
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    // Método para contar el número total de reservas
    public long contarReservas() {
        return reservaRepository.count();
    }

    // Método para buscar reservas por conductor
    public List<Reserva> buscarReservasPorConductor(int idConductor) {
        return reservaRepository.findByConductor_IdConductor(idConductor);
    }
}
