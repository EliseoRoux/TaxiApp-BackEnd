package com.centraltaxis.controller;

// Importamos las clases necesarias para el controlador
import com.centraltaxis.model.Conductor;
import com.centraltaxis.service.ConductorService;
// Importamos las anotaciones de Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// Importamos la anotación para definir un controlador REST
import org.springframework.web.bind.annotation.*;

@RestController
// Definimos la ruta base para este controlador
@RequestMapping("/api/conductor")
public class ConductorController {

    // Inyectamos el servicio ConductorService para manejar la lógica de negocio
    @Autowired
    private ConductorService conductorService;

    // Aquí se pueden definir los endpoints para manejar las solicitudes HTTP relacionadas con Conductor

    // CRUD
        // ------------------------------ CREATE ------------------------------ //
    
        // Crear un nuevo conductor

        // ------------------------------ READ ------------------------------ //

    // Leer todos los conductores


    // Leer un conductor por ID

        // ------------------------------ UPDATE ------------------------------ //

    // Actualizar un conductor existente

        // ------------------------------ DELETE ------------------------------ //

    // Eliminar un conductor por ID








}
