package com.centraltaxis.repository;

// Importamos la clase necesaria para esta interfaz, en este caso Reserva
import com.centraltaxis.model.Reserva;

import java.util.List;

// Importamos las clases necesarias para JPA
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ReservaRepository es una interfaz que extiende JpaRepository para manejar
 * operaciones CRUD
 * sobre la entidad Reserva.
 */
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
   // Aquí definimos métodos adicionales de consulta si es necesario
   /**
    * findAll() Lista todos los registros de la entidad
    * 
    * findById(Integer id) Busca un registro por ID
    * 
    * save(Reserva reserva) Guarda o actualiza un registro
    * 
    * deleteById(Integer id) Elimina un registro por ID
    * 
    * count() Cuenta cuántos registros hay en total
    * 
    * existsById(Integer id) Devuelve true si existe un registro con ese ID
    * 
    */

   List<Reserva> findByClienteId(int idConductor);
}
