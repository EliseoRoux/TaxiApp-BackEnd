package com.centraltaxis.controller;

import com.centraltaxis.dto.conductor.ConductorCreateDTO;
import com.centraltaxis.dto.conductor.ConductorResponseDTO;
import com.centraltaxis.dto.conductor.ConductorUpdateDTO;
import com.centraltaxis.service.ConductorService;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

// Importamos las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
// Importamos la anotación para definir un controlador REST
import org.springframework.web.bind.annotation.*;

// Importamos la clase Logger para registrar eventos
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
// Definimos la ruta base para este controlador
@RequestMapping("/api/conductor")
// Usamos @Validated para habilitar la validación de los parámetros de entrada
@Validated
public class ConductorController {

    // Instancia estática del Logger para esta clase
    private static final Logger log = LoggerFactory.getLogger(ConductorController.class);

    // Inyectamos el servicio ConductorService para manejar la lógica de negocio
    @Autowired
    private ConductorService conductorService;

    // Aquí se pueden definir los endpoints para manejar las solicitudes HTTP
    // relacionadas con Conductor
    // CRUD
    // ------------------------------ CREATE ------------------------------ //
    // Crear un nuevo conductor
    @PostMapping
    public ResponseEntity<ConductorResponseDTO> crearConductor(@Valid @RequestBody ConductorCreateDTO conductorDTO) {
        log.info("Recibida petición POST en /api/conductor para crear un nuevo conductor.");
        // Lógica para crear un nuevo conductor
        ConductorResponseDTO nuevoConductor = conductorService.crearConductor(conductorDTO);
        // Retornamos el conductor creado con un código de estado 201
        log.info("Conductor creado con ID: {}", nuevoConductor.getIdConductor());
        return ResponseEntity.status(201).body(nuevoConductor);
    }

    // ------------------------------ READ ------------------------------ //
    @GetMapping
    public ResponseEntity<List<ConductorResponseDTO>> obtenemosConductores() {
        log.info("Recibida petición GET en /api/conductor para obtener todos los conductores.");
        List<ConductorResponseDTO> conductores = conductorService.listarConductores();
        log.info("Total de conductores encontrados: {}", conductores.size());
        return ResponseEntity.ok(conductores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConductorResponseDTO> obtenerConductorPorId(
            @PathVariable @Min(1) int id) {
        log.info("Recibida petición GET en /api/conductor/{} para obtener un conductor específico.", id);
        ConductorResponseDTO conductor = conductorService.buscarConductorPorId(id);
        log.info("Conductor con ID: {} encontrado.", id);
        return ResponseEntity.ok(conductor);
    }

    @GetMapping("/deudas")
    public ResponseEntity<List<ConductorResponseDTO>> listarDeudas() {
        log.info("Recibida petición GET en /api/conductor/deudas para obtener conductores con deudas.");
        List<ConductorResponseDTO> conductores = conductorService.listarConductoresConDeuda();
        log.info("Total de conductores con deudas encontrados: {}", conductores.size());
        return ResponseEntity.ok(conductores);
    }

    @GetMapping("/no-deuda")
    public ResponseEntity<List<ConductorResponseDTO>> conductorSinDeuda() {
        log.info("Recibida petición GET en /api/conductor/no-deuda para obtener conductores sin deudas.");
        List<ConductorResponseDTO> conductores = conductorService.conductoresSinDeuda();
        log.info("Total de conductores sin deudas encontrados: {}", conductores.size());
        return ResponseEntity.ok(conductores);
    }

    // ------------------------------ UPDATE ------------------------------ //
    @PatchMapping("/{id}")
    public ResponseEntity<ConductorResponseDTO> actualizarConductor(
            @PathVariable @Min(1) int id,
            @Valid @RequestBody ConductorUpdateDTO conductorDTO) {
        log.info("Recibida petición PATCH en /api/conductor/{} para actualizar un conductor.", id);
        ConductorResponseDTO conductorActualizado = conductorService.actualizarConductor(id, conductorDTO);
        log.info("Conductor con ID: {} actualizado exitosamente.", id);
        return ResponseEntity.ok(conductorActualizado);
    }

    // ------------------------------ DELETE ------------------------------ //
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConductor(@PathVariable @Min(1) int id) {
        log.info("Recibida petición DELETE en /api/conductor/{} para eliminar un conductor.", id);
        conductorService.eliminarConductorPorId(id);
        log.info("Conductor con ID: {} eliminado exitosamente.", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/pagar-deuda")
    public ResponseEntity<ConductorResponseDTO> pagarDeuda(@PathVariable @Min(1) int id) {
        log.info("Recibida petición PUT en /api/conductor/{}/pagar-deuda para pagar la deuda del conductor.", id);
        ConductorResponseDTO conductor = conductorService.pagarDeuda(id);
        log.info("Deuda del conductor con ID: {} pagada exitosamente.", id);
        return ResponseEntity.ok(conductor);
    }

}
