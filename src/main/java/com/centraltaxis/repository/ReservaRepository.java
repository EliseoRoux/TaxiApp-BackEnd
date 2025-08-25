package com.centraltaxis.repository;

// Importamos la clase necesaria para esta interfaz, en este caso Reserva
import com.centraltaxis.model.Reserva;

import java.time.LocalDate;
import java.util.List;

// Importamos las clases necesarias para JPA
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

   List<Reserva> findByConductor_IdConductor(int idConductor);

   // Consulta con rango de fechas
   @Query("SELECT r FROM Reserva r WHERE r.conductor.idConductor = :idConductor " +
         "AND (:fechaInicio IS NULL OR r.fechaReserva >= :fechaInicio) " +
         "AND (:fechaFin IS NULL OR r.fechaReserva <= :fechaFin)")
   List<Reserva> findByConductorAndFechaRango(
         @Param("idConductor") int idConductor,
         @Param("fechaInicio") LocalDate fechaInicio,
         @Param("fechaFin") LocalDate fechaFin);
}
