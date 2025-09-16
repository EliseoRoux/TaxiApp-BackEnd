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

@RestController
// Definimos la ruta base para este controlador
@RequestMapping("/api/conductor")
// Usamos @Validated para habilitar la validación de los parámetros de entrada
@Validated
public class ConductorController {

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
        // Lógica para crear un nuevo conductor
        ConductorResponseDTO nuevoConductor = conductorService.crearConductor(conductorDTO);
        // Retornamos el conductor creado con un código de estado 201
        return ResponseEntity.status(201).body(nuevoConductor);
    }

    // ------------------------------ READ ------------------------------ //
    @GetMapping
    public ResponseEntity<List<ConductorResponseDTO>> obtenemosConductores() {
        List<ConductorResponseDTO> conductores = conductorService.listarConductores();
        return ResponseEntity.ok(conductores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConductorResponseDTO> obtenerConductorPorId(
            @PathVariable @Min(1) int id) {
        ConductorResponseDTO conductor = conductorService.buscarConductorPorId(id);
        return ResponseEntity.ok(conductor);
    }

    @GetMapping("/deudas")
    public ResponseEntity<List<ConductorResponseDTO>> listarDeudas() {
        List<ConductorResponseDTO> conductores = conductorService.listarConductoresConDeuda();
        return ResponseEntity.ok(conductores);
    }

    @GetMapping("/no-deuda")
    public ResponseEntity<List<ConductorResponseDTO>> conductorSinDeuda() {
        List<ConductorResponseDTO> conductores = conductorService.conductoresSinDeuda();
        return ResponseEntity.ok(conductores);
    }

    // ------------------------------ UPDATE ------------------------------ //
    @PutMapping("/{id}")
    public ResponseEntity<ConductorResponseDTO> actualizarConductor(
            @PathVariable @Min(1) int id,
            @Valid @RequestBody ConductorUpdateDTO conductorDTO) {
        ConductorResponseDTO conductorActualizado = conductorService.actualizarConductor(id, conductorDTO);
        return ResponseEntity.ok(conductorActualizado);
    }

    // ------------------------------ DELETE ------------------------------ //
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConductor(@PathVariable @Min(1) int id) {
        conductorService.eliminarConductorPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/pagar-deuda")
    public ResponseEntity<ConductorResponseDTO> pagarDeuda(@PathVariable @Min(1) int id) {
        ConductorResponseDTO conductor = conductorService.pagarDeuda(id);
        return ResponseEntity.ok(conductor);
    }

}
