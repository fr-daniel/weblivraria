package com.weblivraria.repository.helper.livro;

import java.util.List;

import com.weblivraria.model.Livro;
import com.weblivraria.repository.filter.LivroFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * LivroQueries
 */
public interface LivroRepositoryQueries {

    public Page<Livro> filtrar(LivroFilter filtro, Pageable pageable);
    public Page<Livro> filtrar(Pageable pageable);
    public List<Livro> filtrar(LivroFilter filtro);
    public List<Livro> livroSemOferta();

}