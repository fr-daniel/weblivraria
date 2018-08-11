package com.weblivraria.repository;

import java.util.List;
import java.util.Optional;

import com.weblivraria.model.Editora;
import com.weblivraria.repository.helper.editora.EditoraRepositoryQueries;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EditoraRepository extends JpaRepository<Editora, Integer>, EditoraRepositoryQueries {
    
    public Optional<Editora> findById(Integer id);
    public List<Editora> findByNomeStartingWithIgnoreCase(String nome);
    public Editora findByIdUrl(String idUrl);

}