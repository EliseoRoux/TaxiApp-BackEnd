package com.centraltaxis.repository;

import com.centraltaxis.model.Servicio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ServicioRepository es una interfaz que extiende JpaRepository para manejar
 * operaciones CRUD
 * sobre la entidad Servicio.
 */
@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
   // Aquí definimos métodos adicionales de consulta si es necesario
   /**
    * findAll() Lista todos los registros de la entidad
    * 
    * findById(Integer id) Busca un registro por ID
    * 
    * save(Servicio servicio) Guarda o actualiza un registro
    * 
    * deleteById(Integer id) Elimina un registro por ID
    * 
    * count() Cuenta cuántos registros hay en total
    * 
    * existsById(Integer id) Devuelve true si existe un registro con ese ID
    * 
    */

   List<Servicio> findByConductorId(int idConductor);

}
