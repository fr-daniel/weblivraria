package com.weblivraria.controller.admin;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.weblivraria.controller.page.PageWrapper;
import com.weblivraria.controller.validator.UsuarioValidator;
import com.weblivraria.model.Grupo;
import com.weblivraria.model.Usuario;
import com.weblivraria.repository.GrupoRepository;
import com.weblivraria.repository.UsuarioRepository;
import com.weblivraria.repository.filter.UsuarioFilter;
import com.weblivraria.service.UsuarioService;
import com.weblivraria.service.exception.CpfUsuarioJaCadastradoException;
import com.weblivraria.service.exception.EmailUsuarioJaCadastradoException;
import com.weblivraria.service.exception.SenhaObrigatoriaUsuarioException;

@Controller("adminUsuarioController")
@RequestMapping("/admin/usuarios")
public class UsuarioController {

	@Autowired
	private GrupoRepository gruposRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired UsuarioValidator usuarioValidator;

	@GetMapping("/add")
    public ModelAndView cadastro(Usuario usuario) {
		ModelAndView mv = new ModelAndView("admin/usuarios/form");
		mv.addObject("grupos", gruposRepository.findAll());
        return mv;
    }
	
	@PostMapping("/save")
	public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		validarUsuario(usuario, result);
		if(result.hasErrors()) {
			return cadastro(usuario);
		}
		
		String msg = "Usuário salvo com sucesso!";
		String redirect = "redirect:/admin/usuarios/add";
		
		try {
			usuarioService.salvar(usuario);
		} catch (EmailUsuarioJaCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return cadastro(usuario);
		} catch (CpfUsuarioJaCadastradoException e) {
			result.rejectValue("cpf", e.getMessage(), e.getMessage());
			return cadastro(usuario);
		} catch (SenhaObrigatoriaUsuarioException e) {
			result.rejectValue("senha", e.getMessage(), e.getMessage());
			return cadastro(usuario);
		}
		
		if(usuario.getCodigo() != null) {
			msg = "Usuário atualizado com sucesso!";
			redirect = "redirect:/admin/usuarios/" + usuario.getCodigo();
		}
		
		attributes.addFlashAttribute("mensagem", msg);
		return new ModelAndView(redirect);
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Usuario usuario = usuarioRepository.buscarComGrupos(codigo);
		ModelAndView mv = cadastro(usuario);
		mv.addObject(usuario);
		return mv;
	}

	@GetMapping
	public ModelAndView pesquisar(UsuarioFilter usuarioFilter, 
		@PageableDefault(size = 10) Pageable pageable, HttpServletRequest httpServletRequest) {
		
		ModelAndView mv = new ModelAndView("/admin/usuarios/list");
		mv.addObject("grupos", gruposRepository.findAll());
		
		PageWrapper<Usuario> paginaWrapper = new PageWrapper<>(usuarioRepository.filtrar(usuarioFilter, pageable)
				, httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	private void validarUsuario(Usuario usuario, BindingResult result) {
		Grupo userRole = gruposRepository.findByCodigoIgnoreCase("ROLE_USER").get();
		usuario.setGrupos(Arrays.asList(userRole));
		usuario.setAtivo(true);
		usuarioValidator.validate(usuario, result);
		System.out.println(result.getAllErrors());
	}
}
