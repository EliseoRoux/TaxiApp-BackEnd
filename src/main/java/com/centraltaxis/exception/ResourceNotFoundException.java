package com.centraltaxis.exception;

// Excepción personalizada para manejar recursos no encontrados
// Por ejemplo, cuando un cliente o servicio no existe en la base de datos
public class ResourceNotFoundException extends RuntimeException {
    // Constructor que recibe un mensaje de error
    public ResourceNotFoundException(String mensaje) {
        // Llama al constructor de la clase padre (RuntimeException) con el mensaje de error
        super(mensaje);
    }
}