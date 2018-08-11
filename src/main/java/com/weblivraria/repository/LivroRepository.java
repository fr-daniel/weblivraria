package com.weblivraria.repository;

import java.util.List;
import java.util.Optional;

import com.weblivraria.model.Livro;
import com.weblivraria.repository.helper.livro.LivroRepositoryQueries;

import org.springframework.data.repository.CrudRepository;

public interface LivroRepository extends CrudRepository<Livro, Integer>, LivroRepositoryQueries{
    
    Optional<Livro> findById(Integer id);
    Livro findOneById(Integer id);
    Livro findOneByIdUrl(String idUrl);
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    Livro findByIdUrl(String idUrl);
}
