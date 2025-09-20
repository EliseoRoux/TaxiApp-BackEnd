package com.centraltaxis.controller;

import com.centraltaxis.dto.servicio.ServicioCreateDTO;
import com.centraltaxis.dto.servicio.ServicioPatchDTO;
import com.centraltaxis.dto.servicio.ServicioResponseDTO;
import com.centraltaxis.mapper.ServicioMapper;
import com.centraltaxis.model.Servicio;
import com.centraltaxis.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;
// Importamos la clase Logger para registrar eventos
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/servicios")
@Validated
public class ServicioController {
    // Instancia estática del Logger para esta clase
    private static final Logger log = LoggerFactory.getLogger(ServicioController.class);

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ServicioMapper servicioMapper;

    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crearServicio(@Valid @RequestBody ServicioCreateDTO servicioDTO) {
        // Log para registrar la entrada a este endpoint
        log.info("Recibida petición POST en /api/servicios para crear un nuevo servicio.");

        Servicio servicioCreado = servicioService.crearServicio(servicioDTO);

        log.info("Servicio creado con ID: {}", servicioCreado.getIdServicio());
        return ResponseEntity.status(201).body(servicioMapper.toResponse(servicioCreado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> actualizarServicioParcialmente(
            @PathVariable @Min(1) int id,
            @RequestBody ServicioPatchDTO updates) {
        // Log con parámetros para saber a qué recurso específico se está intentando acceder.
        log.info("Recibida petición PATCH en /api/servicios/{} para actualización parcial.", id);

        Servicio actualizado = servicioService.actualizarServicioParcialmente(id, updates);
        log.info("Servicio con ID: {} actualizado exitosamente.", id);
        return ResponseEntity.ok(servicioMapper.toResponse(actualizado));
    }

    // --- MÉTODOS DE LECTURA Y BORRADO ---
    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> obtenerTodosLosServicios() {
        log.info("Recibida petición GET en /api/servicios para obtener todos los servicios.");

        List<Servicio> servicios = servicioService.listarServicios();
        log.info("Total de servicios encontrados: {}", servicios.size());
        return ResponseEntity.ok(servicios.stream().map(servicioMapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> obtenerServicioPorId(@PathVariable @Min(1) int id) {
        log.info("Recibida petición GET en /api/servicios/{} para obtener servicio por ID.", id);

        Servicio s = servicioService.buscarServicioPorId(id);
        log.info("Servicio con ID: {} encontrado.", id);
        return ResponseEntity.ok(servicioMapper.toResponse(s));
    }

    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<ServicioResponseDTO>> obtenerServiciosPorConductor(
            @PathVariable @Min(1) int idConductor) {
        log.info("Recibida petición GET en /api/servicios/conductor/{} para obtener servicios por conductor.", idConductor);

        List<Servicio> servicios = servicioService.buscarServiciosPorConductor(idConductor);
        log.info("Total de servicios encontrados para el conductor ID {}: {}", idConductor, servicios.size());
        return ResponseEntity.ok(servicios.stream().map(servicioMapper::toResponse).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable @Min(1) int id) {
        log.info("Recibida petición DELETE en /api/servicios/{} para eliminar servicio.", id);

        servicioService.eliminarServicioPorId(id);

        log.info("Servicio con ID: {} eliminado exitosamente.", id);
        return ResponseEntity.noContent().build();
    }
}
