package com.centraltaxis.repository;

// Importamos las clases para esta interfaz, en este caso Cliente
import com.centraltaxis.model.Cliente;
// Importamos las clases necesarias para JPA
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ClienteRepository es una interfaz que extiende JpaRepository para manejar
 * operaciones CRUD
 * sobre la entidad Cliente.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
   // Aquí definimos métodos adicionales de consulta si es necesario
   /**
    * findAll() Lista todos los registros de la entidad
    * 
    * findById(Integer id) Busca un registro por ID
    * 
    * save(Cliente cliente) Guarda o actualiza un registro
    * 
    * deleteById(Integer id) Elimina un registro por ID
    * 
    * count() Cuenta cuántos registros hay en total
    * 
    * existsById(Integer id) Devuelve true si existe un registro con ese ID
    * 
    */

   String findByTelefono(String telefono);

}
