package com.weblivraria.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.weblivraria.controller.page.PageWrapper;
import com.weblivraria.dto.CategoriaDTO;
import com.weblivraria.model.Categoria;
import com.weblivraria.repository.CategoriaRepository;
import com.weblivraria.repository.filter.CategoriaFilter;
import com.weblivraria.service.CategoriaService;

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
@RequestMapping("/admin/categorias")
public class CategoriaController {

	@Autowired
	CategoriaService categoriaService;

	@Autowired
	CategoriaRepository categoriaRepositiry;


	@RequestMapping("/add")
	public ModelAndView nova(Categoria categoria) {
		ModelAndView mv = new ModelAndView("admin/categorias/form");
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView cadastrar(@Valid Categoria categoria, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return nova(categoria);
		}

		String msg;
		String redirect;

		if(categoria.getId() != null) {
			categoriaService.salvar(categoria);
			msg = "Categoria atualizada com sucesso!";
			redirect = "redirect:/admin/categorias/" + categoria.getId();
		} else {
			categoriaService.salvar(categoria);
			msg = "Categoria salva com sucesso!";
			redirect = "redirect:/admin/categorias?id=" + categoria.getId();
		}		

		attributes.addFlashAttribute("mensagem", msg);
		return new ModelAndView(redirect);
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Integer id) {
		Optional<Categoria> categoria = categoriaRepositiry.findById(id);

		if(!categoria.isPresent())
			return new ModelAndView("redirect:/admin/categorias/add");

		ModelAndView  mv = nova(categoria.get());
		mv.addObject(categoria.get());
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(CategoriaFilter categoriaFilter, 
		@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("/admin/categorias/list");
		
		PageWrapper<Categoria> paginaWrapper = new PageWrapper<>(categoriaRepositiry.filtrar(categoriaFilter, pageable), 
		httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<CategoriaDTO> pesquisar(String titulo) {
		return categoriaRepositiry.porTitulo(titulo);
	}
}
