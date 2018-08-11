package com.weblivraria.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.weblivraria.controller.page.PageWrapper;
import com.weblivraria.model.Editora;
import com.weblivraria.repository.EditoraRepository;
import com.weblivraria.repository.filter.EditoraFilter;
import com.weblivraria.service.EditoraService;

@Controller
@RequestMapping("/admin/editoras")
public class EditoraController {

	@Autowired
	private EditoraService editoraService;

	@Autowired
	private EditoraRepository editoraRepository;

	@RequestMapping("/add")
	public ModelAndView nova(Editora editora) {
		ModelAndView mv = new ModelAndView("admin/editoras/form");
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView cadastrar(@Valid Editora editora, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return nova(editora);
		}

		String msg;
		String redirect;

		if(editora.getId() != null) {
			editoraService.salvar(editora);
			msg = "Editora atualizada com sucesso!";
			redirect = "redirect:/admin/editoras/" + editora.getId();
		} else {
			editoraService.salvar(editora);
			msg = "Editora salva com sucesso!";
			redirect = "redirect:/admin/editoras?id=" + editora.getId();
		}		

		attributes.addFlashAttribute("mensagem", msg);
		return new ModelAndView(redirect);
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Integer id) {
		Optional<Editora> editora = editoraRepository.findById(id);

		if(!editora.isPresent())
			return new ModelAndView("redirect:/admin/editoras/add");

		ModelAndView  mv = nova(editora.get());
		mv.addObject(editora.get());
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(EditoraFilter editoraFilter, 
		@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("/admin/editoras/list");
		
		PageWrapper<Editora> paginaWrapper = new PageWrapper<>(editoraRepository.filtrar(editoraFilter, pageable), 
		httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public List<Editora> pesquisar(String nome) {
		validarTamanhoNome(nome);
		return editoraRepository.findByNomeStartingWithIgnoreCase(nome);
	}

	public void validarTamanhoNome(String nome) {
		if(StringUtils.isEmpty(nome) || nome.length() < 2) 
			throw new IllegalArgumentException();
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
}
