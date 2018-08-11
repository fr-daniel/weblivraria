package com.weblivraria.repository.helper.autor;

import java.util.List;

import com.weblivraria.dto.AutorDTO;
import com.weblivraria.model.Autor;
import com.weblivraria.repository.filter.AutorFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AutorRepositoryQueries {

    public Page<Autor> filtrar(AutorFilter filtro, Pageable pageable);
    public Page<Autor> filtrar(Pageable pageable);
    public List<AutorDTO> porNomeOuSobrenome(String idOuNome);

}