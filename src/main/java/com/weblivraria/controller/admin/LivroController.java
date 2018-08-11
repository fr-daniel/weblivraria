package com.weblivraria.controller.admin;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.weblivraria.controller.page.PageWrapper;
import com.weblivraria.controller.validator.LivroValidator;
import com.weblivraria.model.Autor;
import com.weblivraria.model.Categoria;
import com.weblivraria.model.Livro;
import com.weblivraria.repository.LivroRepository;
import com.weblivraria.repository.filter.LivroFilter;
import com.weblivraria.service.LivroService;
import com.weblivraria.session.TabelasLivroSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/livros")
public class LivroController {

	@Autowired
	private LivroRepository livroRepository;

	@Autowired
	private LivroService livroService;
	
	@Autowired
	private LivroValidator livroValidator;

	@Autowired
	private TabelasLivroSession tabelasLivroSession;

	@RequestMapping("/add")
	public ModelAndView novo(Livro livro) {
		ModelAndView mv = new ModelAndView("admin/livros/form");
		setUuid(livro);
		
		mv.addObject("autores", tabelasLivroSession.getAutores(livro.getUuid()));
		mv.addObject("categorias", tabelasLivroSession.getCategorias(livro.getUuid()));

		return mv;
	}

	@PostMapping("/save") 
	public ModelAndView salvar(@Valid Livro livro, BindingResult result, RedirectAttributes attributes) {
		validarLivro(livro, result);

		if(result.hasErrors()) {
			return novo(livro);
		}

		livroService.salvar(livro);

		attributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
		return new ModelAndView("redirect:/admin/livros/add");
	}

	@PutMapping("/autor/{uuid}/{idAutor}")
	public ModelAndView addAutor(@PathVariable("idAutor") Autor autor, @PathVariable("uuid") String uuid) {
		tabelasLivroSession.adicionarAutor(uuid, autor);
		return mvTabelaAutoresLivro(uuid);
	}

	@DeleteMapping("/autor/{uuid}/{idAutor}")
	public ModelAndView excluirAutor(@PathVariable("idAutor") Autor autor, @PathVariable("uuid") String uuid) {
		tabelasLivroSession.excluirAutor(uuid, autor);
		return mvTabelaAutoresLivro(uuid);
	}

	@PutMapping("/cat/{uuid}/{idCategoria}")
	public ModelAndView addCategoria(@PathVariable("idCategoria") Categoria categoria, @PathVariable("uuid") String uuid) {
		tabelasLivroSession.adicionarCategoria(uuid, categoria);
		return mvTabelaCategoriasLivro(uuid);
	}

	@DeleteMapping("/cat/{uuid}/{idCategoria}")
	public ModelAndView excluirCategoria(@PathVariable("idCategoria") Categoria categoria, @PathVariable("uuid") String uuid) {
		tabelasLivroSession.excluirCategoria(uuid, categoria);
		return mvTabelaCategoriasLivro(uuid);
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Livro livro) {
		setUuid(livro);

		livro.getAutores().forEach(
			autor -> tabelasLivroSession.adicionarAutor(livro.getUuid(), autor));
		livro.getCategorias().forEach(
			categoria -> tabelasLivroSession.adicionarCategoria(livro.getUuid(), categoria));
		
		ModelAndView mv = novo(livro);
		mv.addObject(livro);
		return mv;
	}

	@GetMapping
	public ModelAndView pesquisar(LivroFilter livroFilter, 
		@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("/admin/livros/list");
		
		PageWrapper<Livro> paginaWrapper = new PageWrapper<>(livroRepository.filtrar(livroFilter, pageable), 
		httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	private ModelAndView mvTabelaAutoresLivro(String uuid) {
		ModelAndView mv = new ModelAndView("admin/livros/tabela-autores-livro");
		mv.addObject("autores", tabelasLivroSession.getAutores(uuid));
		return mv;
	}

	private ModelAndView mvTabelaCategoriasLivro(String uuid) {
		ModelAndView mv = new ModelAndView("admin/livros/tabela-categorias-livro");
		mv.addObject("categorias", tabelasLivroSession.getCategorias(uuid));
		return mv;
	}

	private void validarLivro(Livro livro, BindingResult result) {
		livro.setAutores(tabelasLivroSession.getAutores(livro.getUuid()));
		livro.setCategorias(tabelasLivroSession.getCategorias(livro.getUuid()));
		livroValidator.validate(livro, result);
	}

	private void setUuid(Livro livro) {
		if (StringUtils.isEmpty(livro.getUuid())) {
			livro.setUuid(UUID.randomUUID().toString());
		}
	}

	@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public List<Livro> pesquisar(String titulo) {
		validarTamanhoNome(titulo);
		return livroRepository.findByTituloContainingIgnoreCase(titulo);
	}

	public void validarTamanhoNome(String titulo) {
		if(StringUtils.isEmpty(titulo) || titulo.length() < 2) 
			throw new IllegalArgumentException();
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Void> tratarIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().build();
	}
}
