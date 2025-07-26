package com.centraltaxis.controller;

// Importamos las clases necesarias para el controlador
import com.centraltaxis.model.Servicio;
import com.centraltaxis.service.ServicioService;
// Importamos las clases necesarias para manejar listas
import java.util.List;
// Importamos las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// Importamos la anotación para definir un controlador REST
import org.springframework.web.bind.annotation.*;

@RestController
// Definimos la ruta base para este controlador
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    // CRUD
    // ------------------------------ CREATE ------------------------------ //

    // Crear un nuevo servicio
    @PostMapping
    public ResponseEntity<Servicio> crearServicio(@RequestBody Servicio servicio) {
        // Llamamos al servicio para guardar el nuevo servicio
        Servicio nuevoServicio = servicioService.guardarServicio(servicio);
        // Devolvemos el servicio creado con un código de estado 201 (Creado)
        return ResponseEntity.status(201).body(nuevoServicio);
    }

    // ------------------------------ READ ------------------------------ //

    // Obtener todos los servicios
    @GetMapping
    public ResponseEntity<List<Servicio>> obtenerTodosLosServicios() {
        // Llamamos al servicio para listar todos los servicios
        List<Servicio> servicios = servicioService.listarServicios();
        if (servicios.isEmpty()) {
            // Si no hay servicios, devolvemos un código de estado 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Si hay servicios, los devolvemos con un código de estado 200 (OK)
            return ResponseEntity.ok(servicios);
        }
    }

    // Obtener un servicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerServicioPorId(@PathVariable int id) {
        // Intentamos buscar el servicio por ID
        // Si no se encuentra, se lanzará una excepción que podemos manejar
        try {
            Servicio servicio = servicioService.buscarServicioPorId(id)
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
            return ResponseEntity.ok(servicio); // Si se encuentra, devolvemos el servicio
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si no se encuentra, devolvemos un 404 Not Found
        }
    }

    // obtener servicios por conductor
    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<Servicio>> obtenerServiciosPorConductor(@PathVariable int idConductor) {
        // Llamamos al servicio para buscar servicios por conductor
        List<Servicio> servicios = servicioService.buscarServiciosPorConductor(idConductor);
        if (servicios.isEmpty()) {
            // Si no hay servicios, devolvemos un código de estado 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Si hay servicios, los devolvemos con un código de estado 200 (OK)
            return ResponseEntity.ok(servicios);
        }
    }

    // ------------------------------ UPDATE ------------------------------ //

    // Actualizar un servicio existente
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicio(@PathVariable int id,
            @RequestBody Servicio servicioActualizado) {
        // Intentamos buscar el servicio por ID para actualizarlo
        try {
            Servicio servicioExistente = servicioService.buscarServicioPorId(id)
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
            // Actualizamos los datos del servicio existente
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
            // Guardamos el servicio actualizado
            Servicio servicioGuardado = servicioService.guardarServicio(servicioExistente);
            return ResponseEntity.ok(servicioGuardado); // Devolvemos el servicio actualizado
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si no se encuentra, devolvemos un 404 Not Found
        }
    }

    // ------------------------------- DELETE ------------------------------ //

    // Eliminar un servicio por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable int id) {
        // Intentamos eliminar el servicio por ID
        try {
            servicioService.eliminarServicioPorId(id);
            return ResponseEntity.noContent().build(); // Devolvemos un 204 No Content si se elimina correctamente
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si no se encuentra el servicio, devolvemos un 404 Not Found
        }
    }

}
