package com.weblivraria.controller.validator;

import com.weblivraria.model.Usuario;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsuarioValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		Usuario usuario = (Usuario) target;
		
		validarSeInformouGrupos(errors, usuario);
		validarSeInformouStatus(errors, usuario);
	}

	private void validarSeInformouGrupos(Errors errors, Usuario usuario) {
		if (usuario.getGrupos().isEmpty()) {
			errors.reject("", "Adicione pelo menos um grupo ao usuario");
		}
	}

	private void validarSeInformouStatus(Errors errors, Usuario usuario) {
		if (usuario.getAtivo() == null) {
			errors.reject("", "Adicione o status do usuário");
		}
	}

}
