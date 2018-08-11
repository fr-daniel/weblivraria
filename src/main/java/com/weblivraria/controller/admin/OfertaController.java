package com.weblivraria.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.weblivraria.controller.page.PageWrapper;
import com.weblivraria.model.Oferta;
import com.weblivraria.repository.OfertaRepository;
import com.weblivraria.repository.filter.OfertaFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/ofertas")
public class OfertaController {

	@Autowired
	private OfertaRepository ofertaReposirtory;

	@RequestMapping("/add")
	public ModelAndView nova(Oferta oferta) {
		ModelAndView mv = new ModelAndView("admin/ofertas/form");
		mv.addObject("oferta", oferta);
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView cadastrar(@Valid Oferta oferta, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return nova(oferta);
		}

		String msg;
		String redirect;

		if(oferta.getId() != null) {
			ofertaReposirtory.save(oferta);
			msg = "Oferta atualizada com sucesso!";
			redirect = "redirect:/admin/ofertas/" + oferta.getId();
		} else {
			ofertaReposirtory.save(oferta);
			msg = "Oferta salva com sucesso!";
			redirect = "redirect:/admin/ofertas?id=" + oferta.getId();
		}		

		attributes.addFlashAttribute("mensagem", msg);
		return new ModelAndView(redirect);
	}

	@GetMapping
	public ModelAndView pesquisar(OfertaFilter ofertaFilter, 
		@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("/admin/ofertas/list");
		
		PageWrapper<Oferta> paginaWrapper = new PageWrapper<>(ofertaReposirtory.filtrar(ofertaFilter, pageable), 
		httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

}
