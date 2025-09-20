package com.centraltaxis.controller;

import com.centraltaxis.model.*;
import com.centraltaxis.service.ReservaService;
import com.centraltaxis.mapper.ReservaMapper;
import com.centraltaxis.dto.reserva.ReservaCreateDTO;
import com.centraltaxis.dto.reserva.ReservaResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// Importamos la clase Logger para registrar eventos
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/reservas")
@Validated
public class ReservaController {
    // Instancia estática del Logger para esta clase
    private static final Logger log = LoggerFactory.getLogger(ReservaController.class);

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaMapper reservaMapper;

    // ------------------------------ CREATE ------------------------------
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@Valid @RequestBody ReservaCreateDTO reservaDTO) {
        log.info("Recibida petición POST en /api/reservas para crear una nueva reserva.");
        Reserva nuevaReserva = reservaService.crearReserva(reservaDTO);
        ReservaResponseDTO responseDTO = reservaMapper.toResponseDTO(nuevaReserva);
        log.info("Reserva creada con ID: {}", nuevaReserva.getIdReserva());
        return ResponseEntity.status(201).body(responseDTO);
    }

    // ------------------------------ READ ------------------------------
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> obtenerTodasLasReservas() {
        log.info("Recibida petición GET en /api/reservas para obtener todas las reservas.");
        List<Reserva> reservas = reservaService.listarReservas();
        List<ReservaResponseDTO> responseDTOs = reservas.stream()
                .map(reservaMapper::toResponseDTO)
                .toList();
        log.info("Total de reservas encontradas: {}", responseDTOs.size());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerReservaPorId(@PathVariable @Min(1) int id) {
        log.info("Recibida petición GET en /api/reservas/{} para obtener una reserva específica.", id);
        Reserva reserva = reservaService.buscarReservaPorId(id);
        ReservaResponseDTO responseDTO = reservaMapper.toResponseDTO(reserva);
        log.info("Reserva con ID: {} encontrada.", id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorConductor(@PathVariable int idConductor) {
        log.info("Recibida petición GET en /api/reservas/conductor/{} para obtener reservas del conductor.", idConductor);
        List<Reserva> reservas = reservaService.buscarReservasPorConductor(idConductor);
        List<ReservaResponseDTO> responseDTOs = reservas.stream()
                .map(reservaMapper::toResponseDTO)
                .toList();
        log.info("Total de reservas encontradas para el conductor ID {}: {}", idConductor, responseDTOs.size());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/conductor/{idConductor}/filtrar")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservaPorConductorYFecha(
            @PathVariable @Min(1) int idConductor,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        log.info("Recibida petición GET en /api/reservas/conductor/{}/filtrar para obtener reservas con filtros de fecha. Fecha inicio: {}, Fecha fin: {}",
                idConductor, fechaInicio, fechaFin);
        List<Reserva> reservas = reservaService.buscarReservaPorConductorFecha(idConductor, fechaInicio, fechaFin);
        List<ReservaResponseDTO> out = reservas.stream()
                .map(reservaMapper::toResponseDTO)
                .collect(Collectors.toList());
        log.info("Total de reservas encontradas para el conductor ID {} con los filtros aplicados: {}", idConductor, out.size());
        return ResponseEntity.ok(out);
    }

    // ------------------------------ UPDATE ------------------------------
    @PatchMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReservaParcial(
            @PathVariable @Min(1) int id,
            @RequestBody ReservaCreateDTO reservaDTO) {
        log.info("Recibida petición PATCH en /api/reservas/{} para actualización parcial.", id);

        Reserva reservaActualizada = reservaService.actualizarReservaParcial(id, reservaDTO);
        ReservaResponseDTO responseDTO = reservaMapper.toResponseDTO(reservaActualizada);

        log.info("Reserva con ID: {} actualizada exitosamente.", id);
        return ResponseEntity.ok(responseDTO);
    }

    // ------------------------------ DELETE ------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable @Min(1) int id) {
        log.info("Recibida petición DELETE en /api/reservas/{} para eliminar reserva.", id);
        reservaService.eliminarReservaPorId(id);
        log.info("Reserva con ID: {} eliminada exitosamente.", id);
        return ResponseEntity.noContent().build();
    }
}
