package com.centraltaxis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejamos los errores de validación (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // Este método captura las excepciones de validación y devuelve un mapa con los errores
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Creamos un mapa para almacenar los errores de validación
        Map<String, String> errores = new HashMap<>();
        // Iteramos sobre los errores de validación y los añadimos al mapa
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });
        // Devolvemos el mapa de errores con un código de estado 400 (Bad Request)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }

    // Maneja errores cuando no se encuentra un recurso (ej: cliente no existe)
    @ExceptionHandler(ResourceNotFoundException.class)
    // Este método captura la excepción ResourceNotFoundException y devuelve un mensaje de error
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        // Devolvemos el mensaje de error con un código de estado 404 (Not Found)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Maneja otros errores inesperados (genérico)
    @ExceptionHandler(Exception.class)
    // Este método captura cualquier otra excepción y devuelve un mensaje genérico
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        // Devolvemos un mensaje genérico de error con un código de estado 500 (Internal Server Error)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}