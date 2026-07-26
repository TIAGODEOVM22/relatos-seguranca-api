package com.tiago.relatos_seguranca_api.infrastructure.repository;

import com.tiago.relatos_seguranca_api.infrastructure.entity.Relato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatoRepository extends JpaRepository<Relato, Long> {


}
