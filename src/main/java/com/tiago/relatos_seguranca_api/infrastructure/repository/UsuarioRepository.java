package com.tiago.relatos_seguranca_api.infrastructure.repository;

import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByNameContainingIgnoreCase(String name);

    /*@Query("SELECT u FROM Usuario u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
      List<Usuario> findByName(@Param("name") String name);*/

}
