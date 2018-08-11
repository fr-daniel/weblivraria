package com.weblivraria.repository;

import java.util.Optional;

import com.weblivraria.model.Oferta;
import com.weblivraria.repository.helper.oferta.OfertaRepositoryQueries;

import org.springframework.data.repository.CrudRepository;

public interface OfertaRepository extends CrudRepository<Oferta, Long>, OfertaRepositoryQueries{

	Optional<Oferta> findById(Long id);
	Oferta findOneById(Long id);
	
	
}
