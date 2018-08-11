package com.weblivraria.service;

import com.github.slugify.Slugify;
import com.weblivraria.model.Autor;
import com.weblivraria.repository.AutorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public Autor salvar(Autor autor) {
        autorRepository.save(autor);

        Slugify slg = new Slugify();
        String nomeAndId = autor.getNomeCompleto() + "-" + autor.getId().toString();
        String result = slg.slugify(nomeAndId);
        autor.setIdUrl(result);
    
        return autorRepository.save(autor);
    }
    
}