package com.centraltaxis.repository;

import com.centraltaxis.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Busca un usuario por su nombre de usuario
    Optional<Usuario> findByUsername(String username);

    // Comprueba si un nombre de usuario ya existe
    Boolean existsByUsername(String username);
}