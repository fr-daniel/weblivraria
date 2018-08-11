package com.weblivraria.controller.cliente;

import com.weblivraria.repository.AutorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * AutoresController
 */

@Controller("userAutorController")
@RequestMapping("/autores")
public class AutoresController {

    @Autowired
    AutorRepository autoresRepository;

    @GetMapping
    public ModelAndView autores() {
        ModelAndView mv = new ModelAndView("site/autores/list");
        mv.addObject("autores", autoresRepository.findAll());
        return mv;
    }

    @GetMapping("/{idUrl}")
    public ModelAndView autores(@PathVariable("idUrl") String idUrl) {
        ModelAndView mv = new ModelAndView("site/autores/autor");
        mv.addObject("autor", autoresRepository.findByIdUrl(idUrl));
        return mv;
    }

    
}