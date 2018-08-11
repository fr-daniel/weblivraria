package com.weblivraria.repository;

import com.weblivraria.model.Usuario;
import com.weblivraria.repository.helper.usuario.UsuarioRepositoryQueries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQueries {
    
    Usuario findOneByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);

}