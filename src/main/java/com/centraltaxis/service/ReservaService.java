package com.centraltaxis.service;

// Importamos las clases necesarias para el servicio Reserva
import com.centraltaxis.model.Reserva;
import com.centraltaxis.repository.ReservaRepository;

import java.util.List;

// Importamos las anotaciones necesarias para el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // Método para guardar o actualizar una reserva
    public Reserva guardarReserva(Reserva reserva) {
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

    // Método para buscar reservas por cliente
    public List<Reserva> buscarReservasPorCliente(int idCliente) {
        return reservaRepository.findByClienteId(idCliente);
    }
}
