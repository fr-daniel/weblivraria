package com.weblivraria.repository.helper.editora;

import com.weblivraria.model.Editora;
import com.weblivraria.repository.filter.EditoraFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EditoraRepositoryQueries {

	public Page<Editora> filtrar(EditoraFilter filtro, Pageable pageable);
	public Page<Editora> filtrar(Pageable pageable);

}

