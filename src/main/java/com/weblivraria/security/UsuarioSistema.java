package com.weblivraria.security;

import java.util.Collection;

import com.weblivraria.model.Usuario;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class UsuarioSistema extends User {

	private static final long serialVersionUID = 4634907079310425556L;
	private Usuario usuario;

	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}