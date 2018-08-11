package com.weblivraria.service;

import com.github.slugify.Slugify;
import com.weblivraria.model.Editora;
import com.weblivraria.repository.EditoraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditoraService {

    @Autowired
    private EditoraRepository editoraRepository;

    @Transactional
    public Editora salvar(Editora editora) {
        editoraRepository.save(editora);

        Slugify slg = new Slugify();
        String nomeAndId = editora.getNome() + "-" + editora.getId().toString();
        String result = slg.slugify(nomeAndId);
        editora.setIdUrl(result);
    
        return editoraRepository.save(editora);
    }
    
}