package com.tiago.relatos_seguranca_api.security.repository;

import com.tiago.relatos_seguranca_api.security.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
