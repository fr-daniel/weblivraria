package com.weblivraria.repository.helper.oferta;

import java.util.List;

import com.weblivraria.model.Oferta;
import com.weblivraria.repository.filter.OfertaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfertaRepositoryQueries {

    public Page<Oferta> filtrar(OfertaFilter filtro, Pageable pageable);
    public Page<Oferta> filtrar(Pageable pageable);
    public List<Oferta> filtrar(OfertaFilter filtro);
    public List<Oferta> ultimasOfertas(Integer limit);

}