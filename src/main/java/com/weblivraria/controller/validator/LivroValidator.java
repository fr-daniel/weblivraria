package com.weblivraria.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.weblivraria.model.Livro;

@Component
public class LivroValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Livro.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {		
		Livro livro = (Livro) target;
		
		validarSeInformouAutores(errors, livro);
		validarSeInformouCategorias(errors, livro);
	}

	private void validarSeInformouAutores(Errors errors, Livro livro) {
		if (livro.getAutores().isEmpty()) {
			errors.reject("", "Adicione pelo menos um autor do livro");
		}
	}

	private void validarSeInformouCategorias(Errors errors, Livro livro) {
		if (livro.getCategorias().isEmpty()) {
			errors.reject("", "Adicione pelo menos uma categoria ao livro");
		}
	}

}
