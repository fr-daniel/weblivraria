package com.weblivraria.controller;

import com.weblivraria.repository.CategoriaRepository;
import com.weblivraria.repository.OfertaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * HomeController
 */

@Controller
public class HomeController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    OfertaRepository ofertasRepository;


    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("site/index");
        mv.addObject("categorias", categoriaRepository.findAll());
        mv.addObject("new_ofertas", ofertasRepository.ultimasOfertas(10));
        return mv;
    }
    
}