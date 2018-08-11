package com.weblivraria.repository;

import java.util.Optional;

import com.weblivraria.model.Categoria;
import com.weblivraria.repository.helper.categoria.CategoriaRepositoryQueries;

import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer>, CategoriaRepositoryQueries {

	Optional<Categoria> findById(Integer id);
	Categoria findOneById(Integer id);
	Categoria findByIdUrl(String idUrl);
	
}
