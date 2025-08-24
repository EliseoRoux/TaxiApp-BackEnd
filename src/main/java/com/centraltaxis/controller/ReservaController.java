package com.centraltaxis.controller;

import com.centraltaxis.model.*;
import com.centraltaxis.service.ReservaService;
import com.centraltaxis.mapper.ReservaMapper;
import com.centraltaxis.dto.reserva.ReservaCreateDTO;
import com.centraltaxis.dto.reserva.ReservaResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
@Validated
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    
    @Autowired
    private ReservaMapper reservaMapper;

    // ------------------------------ CREATE ------------------------------
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(@Valid @RequestBody ReservaCreateDTO reservaDTO) {
        Reserva nuevaReserva = reservaService.crearReserva(reservaDTO);
        ReservaResponseDTO responseDTO = reservaMapper.toResponseDTO(nuevaReserva);
        return ResponseEntity.status(201).body(responseDTO);
    }

    // ------------------------------ READ ------------------------------
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> obtenerTodasLasReservas() {
        List<Reserva> reservas = reservaService.listarReservas();
        List<ReservaResponseDTO> responseDTOs = reservas.stream()
                .map(reservaMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerReservaPorId(@PathVariable @Min(1) int id) {
        Reserva reserva = reservaService.buscarReservaPorId(id);
        ReservaResponseDTO responseDTO = reservaMapper.toResponseDTO(reserva);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<ReservaResponseDTO>> obtenerReservasPorConductor(@PathVariable int idConductor) {
        List<Reserva> reservas = reservaService.buscarReservasPorConductor(idConductor);
        List<ReservaResponseDTO> responseDTOs = reservas.stream()
                .map(reservaMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(responseDTOs);
    }

    // ------------------------------ UPDATE ------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReserva(
            @PathVariable @Min(1) int id,
            @Valid @RequestBody ReservaCreateDTO reservaDTO) {
        
        Reserva reservaActualizada = reservaService.actualizarReserva(id, reservaDTO);
        ReservaResponseDTO responseDTO = reservaMapper.toResponseDTO(reservaActualizada);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarReservaParcial(
            @PathVariable @Min(1) int id,
            @RequestBody ReservaCreateDTO reservaDTO) {
        
        try {
            Reserva reservaActualizada = reservaService.actualizarReservaParcial(id, reservaDTO);
            ReservaResponseDTO responseDTO = reservaMapper.toResponseDTO(reservaActualizada);
            return ResponseEntity.ok(responseDTO);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error interno del servidor"));
        }
    }

    // ------------------------------ DELETE ------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable @Min(1) int id) {
        reservaService.eliminarReservaPorId(id);
        return ResponseEntity.noContent().build();
    }
}