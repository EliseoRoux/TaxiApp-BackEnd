package com.centraltaxis.controller;

import com.centraltaxis.model.*;
import com.centraltaxis.repository.*;
import com.centraltaxis.service.ServicioService;
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
@RequestMapping("/servicios")
@Validated
public class ServicioController {

    @Autowired
    private ServicioService servicioService;
    @Autowired
    private ConductorRepository conductorRepository;
    @Autowired
    private ServicioRepository servicioRepository;

    // ------------------------------ CREATE ------------------------------
    @PostMapping
    public ResponseEntity<Servicio> crearServicio(@Valid @RequestBody Servicio servicio) {
        Servicio nuevoServicio = servicioService.guardarServicio(servicio);
        return ResponseEntity.status(201).body(nuevoServicio);
    }

    // ------------------------------ READ ------------------------------
    @GetMapping
    public ResponseEntity<List<Servicio>> obtenerTodosLosServicios() {
        return ResponseEntity.ok(servicioService.listarServicios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerServicioPorId(@PathVariable @Min(1) int id) {
        return ResponseEntity.ok(servicioService.buscarServicioPorId(id));
    }

    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<Servicio>> obtenerServiciosPorConductor(@PathVariable @Min(1) int idConductor) {
        return ResponseEntity.ok(servicioService.buscarServiciosPorConductor(idConductor));
    }

    // ------------------------------ UPDATE ------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicio(
            @PathVariable @Min(1) int id,
            @Valid @RequestBody Servicio servicioActualizado) {

        Servicio servicioExistente = servicioService.buscarServicioPorId(id);
        servicioExistente.setConductor(servicioActualizado.getConductor());
        servicioExistente.setCliente(servicioActualizado.getCliente());
        servicioExistente.setOrigen(servicioActualizado.getOrigen());
        servicioExistente.setDestino(servicioActualizado.getDestino());
        servicioExistente.setNPersona(servicioActualizado.getNPersona());
        servicioExistente.setRequisitos(servicioActualizado.getRequisitos());
        servicioExistente.setPrecio(servicioActualizado.getPrecio());
        servicioExistente.setPrecio10(servicioActualizado.getPrecio10());
        servicioExistente.setEurotaxi(servicioActualizado.isEurotaxi());
        servicioExistente.setFecha(servicioActualizado.getFecha());
        servicioExistente.setHora(servicioActualizado.getHora());

        return ResponseEntity.ok(servicioService.guardarServicio(servicioExistente));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarServicioParcialmente(
            @PathVariable @Min(1) int id,
            @RequestBody Map<String, Object> updates) {

        try {
            Servicio servicioExistente = servicioService.buscarServicioPorId(id);

            // Actualización del conductor
            if (updates.containsKey("conductor")) {
                if (updates.get("conductor") == null) {
                    servicioExistente.setConductor(null);
                } else if (updates.get("conductor") instanceof Map<?, ?> conductorMap) {
                    Integer idConductor = (Integer) conductorMap.get("idConductor");
                    if (idConductor != null) {
                        Conductor conductor = conductorRepository.findById(idConductor)
                                .orElseThrow(
                                        () -> new RuntimeException("Conductor no encontrado con ID: " + idConductor));
                        servicioExistente.setConductor(conductor);
                    }
                }
            }

            // Actualización de otros campos
            actualizarCampoSiExiste(updates, "origen", String.class, servicioExistente::setOrigen);
            actualizarCampoSiExiste(updates, "destino", String.class, servicioExistente::setDestino);
            actualizarCampoSiExiste(updates, "requisitos", String.class, servicioExistente::setRequisitos);
            actualizarCampoSiExiste(updates, "precio", Double.class, servicioExistente::setPrecio);
            actualizarCampoSiExiste(updates, "precio10", Double.class, servicioExistente::setPrecio10);
            actualizarCampoSiExiste(updates, "eurotaxi", Boolean.class, servicioExistente::setEurotaxi);
            actualizarCampoSiExiste(updates, "nPersona", Integer.class, servicioExistente::setNPersona);
            actualizarCampoSiExiste(updates, "fecha", LocalDate.class, servicioExistente::setFecha);
            actualizarCampoSiExiste(updates, "hora", LocalTime.class, servicioExistente::setHora);

            return ResponseEntity.ok(servicioRepository.save(servicioExistente));

        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error interno del servidor"));
        }
    }

    // ------------------------------ DELETE ------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable @Min(1) int id) {
        servicioService.eliminarServicioPorId(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------ HELPERS ------------------------------
    private <T> void actualizarCampoSiExiste(Map<String, Object> updates, String campo, Class<T> tipo, java.util.function.Consumer<T> setter) {
        if (updates.containsKey(campo)) {
            Object valor = updates.get(campo);
            if (tipo.isInstance(valor)) {
                setter.accept(tipo.cast(valor));
            }
        }
    }
}