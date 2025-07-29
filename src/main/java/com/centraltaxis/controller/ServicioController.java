package com.centraltaxis.controller;

import com.centraltaxis.model.Conductor;
import com.centraltaxis.repository.ConductorRepository;
// Importamos las clases necesarias para el controlador
import com.centraltaxis.model.Servicio;
import com.centraltaxis.service.ServicioService;
// Importamos las clases necesarias para manejar listas
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.*;
import javax.validation.constraints.Min;

// Importamos las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
// Importamos la anotación para definir un controlador REST
import org.springframework.web.bind.annotation.*;

@RestController
// Definimos la ruta base para este controlador
@RequestMapping("/servicios")
// Usamos @Validated para habilitar la validación de los parámetros de entrada
@Validated
public class ServicioController {

    @Autowired
    private ServicioService servicioService;
    @Autowired
    private ConductorRepository conductorRepository;

    // CRUD
    // ------------------------------ CREATE ------------------------------ //

    // Crear un nuevo servicio
    @PostMapping
    public ResponseEntity<Servicio> crearServicio(@Valid @RequestBody Servicio servicio) {
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
        // Si hay servicios, los devolvemos con un código de estado 200 (OK)
        return ResponseEntity.ok(servicios);

    }

    // Obtener un servicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerServicioPorId(@PathVariable @Min(1) int id) {
        // Intentamos buscar el servicio por ID

        Servicio servicio = servicioService.buscarServicioPorId(id);
        // Devolvemos el servicio
        return ResponseEntity.ok(servicio);
    }

    // obtener servicios por conductor
    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<Servicio>> obtenerServiciosPorConductor(@PathVariable @Min(1) int idConductor) {
        // Llamamos al servicio para buscar servicios por conductor
        List<Servicio> servicios = servicioService.buscarServiciosPorConductor(idConductor);

        // Si hay servicios, los devolvemos con un código de estado 200 (OK)
        return ResponseEntity.ok(servicios);

    }

    // ------------------------------ UPDATE ------------------------------ //

    // Actualizar un servicio existente
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicio(@PathVariable @Min(1) int id,
            @Valid @RequestBody Servicio servicioActualizado) {
        // Intentamos buscar el servicio por ID para actualizarlo

        Servicio servicioExistente = servicioService.buscarServicioPorId(id);

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

    }

    // Actualizar parcialmente un servicio (solo algunos campos)
    @PatchMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicioParcialmente(
            @PathVariable @Min(1) int id,
            @RequestBody Map<String, Object> updates) { // Recibe un mapa dinámico de campos a actualizar

        // 1. Busca el servicio existente
        Servicio servicioExistente = servicioService.buscarServicioPorId(id);

        // 2. Actualización segura del conductor con pattern matching
        if (updates.get("conductor") instanceof Map<?, ?> rawConductorMap) {
            Map<String, Object> conductorMap = convertToTypedMap(rawConductorMap); // 🔥 Conversión segura
            if (conductorMap.get("idConductor") instanceof Integer idConductor) {
                Conductor conductor = conductorRepository.findById(idConductor)
                        .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));
                servicioExistente.setConductor(conductor);
            } else {
                servicioExistente.setConductor(null); // Si idConductor no es Integer o es null
            }
        } else if (updates.get("conductor") == null) {
            servicioExistente.setConductor(null); // Caso explícito para null
        }

        // 3. Repetir el patrón para otros campos que necesitemos
        if (updates.get("origen") instanceof String nuevoOrigen) {
            servicioExistente.setOrigen(nuevoOrigen);
        }
        // ... (añadiremos más campos según necesitemos, para pruebas de momento asi,
        // HAY QUE ACTUALIZAR)
        if (updates.get("nPersona") instanceof Integer nPersona) {
            servicioExistente.setNPersona(nPersona);
        } 
        // 4. Guarda y devuelve el servicio actualizado
        Servicio servicioActualizado = servicioService.guardarServicio(servicioExistente);
        return ResponseEntity.ok(servicioActualizado);
    }

    // Método auxiliar para convertir Map<?, ?> a Map<String, Object>
    private Map<String, Object> convertToTypedMap(Map<?, ?> rawMap) {
        return rawMap.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().toString(),
                        Map.Entry::getValue));
    }

    // ------------------------------- DELETE ------------------------------ //

    // Eliminar un servicio por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable @Min(1) int id) {
        // Intentamos eliminar el servicio por ID

        servicioService.eliminarServicioPorId(id);
        // Devolvemos un 204 No Content si se elimina correctamente
        return ResponseEntity.noContent().build();

    }

}
