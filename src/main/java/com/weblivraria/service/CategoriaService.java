package com.weblivraria.service;

import com.github.slugify.Slugify;
import com.weblivraria.model.Categoria;
import com.weblivraria.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public Categoria salvar(Categoria categoria) {
        categoriaRepository.save(categoria);

        Slugify slg = new Slugify();
        String tituloAndId = categoria.getTitulo() + "-" + categoria.getId().toString();
        String result = slg.slugify(tituloAndId);
        categoria.setIdUrl(result);
    
        return categoriaRepository.save(categoria);
    }
    
}