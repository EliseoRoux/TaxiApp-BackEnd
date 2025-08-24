package com.centraltaxis.repository;

// Importamos la clase necesaria para esta interfaz, en este caso Conductor
import com.centraltaxis.model.Conductor;

import java.util.List;

// Importamos las clases necesarias para JPA
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * ConductorRepository es una interfaz que extiende JpaRepository para manejar
 * operaciones CRUD
 * sobre la entidad Conductor.
 */
@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Integer> {
    // Aquí definimos métodos adicionales de consulta si es necesario
    /**
     * findAll() Lista todos los registros de la entidad
     * 
     * findById(Integer id) Busca un registro por ID
     * 
     * save(Conductor conductor) Guarda o actualiza un registro
     * 
     * deleteById(Integer id) Elimina un registro por ID
     * 
     * count() Cuenta cuántos registros hay en total
     * 
     * existsById(Integer id) Devuelve true si existe un registro con ese ID
     * 
     */

    @Query("SELECT c FROM Conductor c WHERE c.deuda > 0")
    List<Conductor> findConductoresConDeuda();

}
