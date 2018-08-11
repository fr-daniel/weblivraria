package com.weblivraria.controller.cliente;

import java.util.Arrays;

import javax.validation.Valid;

import com.weblivraria.controller.validator.UsuarioValidator;
import com.weblivraria.model.Grupo;
import com.weblivraria.model.Usuario;
import com.weblivraria.repository.GrupoRepository;
import com.weblivraria.service.UsuarioService;
import com.weblivraria.service.exception.CpfUsuarioJaCadastradoException;
import com.weblivraria.service.exception.EmailUsuarioJaCadastradoException;
import com.weblivraria.service.exception.SenhaObrigatoriaUsuarioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

	@Autowired
	private GrupoRepository grupoRepository;

    @Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioValidator usuarioValidator;

    @GetMapping("/cadastro")
    public ModelAndView cadastro(Usuario usuario) {
		ModelAndView mv = new ModelAndView("site/register");
		mv.addObject("usuario", usuario);
        return mv;
    }

    @PostMapping("/cadastro")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes) {
		validarUsuario(usuario, result);
		if(result.hasErrors()) {
			System.out.println(result.getAllErrors());
			return cadastro(usuario);
		}
		
		String msg = "Cadastro realizado com sucesso!";
		String redirect = "redirect:/login";
		
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
			msg = "Dados atualizados com sucesso!";
			redirect = "redirect:/user/edit" + usuario.getCodigo();
		}
		
		attributes.addFlashAttribute("mensagem", msg);
		return new ModelAndView(redirect);
	}

	private void validarUsuario(Usuario usuario, BindingResult result) {
		Grupo userRole = grupoRepository.findByCodigoIgnoreCase("ROLE_USER").get();
		usuario.setGrupos(Arrays.asList(userRole));
		usuario.setAtivo(true);
		usuarioValidator.validate(usuario, result);
		System.out.println(result.getAllErrors());
	}
    
}