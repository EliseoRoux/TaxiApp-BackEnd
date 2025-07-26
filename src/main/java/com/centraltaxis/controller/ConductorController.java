package com.centraltaxis.controller;

// Importamos las clases necesarias para el controlador
import com.centraltaxis.model.Conductor;
import com.centraltaxis.service.ConductorService;

import java.util.List;

// Importamos las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// Importamos la anotación para definir un controlador REST
import org.springframework.web.bind.annotation.*;

@RestController
// Definimos la ruta base para este controlador
@RequestMapping("/api/conductor")
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
    public ResponseEntity<Conductor> crearConductor(@RequestBody Conductor conductor) {
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
        if (conductores.isEmpty()) {
            // Si no hay conductores, devolvemos un código de estado 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Si hay conductores, los devolvemos con un código de estado 200 (OK)
            return ResponseEntity.ok(conductores);
        }
    }

    // Leer un conductor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Conductor> obtenerConductorPorId(@PathVariable int id) {
        // Llamamos al servicio para buscar un conductor por ID
        return conductorService.buscarConductorPorId(id)
                .map(conductor -> ResponseEntity.ok(conductor)) // Si se encuentra, devolvemos el conductor
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra, devolvemos un 404 Not Found
    }

    // ------------------------------ UPDATE ------------------------------ //

    // Actualizar un conductor existente
    @PutMapping("/{id}")
    public ResponseEntity<Conductor> actualizarConductor(@PathVariable int id,
            @RequestBody Conductor conductorActualizado) {
        // Controlamos el metodo con un try-catch para manejar excepciones
        // Llamamos al servicio para buscar el conductor por ID
        try {
            Conductor conductorExistente = conductorService.buscarConductorPorId(id)
                    .orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));
            // Actualizamos los datos del conductor existente
            conductorExistente.setNombre(conductorActualizado.getNombre());
            conductorExistente.setTelefono(conductorActualizado.getTelefono());
            conductorExistente.setDeuda(conductorActualizado.getDeuda());
            conductorExistente.setDineroGenerado(conductorActualizado.getDineroGenerado());
            // Guardamos el conductor actualizado
            Conductor conductorGuardado = conductorService.guardarConductor(conductorExistente);
            return ResponseEntity.ok(conductorGuardado);
        } catch (RuntimeException e) {
            // Si ocurre una excepción, devolvemos un 404 Not Found
            return ResponseEntity.notFound().build();
        }

    }

    // ------------------------------ DELETE ------------------------------ //

    // Eliminar un conductor por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConductor(@PathVariable int id) {
        // Controlamos el metodo con un try-catch para manejar excepciones
        try {
            // Llamamos al servicio para eliminar un conductor por ID
            conductorService.eliminarConductorPorId(id);
            return ResponseEntity.noContent().build(); // Devolvemos un 204 No Content si se elimina correctamente
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si no se encuentra el conductor, devolvemos un 404 Not Found
        }
    }

}
