package com.centraltaxis.controller;

// Importamos las clases necesarias para el controlador Reserva
import com.centraltaxis.model.Reserva;
import com.centraltaxis.service.ReservaService;
// Importamos las clases necesarias para manejar listas
import java.util.List;
// Importamos las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// Importamos la anotación para definir un controlador REST
import org.springframework.web.bind.annotation.*;

@RestController
// Definimos la ruta base para este controlador
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // CRUD
    // ------------------------------ CREATE ------------------------------ //

    // Crear una nueva reserva
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        // Llamamos al servicio para guardar la nueva reserva
        Reserva nuevaReserva = reservaService.guardarReserva(reserva);
        // Devolvemos la reserva creada con un código de estado 201 (Creado)
        return ResponseEntity.status(201).body(nuevaReserva);
    }

    // ------------------------------- READ ------------------------------ //

    // Obtener todas las reservas
    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodasLasReservas() {
        // Llamamos al servicio para listar todas las reservas
        List<Reserva> reservas = reservaService.listarReservas();
        if (reservas.isEmpty()) {
            // Si no hay reservas, devolvemos un código de estado 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Si hay reservas, las devolvemos con un código de estado 200 (OK)
            return ResponseEntity.ok(reservas);
        }
    }

    // Obtener una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable int id) {
        // Intentamos buscar la reserva por ID
        // Si no se encuentra, se lanzará una excepción que podemos manejar
        try {
            Reserva reserva = reservaService.buscarReservaPorId(id);
            return ResponseEntity.ok(reserva); // Si se encuentra, devolvemos la reserva
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Si no se encuentra, devolvemos un 404 Not Found
        }
    }

    // Obtener reservas por conductor
    @GetMapping("/conductor/{idConductor}")
    public ResponseEntity<List<Reserva>> obtenerReservasPorConductor(@PathVariable int idConductor) {
        // Llamamos al servicio para buscar reservas por conductor
        List<Reserva> reservas = reservaService.buscarReservasPorConductor(idConductor);
        if (reservas.isEmpty()) {
            // Si no hay reservas, devolvemos un código de estado 204 (No Content)
            return ResponseEntity.noContent().build();
        } else {
            // Si hay reservas, las devolvemos con un código de estado 200 (OK)
            return ResponseEntity.ok(reservas);
        }
    }

    // ------------------------------- UPDATE ------------------------------ //

    // Actualizar una reserva por ID
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable int id, @RequestBody Reserva reservaActualizada) {
        // Intentamos buscar la reserva por ID
        // Si no se encuentra, se lanzará una excepción que podemos manejar
        try {
            // Llamamos al servicio para buscar la reserva por ID
            Reserva reservaExistente = reservaService.buscarReservaPorId(id);
            // Actualizamos los campos de la reserva existente con los nuevos valores
            reservaExistente.setConductor(reservaActualizada.getConductor());
            reservaExistente.setCliente(reservaActualizada.getCliente());
            reservaExistente.setOrigen(reservaActualizada.getOrigen());
            reservaExistente.setDestino(reservaActualizada.getDestino());
            reservaExistente.setNPersona(reservaActualizada.getNPersona());
            reservaExistente.setRequisitos(reservaActualizada.getRequisitos());
            reservaExistente.setPrecio(reservaActualizada.getPrecio());
            reservaExistente.setPrecio10(reservaActualizada.getPrecio10());
            reservaExistente.setEurotaxi(reservaActualizada.isEurotaxi());
            reservaExistente.setFechaReserva(reservaActualizada.getFechaReserva());
            reservaExistente.setHora(reservaActualizada.getHora());
            // Guardamos la reserva actualizada
            Reserva reservaGuardada = reservaService.guardarReserva(reservaExistente);
            // Devolvemos la reserva actualizada con un código de estado 200 (OK)
            return ResponseEntity.ok(reservaGuardada);
        } catch (RuntimeException e) {
            // Si no se encuentra la reserva, devolvemos un código de estado 404 (Not Found)
            return ResponseEntity.notFound().build();
        }

    }

    // ------------------------------ DELETE ------------------------------ //

    // Eliminar una reserva por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable int id) {
        // Intentamos eliminar la reserva por ID
        try {
            reservaService.eliminarReservaPorId(id);
            // Devolvemos un código de estado 204 (No Content) si la eliminación fue exitosa
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Si no se encuentra la reserva, devolvemos un código de estado 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

}
