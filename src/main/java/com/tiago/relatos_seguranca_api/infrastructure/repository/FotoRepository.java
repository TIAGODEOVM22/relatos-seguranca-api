package com.tiago.relatos_seguranca_api.infrastructure.repository;

import com.tiago.relatos_seguranca_api.infrastructure.entity.Foto;
import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    long countByRelatoId(Long relatoId);
    List<Foto> findByRelato(Relato relato);


}
