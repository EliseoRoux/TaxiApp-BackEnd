package com.centraltaxis.controller;

// Importamos las clases necesarias para el controlador
import com.centraltaxis.model.Cliente;
import com.centraltaxis.service.ClienteService;
// Importamos las clases necesarias para manejar listas
import java.util.List;
// Importamos las anotaciones de validación
import javax.validation.*;
import javax.validation.constraints.Min;

// Importamos las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
// Importamos la anotación para definir un controlador REST
import org.springframework.web.bind.annotation.*;

// Importamos la anotación para definir el controlador como un controlador REST
@RestController
// Definimos la ruta base para este controlador
@RequestMapping("/api/clientes")
// Usamos @Validated para habilitar la validación de los parámetros de entrada
@Validated
public class ClienteController {

    // Inyectamos el servicio ClienteService para manejar la lógica de negocio
    @Autowired
    private ClienteService clienteService;

    // Aquí se pueden definir los endpoints para manejar las solicitudes HTTP
    // relacionadas con Cliente
    // CRUD

    // ------------------------------ CREATE ------------------------------ //

    // Crear un nuevo cliente
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        // Llamamos al servicio para guardar el nuevo cliente
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        // Devolvemos el cliente creado con un código de estado 201 (Creado)
        return ResponseEntity.status(201).body(nuevoCliente);
    }

    // ------------------------------ READ ------------------------------ //

    // Obtenemos todos los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        // Llamamos al servicio para listar todos los clientes
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes); // Devolvemos la lista de clientes con un código de estado 200 (OK)
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable @Min(1) int id) {
        Cliente cliente = clienteService.buscarClientePorId(id); // Lanza ResourceNotFoundException si no existe
        return ResponseEntity.ok(cliente); // Si se encuentra, devolvemos el cliente
    }

    // ------------------------------ UPDATE ------------------------------ //

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable @Min(1) int id,
            @Valid @RequestBody Cliente clienteActualizado) {
        // Buscamos el cliente por el ID
        Cliente clienteExistente = clienteService.buscarClientePorId(id);
        // Actualizamos los datos del cliente existente
        clienteExistente.setNombre(clienteActualizado.getNombre());
        clienteExistente.setTelefono(clienteActualizado.getTelefono());
        // Llamamos al servicio para guardar el cliente actualizado
        Cliente cliente = clienteService.guardarCliente(clienteExistente);
        // Devolvemos el cliente actualizado con un código de estado 200 (OK)
        return ResponseEntity.ok(cliente);
    }

    // ------------------------------ DELETE ------------------------------ //

    // Eliminar un cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable @Min(1) int id) {
        // Intentamos eliminar el cliente por ID
        clienteService.eliminarClientePorId(id);
        // Devolvemos un código de estado 204 (No Content) si la eliminación fue exitosa
        return ResponseEntity.noContent().build();
    }
}
