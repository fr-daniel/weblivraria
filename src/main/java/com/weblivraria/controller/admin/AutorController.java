package com.weblivraria.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.weblivraria.controller.page.PageWrapper;
import com.weblivraria.dto.AutorDTO;
import com.weblivraria.model.Autor;
import com.weblivraria.repository.AutorRepository;
import com.weblivraria.repository.filter.AutorFilter;
import com.weblivraria.service.AutorService;

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
@RequestMapping("/admin/autores")
public class AutorController {

	@Autowired
	private AutorService autorService;

	@Autowired
	private AutorRepository autorRepository;

	@RequestMapping("/add")
	public ModelAndView novo(Autor autor) {
		ModelAndView mv = new ModelAndView("admin/autores/form");
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView cadastrar(@Valid Autor autor, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(autor);
		}

		String msg;
		String redirect;

		if(autor.getId() != null) {
			autorService.salvar(autor);
			msg = "Autor atualizado com sucesso!";
			redirect = "redirect:/admin/autores/" + autor.getId();
		} else {
			autorService.salvar(autor);
			msg = "Autor salvo com sucesso!";
			redirect = "redirect:/admin/autores?id=" + autor.getId();
		}		

		attributes.addFlashAttribute("mensagem", msg);
		return new ModelAndView(redirect);
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Integer id) {
		Optional<Autor> autor = autorRepository.findById(id);

		if(!autor.isPresent())
			return new ModelAndView("redirect:/admin/autores/add");

		ModelAndView  mv = novo(autor.get());
		mv.addObject(autor.get());
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(AutorFilter autorFilter, 
		@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("/admin/autores/list");
		
		PageWrapper<Autor> paginaWrapper = new PageWrapper<>(autorRepository.filtrar(autorFilter, pageable), 
		httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<AutorDTO> pesquisar(String nomeOuSobrenome) {
		return autorRepository.porNomeOuSobrenome(nomeOuSobrenome);
	}
}
