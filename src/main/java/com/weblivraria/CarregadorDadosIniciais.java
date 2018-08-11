package com.weblivraria;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import com.weblivraria.model.Grupo;
import com.weblivraria.model.Usuario;
import com.weblivraria.repository.GrupoRepository;
import com.weblivraria.repository.UsuarioRepository;
import com.weblivraria.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * CarregadorDadosIniciais
 */
@Component
public class CarregadorDadosIniciais implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup)
            return;

        criarGrupoSeNaoExistir("ROLE_ADMIN", "Administrador");
        criarGrupoSeNaoExistir("ROLE_USER", "Usuário");
 
        Grupo adminRole = grupoRepository.findByCodigoIgnoreCase("ROLE_ADMIN").get();
        Grupo userRole = grupoRepository.findByCodigoIgnoreCase("ROLE_USER").get();
        
        if(usuarioRepository.count() == 0) {
            Usuario user = new Usuario();
            user.setNome("Admin");
            user.setSobrenome("System");
            user.setSenha("123456");
            user.setSenhaConf("123456");
            user.setEmail("admin@weblivraria.com");
            user.setCpf("897.041.380-40");
            user.setDataNascimento(LocalDate.now());
            user.setGrupos(Arrays.asList(userRole, adminRole));
            user.setAtivo(true);
            usuarioService.salvar(user);
            
            Usuario user2 = new Usuario();
            user2.setNome("User");
            user2.setSobrenome("System");
            user2.setSenha("123456");
            user2.setSenhaConf("123456");
            user2.setEmail("user@weblivraria.com");
            user2.setCpf("410.748.390-80");
            user2.setDataNascimento(LocalDate.now());
            user2.setGrupos(Arrays.asList(userRole));
            user2.setAtivo(true);
            usuarioService.salvar(user2);
        }

        alreadySetup = true;
    }

    @Transactional
    private Grupo criarGrupoSeNaoExistir(String codigo, String nome) {
        Optional<Grupo> grupo = grupoRepository.findByCodigoIgnoreCase(codigo);

        if (!grupo.isPresent()) {
            grupo = Optional.of(new Grupo(codigo, nome));
            grupoRepository.save(grupo.get());
        }

        return grupo.get();
    }
}