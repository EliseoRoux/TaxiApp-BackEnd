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

@RestController
@RequestMapping("/api/servicios")
@Validated
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ServicioMapper servicioMapper;

    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crearServicio(@Valid @RequestBody ServicioCreateDTO servicioDTO) {
        Servicio servicioCreado = servicioService.crearServicio(servicioDTO);
        return ResponseEntity.status(201).body(servicioMapper.toResponse(servicioCreado));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> actualizarServicioParcialmente(
            @PathVariable @Min(1) int id,
            @RequestBody ServicioPatchDTO updates) {
        Servicio actualizado = servicioService.actualizarServicioParcialmente(id, updates);
        return ResponseEntity.ok(servicioMapper.toResponse(actualizado));
    }

    // --- MÉTODOS DE LECTURA Y BORRADO (SIN CAMBIOS) ---

    @GetMapping
    public ResponseEntity<List<ServicioResponseDTO>> obtenerTodosLosServicios() {
        List<Servicio> servicios = servicioService.listarServicios();
        return ResponseEntity.ok(servicios.stream().map(servicioMapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> obtenerServicioPorId(@PathVariable @Min(1) int id) {
        Servicio s = servicioService.buscarServicioPorId(id);
        return ResponseEntity.ok(servicioMapper.toResponse(s));
    }

    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<ServicioResponseDTO>> obtenerServiciosPorConductor(
            @PathVariable @Min(1) int idConductor) {
        List<Servicio> servicios = servicioService.buscarServiciosPorConductor(idConductor);
        return ResponseEntity.ok(servicios.stream().map(servicioMapper::toResponse).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable @Min(1) int id) {
        servicioService.eliminarServicioPorId(id);
        return ResponseEntity.noContent().build();
    }
}