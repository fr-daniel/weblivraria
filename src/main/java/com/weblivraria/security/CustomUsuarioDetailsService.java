package com.weblivraria.security;

import java.util.Collection;
import java.util.Optional;

import com.weblivraria.model.Usuario;
import com.weblivraria.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomUsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioRepository.porEmailEAtivo(email);

		if(!usuario.isPresent())
			throw new UsernameNotFoundException("Usuário não enontrado!");

		return new UsuarioSistema(usuario.get(), getGrupos(usuario.get()));
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		return usuario.getGrupos();
	}

}