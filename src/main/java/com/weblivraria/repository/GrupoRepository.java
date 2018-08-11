package com.weblivraria.repository;

import java.util.Optional;

import com.weblivraria.model.Grupo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    Optional<Grupo> findByCodigoIgnoreCase(String nome);
    
}