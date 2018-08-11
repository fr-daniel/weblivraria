package com.weblivraria.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.caelum.stella.format.CPFFormatter;

import com.weblivraria.model.Usuario;
import com.weblivraria.repository.UsuarioRepository;
import com.weblivraria.service.exception.CpfUsuarioJaCadastradoException;
import com.weblivraria.service.exception.EmailUsuarioJaCadastradoException;
import com.weblivraria.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public void salvar(Usuario usuario) throws EmailUsuarioJaCadastradoException, CpfUsuarioJaCadastradoException, SenhaObrigatoriaUsuarioException {
		
		Optional<Usuario> usuarioExistente = Optional.ofNullable(usuarioRepository.findOneByEmail(usuario.getEmail()));
		String cpf = new CPFFormatter().unformat(usuario.getCpf());
		Optional<Usuario> usuarioCpfExistente = usuarioRepository.findByCpf(cpf);
		
		if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario))
			throw new EmailUsuarioJaCadastradoException("E-mail já cadastrado");
		
		if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha()))
			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
	
		if (usuarioCpfExistente.isPresent() && !usuarioCpfExistente.get().equals(usuario))
			throw new CpfUsuarioJaCadastradoException("CPF já cadastrado");
		
		if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
		} else if (StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioExistente.get().getSenha());
		}
		
		usuario.setSenhaConf(usuario.getSenha());
		
		if (!usuario.isNovo() && usuario.getAtivo() == null)
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		
		usuarioRepository.save(usuario);
	}

	
}
