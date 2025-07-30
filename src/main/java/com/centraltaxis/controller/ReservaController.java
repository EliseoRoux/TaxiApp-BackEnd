package com.centraltaxis.controller;

import com.centraltaxis.model.*;
import com.centraltaxis.repository.*;
import com.centraltaxis.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
@Validated
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private ConductorRepository conductorRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    // ------------------------------ CREATE ------------------------------
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody Reserva reserva) {
        Reserva nuevaReserva = reservaService.guardarReserva(reserva);
        return ResponseEntity.status(201).body(nuevaReserva);
    }

    // ------------------------------ READ ------------------------------
    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodasLasReservas() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable @Min(1) int id) {
        return ResponseEntity.ok(reservaService.buscarReservaPorId(id));
    }

    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<Reserva>> obtenerReservasPorConductor(@PathVariable int idConductor) {
        return ResponseEntity.ok(reservaService.buscarReservasPorConductor(idConductor));
    }

    // ------------------------------ UPDATE ------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(
            @PathVariable @Min(1) int id,
            @Valid @RequestBody Reserva reservaActualizada) {

        Reserva reservaExistente = reservaService.buscarReservaPorId(id);
        reservaExistente.setConductor(reservaActualizada.getConductor());
        reservaExistente.setCliente(reservaActualizada.getCliente());
        reservaExistente.setOrigen(reservaActualizada.getOrigen());
        reservaExistente.setDestino(reservaActualizada.getDestino());
        reservaExistente.setNPersona(reservaActualizada.getNPersona());
        reservaExistente.setRequisitos(reservaActualizada.getRequisitos());
        reservaExistente.setPrecio(reservaActualizada.getPrecio());
        reservaExistente.setPrecio10(reservaActualizada.getPrecio10());
        reservaExistente.setEurotaxi(reservaActualizada.isEurotaxi());
        reservaExistente.setFechaReserva(reservaActualizada.getFechaReserva());
        reservaExistente.setHora(reservaActualizada.getHora());

        return ResponseEntity.ok(reservaService.guardarReserva(reservaExistente));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarReservaParcial(
            @PathVariable @Min(1) int id,
            @RequestBody Map<String, Object> updates) {

        try {
            Reserva reservaExistente = reservaService.buscarReservaPorId(id);

            // Actualización del conductor
            if (updates.containsKey("conductor")) {
                if (updates.get("conductor") == null) {
                    reservaExistente.setConductor(null);
                } else if (updates.get("conductor") instanceof Map<?, ?> conductorMap) {
                    Integer idConductor = (Integer) conductorMap.get("idConductor");
                    if (idConductor != null) {
                        Conductor conductor = conductorRepository.findById(idConductor)
                                .orElseThrow(
                                        () -> new RuntimeException("Conductor no encontrado con ID: " + idConductor));
                        reservaExistente.setConductor(conductor);
                    }
                }
            }

            // Actualización del cliente
            if (updates.containsKey("cliente")) {
                if (updates.get("cliente") == null) {
                    reservaExistente.setCliente(null);
                } else if (updates.get("cliente") instanceof Map<?, ?> clienteMap) {
                    Integer idCliente = (Integer) clienteMap.get("idCliente");
                    if (idCliente != null) {
                        Cliente cliente = clienteRepository.findById(idCliente)
                                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + idCliente));
                        reservaExistente.setCliente(cliente);
                    }
                }
            }

            // Actualización de otros campos
            actualizarCampoSiExiste(updates, "origen", String.class, reservaExistente::setOrigen);
            actualizarCampoSiExiste(updates, "destino", String.class, reservaExistente::setDestino);
            actualizarCampoSiExiste(updates, "requisitos", String.class, reservaExistente::setRequisitos);
            actualizarCampoSiExiste(updates, "precio", Double.class, reservaExistente::setPrecio);
            actualizarCampoSiExiste(updates, "precio10", Double.class, reservaExistente::setPrecio10);
            actualizarCampoSiExiste(updates, "eurotaxi", Boolean.class, reservaExistente::setEurotaxi);
            actualizarCampoSiExiste(updates, "nPersona", Integer.class, reservaExistente::setNPersona);
            actualizarCampoSiExiste(updates, "fechaReserva", LocalDate.class, reservaExistente::setFechaReserva);
            actualizarCampoSiExiste(updates, "hora", LocalTime.class, reservaExistente::setHora);

            return ResponseEntity.ok(reservaService.guardarReserva(reservaExistente));

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