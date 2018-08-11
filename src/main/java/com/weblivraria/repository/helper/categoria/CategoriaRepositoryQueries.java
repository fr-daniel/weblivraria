package com.weblivraria.repository.helper.categoria;

import java.util.List;

import com.weblivraria.dto.CategoriaDTO;
import com.weblivraria.model.Categoria;
import com.weblivraria.repository.filter.CategoriaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaRepositoryQueries {

	public Page<Categoria> filtrar(CategoriaFilter categoriaFilter, Pageable pageable);
	public Page<Categoria> filtrar(Pageable pageable);
	public List<CategoriaDTO> porTitulo(String titulo);
	
}
