package com.centraltaxis.controller;

import com.centraltaxis.model.*;
import com.centraltaxis.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/servicios")
@Validated
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

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

        Servicio servicioActualizadoFinal = servicioService.actualizarServicio(id, servicioActualizado);
        return ResponseEntity.ok(servicioActualizadoFinal);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarServicioParcialmente(
            @PathVariable @Min(1) int id,
            @RequestBody Map<String, Object> updates) {

        try {
            // Llamamos al service para que se encargue de la lógica de actualización
            Servicio servicioActualizado = servicioService.actualizarServicioParcialmente(id, updates);

            // Retornamos el servicio actualizado con código 200 OK
            return ResponseEntity.ok(servicioActualizado);

        } catch (RuntimeException e) {
            // En caso de errores como "servicio no encontrado", devolvemos 404 con mensaje
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            // Cualquier otro error se responde con un error 500
            return ResponseEntity.status(500).body(Map.of("error", "Error interno del servidor"));
        }
    }

    // ------------------------------ DELETE ------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable @Min(1) int id) {
        servicioService.eliminarServicioPorId(id);
        return ResponseEntity.noContent().build();
    }

    
}