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
        // Obtenemos el cliente y lo asignamos a la reserva
        Cliente cliente = clienteRepository.findById(reserva.getCliente().getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        // Asignamos el cliente a la reserva
        reserva.setCliente(cliente);

        // Obtenemos el conductor si lo hay y lo asignamos a la reserva, si no lo hay,
        // lo dejamos como null
        if (reserva.getConductor() != null) {
            Conductor conductor = conductorRepository.findById(reserva.getConductor().getIdConductor())
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
            reserva.setConductor(conductor);
        } else {
            reserva.setConductor(null);
        }
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
