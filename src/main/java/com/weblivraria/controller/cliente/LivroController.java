package com.weblivraria.controller.cliente;

import com.weblivraria.repository.LivroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * LivroController
 */
@Controller("userLivroController")
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    LivroRepository livroRepository;

    @GetMapping
    public ModelAndView livros() {
        ModelAndView mv = new ModelAndView("site/livros/list");
        mv.addObject("livros", livroRepository.findAll());
        return mv;
    }

    @GetMapping("/search")
    public ModelAndView livrosSearch(@RequestParam("search") String search) {
        ModelAndView mv = new ModelAndView("site/livros/list");
        mv.addObject("livros", livroRepository.findByTituloContainingIgnoreCase(search));
        return mv;
    }

    @GetMapping("/{idUrl}")
    public ModelAndView livro(@PathVariable("idUrl") String idUrl) {
        ModelAndView mv = new ModelAndView("site/livros/livro");
        mv.addObject("livro", livroRepository.findByIdUrl(idUrl));
        return mv;
    }
    
}