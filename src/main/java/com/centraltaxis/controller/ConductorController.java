package com.centraltaxis.controller;

// Importamos las clases necesarias para el controlador
import com.centraltaxis.model.Conductor;
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
    public ResponseEntity<Conductor> crearConductor(@Valid @RequestBody Conductor conductor) {
        // Lógica para crear un nuevo conductor
        Conductor nuevoConductor = conductorService.guardarConductor(conductor);
        // Retornamos el conductor creado con un código de estado 201
        return ResponseEntity.status(201).body(nuevoConductor);
    }

    // ------------------------------ READ ------------------------------ //

    // Leer todos los conductores
    @GetMapping
    public ResponseEntity<List<Conductor>> obtenemosConductores() {
        // Llamamos al servicio para listar todos los conductores
        List<Conductor> conductores = conductorService.listarConductores();
        // Si hay conductores, los devolvemos con un código de estado 200 (OK)
        return ResponseEntity.ok(conductores);
    }

    // Leer un conductor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Conductor> obtenerConductorPorId(@PathVariable @Min(1) int id) {
        // Llamamos al servicio para buscar un conductor por ID
        Conductor conductor = conductorService.buscarConductorPorId(id);
        // Si se encuentra, devolvemos el conductor con un código de estado 200 (OK)
        return ResponseEntity.ok(conductor);
    }

    // ------------------------------ UPDATE ------------------------------ //

    // Actualizar un conductor existente
    @PutMapping("/{id}")
    public ResponseEntity<Conductor> actualizarConductor(@PathVariable @Min(1) int id,
            @Valid @RequestBody Conductor conductorActualizado) {
        // Llamamos al servicio para buscar el conductor por ID
        Conductor conductorExistente = conductorService.buscarConductorPorId(id);
        // Actualizamos los datos del conductor existente
        conductorExistente.setNombre(conductorActualizado.getNombre());
        conductorExistente.setTelefono(conductorActualizado.getTelefono());
        conductorExistente.setDeuda(conductorActualizado.getDeuda());
        conductorExistente.setDineroGenerado(conductorActualizado.getDineroGenerado());
        // Guardamos el conductor actualizado
        Conductor conductorGuardado = conductorService.guardarConductor(conductorExistente);
        return ResponseEntity.ok(conductorGuardado);
    }

    // ------------------------------ DELETE ------------------------------ //

    // Eliminar un conductor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConductor(@PathVariable @Min(1) int id) {
        // Llamamos al servicio para eliminar un conductor por ID
        conductorService.eliminarConductorPorId(id);
        return ResponseEntity.noContent().build(); // Devolvemos un 204 No Content si se elimina correctamente
    }
}
