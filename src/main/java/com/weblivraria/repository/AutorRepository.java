package com.weblivraria.repository;

import java.util.Optional;

import com.weblivraria.model.Autor;
import com.weblivraria.repository.helper.autor.AutorRepositoryQueries;

import org.springframework.data.repository.CrudRepository;

public interface AutorRepository extends CrudRepository<Autor, Integer>, AutorRepositoryQueries{
    
    Optional<Autor> findById(Integer id);
    Autor findOneById(Integer id);
    Autor findOneByIdUrl(String idUrl);
    Autor findByIdUrl(String idUrl);
    
}
