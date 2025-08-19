package com.centraltaxis.controller;

import com.centraltaxis.dto.cliente.ClienteCreateDTO;
import com.centraltaxis.dto.cliente.ClienteResponseDTO;
import com.centraltaxis.dto.cliente.ClienteUpdateDTO;
import com.centraltaxis.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // ------------------------------ CREATE ------------------------------ //
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(
            @Valid @RequestBody ClienteCreateDTO clienteCreateDTO) {
        ClienteResponseDTO nuevoCliente = clienteService.crearCliente(clienteCreateDTO);
        return ResponseEntity.status(201).body(nuevoCliente);
    }

    // ------------------------------ READ ------------------------------ //
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodosLosClientes() {
        List<ClienteResponseDTO> clientes = clienteService.listarTodosLosClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorId(
            @PathVariable @Min(1) int id) {
        ClienteResponseDTO cliente = clienteService.obtenerClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorTelefono(
            @PathVariable @Pattern(regexp = "^(\\+?[0-9]{1,3}[-.\\s]?)?([0-9]{2,4}[-.\\s]?){2,4}[0-9]{2,4}$", 
                                 message = "Teléfono inválido") String telefono) {
        ClienteResponseDTO cliente = clienteService.buscarClientePorTelefono(telefono);
        return ResponseEntity.ok(cliente);
    }

    // ------------------------------ UPDATE ------------------------------ //
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable @Min(1) int id,
            @Valid @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        ClienteResponseDTO clienteActualizado = clienteService.actualizarCliente(id, clienteUpdateDTO);
        return ResponseEntity.ok(clienteActualizado);
    }

    // ------------------------------ DELETE ------------------------------ //
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(
            @PathVariable @Min(1) int id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------ UTILIDADES ------------------------------ //
    @GetMapping("/{id}/existe")
    public ResponseEntity<Boolean> existeCliente(
            @PathVariable @Min(1) int id) {
        boolean existe = clienteService.existeCliente(id);
        return ResponseEntity.ok(existe);
    }
}