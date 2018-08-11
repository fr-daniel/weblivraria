package com.weblivraria.controller.cliente;

import com.weblivraria.repository.EditoraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * AutoresController
 */

@Controller("userEditorasController")
@RequestMapping("/editoras")
public class EditorasController {

    @Autowired
    EditoraRepository editoraRepository;

    @GetMapping
    public ModelAndView editoras() {
        ModelAndView mv = new ModelAndView("site/editoras/list");
        mv.addObject("editoras", editoraRepository.findAll());
        return mv;
    }

    @GetMapping("/{idUrl}")
    public ModelAndView editora(@PathVariable("idUrl") String idUrl) {
        ModelAndView mv = new ModelAndView("site/editoras/editora");
        mv.addObject("editora", editoraRepository.findByIdUrl(idUrl));
        return mv;
    }

    
}