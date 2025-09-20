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

// Importamos la clase Logger para registrar eventos
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/clientes")
@Validated
public class ClienteController {

    // Instancia estática del Logger para esta clase
    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clienteService;

    // ------------------------------ CREATE ------------------------------ //
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(
            @Valid @RequestBody ClienteCreateDTO clienteCreateDTO) {
        log.info("Recibida petición POST en /api/clientes para crear un nuevo cliente.");
        ClienteResponseDTO nuevoCliente = clienteService.crearCliente(clienteCreateDTO);
        log.info("Cliente creado con ID: {}", nuevoCliente.getIdCliente());
        return ResponseEntity.status(201).body(nuevoCliente);
    }

    // ------------------------------ READ ------------------------------ //
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodosLosClientes() {
        log.info("Recibida petición GET en /api/clientes para obtener todos los clientes.");
        List<ClienteResponseDTO> clientes = clienteService.listarTodosLosClientes();
        log.info("Total de clientes encontrados: {}", clientes.size());
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorId(
            @PathVariable @Min(1) int id) {
        log.info("Recibida petición GET en /api/clientes/{} para obtener un cliente específico.", id);
        ClienteResponseDTO cliente = clienteService.obtenerClientePorId(id);
        log.info("Cliente con ID: {} encontrado.", id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/telefono/{telefono}")
    public ResponseEntity<ClienteResponseDTO> obtenerClientePorTelefono(
            @PathVariable @Pattern(regexp = "^(\\+?[0-9]{1,3}[-.\\s]?)?([0-9]{2,4}[-.\\s]?){2,4}[0-9]{2,4}$",
                    message = "Teléfono inválido") String telefono) {
        log.info("Recibida petición GET en /api/clientes/telefono/{} para obtener un cliente por teléfono.", telefono);
        ClienteResponseDTO cliente = clienteService.buscarClientePorTelefono(telefono);
        log.info("Cliente con teléfono: {} encontrado.", telefono);
        return ResponseEntity.ok(cliente);
    }

    // ------------------------------ UPDATE ------------------------------ //
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarCliente(
            @PathVariable @Min(1) int id,
            @Valid @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        log.info("Recibida petición PUT en /api/clientes/{} para actualizar un cliente.", id);
        ClienteResponseDTO clienteActualizado = clienteService.actualizarCliente(id, clienteUpdateDTO);
        log.info("Cliente con ID: {} actualizado exitosamente.", id);
        return ResponseEntity.ok(clienteActualizado);
    }

    // ------------------------------ DELETE ------------------------------ //
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(
            @PathVariable @Min(1) int id) {
        log.info("Recibida petición DELETE en /api/clientes/{} para eliminar un cliente.", id);
        clienteService.eliminarCliente(id);
        log.info("Cliente con ID: {} eliminado exitosamente.", id);
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
