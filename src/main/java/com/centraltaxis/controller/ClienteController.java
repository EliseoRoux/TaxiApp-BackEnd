package com.centraltaxis.controller;

// Importamos las clases necesarias para el controlador
import com.centraltaxis.model.Cliente;
import com.centraltaxis.service.ClienteService;
// Importamos las clases necesarias para manejar listas
import java.util.List;
// Importamos las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// Importamos la anotación para definir un controlador REST
import org.springframework.web.bind.annotation.*;

// Importamos la anotación para definir el controlador como un controlador REST
@RestController
// Definimos la ruta base para este controlador
@RequestMapping("/api/clientes")
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
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        // Llamamos al servicio para guardar el cliente
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
        if (clientes.isEmpty()) {
            // Si no hay clientes, devolvemos un código de estado 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Si hay clientes, los devolvemos con un código de estado 200 (OK)
            return ResponseEntity.ok(clientes);
        }
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable int id) {
        // Intentamos buscar el cliente por ID
        // Si no se encuentra, se lanzará una excepción que podemos manejar
        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            return ResponseEntity.ok(cliente);
            // Si ocurre una excepción, la manejamos aquí
        } catch (RuntimeException e) {
            // Manejo de excepciones, podríamos devolver un mensaje de error o un código de
            // estado HTTP
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------ UPDATE ------------------------------ //

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable int id, @RequestBody Cliente clienteActualizado) {
        // Intentamos buscar el cliente por ID para actualizarlo
        // Si no se encuentra, se lanzará una excepción que podemos manejar
        try {
            // Buscamos el cliente por el ID
            Cliente clienteExistente = clienteService.buscarClientePorId(id);
            // Actualizamos los datos del cliente existente
            clienteExistente.setNombre(clienteActualizado.getNombre());
            clienteExistente.setTelefono(clienteActualizado.getTelefono());
            // Llamamos al servicio para guardar el cliente actualizado
            Cliente cliente = clienteService.guardarCliente(clienteExistente);
            // Devolvemos el cliente actualizado con un código de estado 200 (OK)
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            // Manejo de excepciones, podríamos devolver un mensaje de error o un código de
            // estado HTTP
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------ DELETE ------------------------------ //

    // Eliminar un cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
        // Intentamos eliminar el cliente por ID
        // Si no se encuentra, se lanzará una excepción que podemos manejar
        try {
            clienteService.eliminarClientePorId(id);
            // Devolvemos un código de estado 204 (No Content) si la eliminación fue exitosa
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Manejo de excepciones, podríamos devolver un mensaje de error o un código de
            // estado HTTP
            return ResponseEntity.notFound().build();
        }
    }

}
