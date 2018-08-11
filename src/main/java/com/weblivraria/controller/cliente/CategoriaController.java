package com.weblivraria.controller.cliente;

import com.weblivraria.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * CategoriaController
 */

@Controller("userCategoriaController")
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping("/{idUrl}")
    public ModelAndView categoria(@PathVariable("idUrl") String idUrl) {
        ModelAndView mv = new ModelAndView("site/categorias/categoria");
        System.out.println(idUrl);
        mv.addObject("categoria", categoriaRepository.findByIdUrl(idUrl));
        return mv;
    }
    
}