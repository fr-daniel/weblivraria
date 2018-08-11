package com.weblivraria.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import com.weblivraria.model.Usuario;
import com.weblivraria.repository.filter.UsuarioFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UsuarioRepositoryQueries {

	public Optional<Usuario> porEmailEAtivo(String email);
	public Optional<Usuario> porCpfEAtivo(String cpf);

	public List<String> permissoes(Usuario usuario);
	
	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);
	
	public Usuario buscarComGrupos(Long codigo);
	
}
