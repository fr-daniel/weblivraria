package com.weblivraria.controller.cliente;

import javax.servlet.http.HttpServletRequest;

import com.weblivraria.controller.page.PageWrapper;
import com.weblivraria.model.Oferta;
import com.weblivraria.repository.OfertaRepository;
import com.weblivraria.repository.filter.OfertaFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * OfertaController
 */
@Controller("userOfertaController")
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    private OfertaRepository ofertaRepository;
   
    @GetMapping
	public ModelAndView pesquisar(OfertaFilter ofertaFilter, 
		@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		
        ModelAndView mv = new ModelAndView("site/ofertas/list");
        ofertaFilter.setAtivo(true);
        ofertaFilter.setEstoqueMin(1);
		
		PageWrapper<Oferta> paginaWrapper = new PageWrapper<>(ofertaRepository.filtrar(ofertaFilter, pageable), 
		httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
}