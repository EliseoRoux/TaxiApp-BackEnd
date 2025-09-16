package com.centraltaxis.controller;

import com.centraltaxis.dto.servicio.ServicioCreateDTO;
import com.centraltaxis.dto.servicio.ServicioPatchDTO;
import com.centraltaxis.dto.servicio.ServicioResponseDTO;
import com.centraltaxis.dto.servicio.ServicioUpdateDTO;
import com.centraltaxis.mapper.ServicioMapper;
import com.centraltaxis.model.*;
import com.centraltaxis.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
@Validated
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ServicioMapper servicioMapper;

    // ------------------------------ CREATE ------------------------------

    // CREATE (POST) -> recibe ServicioCreateDTO, devuelve ServicioResponseDTO
    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crearServicio(@Valid @RequestBody ServicioCreateDTO servicio) {
        //  Mapear DTO -> Entidad 
        Servicio entidad = servicioMapper.toEntity(servicio);
    
        Servicio creado = servicioService.guardarServicio(entidad);
        //  Entidad -> DTO respuesta
        return ResponseEntity.status(201).body(servicioMapper.toResponse(creado));
    }

    // ------------------------------ READ ------------------------------
    
    // List GET
    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> obtenerTodosLosServicios() {
        List<Servicio> servicios = servicioService.listarServicios();
        List<ServicioResponseDTO> out = servicios.stream()
            .map(servicioMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

     // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> obtenerServicioPorId(@PathVariable @Min(1) int id) {
        Servicio s = servicioService.buscarServicioPorId(id);
        return ResponseEntity.ok(servicioMapper.toResponse(s));
    }

    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<Servicio>> obtenerServiciosPorConductor(@PathVariable @Min(1) int idConductor) {
        return ResponseEntity.ok(servicioService.buscarServiciosPorConductor(idConductor));
    }

    // ------------------------------ UPDATE ------------------------------
    
    // UPDATE (PUT) -> recibe ServicioUpdateDTO, devuelve ServicioResponseDTO
    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> actualizarServicio(
            @PathVariable @Min(1) int id,
            @Valid @RequestBody ServicioUpdateDTO updates) {

        // 1) Obtener entidad actual
        Servicio existente = servicioService.buscarServicioPorId(id);
        // 2) Merge DTO -> Entidad
        servicioMapper.mergeIntoEntity(updates, existente);
        // 3) Delegar lógica a Service 
        Servicio actualizado = servicioService.actualizarServicio(id, existente);
        // 4) Responder
        return ResponseEntity.ok(servicioMapper.toResponse(actualizado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> actualizarServicioParcialmente(
            @PathVariable @Min(1) int id,
            @RequestBody ServicioPatchDTO updates) {

        // 1) Obtener entidad actual
        Servicio existente = servicioService.buscarServicioPorId(id);
        // 2) Aplicar sólo campos no-nulos
        servicioMapper.applyPatch(updates, existente);
        // 3) Delegar a Service 
        Servicio actualizado = servicioService.actualizarServicio(id, existente);
        // 4) Responder
        return ResponseEntity.ok(servicioMapper.toResponse(actualizado));
    }

    // ------------------------------ DELETE ------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable @Min(1) int id) {
        servicioService.eliminarServicioPorId(id);
        return ResponseEntity.noContent().build();
    }

}