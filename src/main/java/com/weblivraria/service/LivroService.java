package com.weblivraria.service;

import com.github.slugify.Slugify;
import com.weblivraria.model.Livro;
import com.weblivraria.repository.LivroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public Livro salvar(Livro livro) {
        livroRepository.save(livro);

        Slugify slg = new Slugify();
        String tituloAndId = livro.getTitulo() + "-" + livro.getId().toString();
        String result = slg.slugify(tituloAndId);
        livro.setIdUrl(result);
    
        return livroRepository.save(livro);
    }
    
}