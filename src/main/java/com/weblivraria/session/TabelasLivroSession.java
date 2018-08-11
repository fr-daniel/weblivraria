package com.weblivraria.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.weblivraria.model.Autor;
import com.weblivraria.model.Categoria;

@SessionScope
@Component
public class TabelasLivroSession {

	private Set<TabelaAutoresLivro> tabelasAutoresLivros = new HashSet<>();
	private Set<TabelaCategoriasLivro> tabelaCategoriasLivros = new HashSet<>();

	public void adicionarAutor(String uuid, Autor autor) {
		TabelaAutoresLivro tabela = buscarTabelaAutoresPorUuid(uuid);
		tabela.adicionarAutor(autor);	
	}

	public List<Autor> getAutores(String uuid) {
		TabelaAutoresLivro tabela = buscarTabelaAutoresPorUuid(uuid);
		return tabela.getAutores();
	}

	public void excluirAutor(String uuid, Autor autor) {
		TabelaAutoresLivro tabela = buscarTabelaAutoresPorUuid(uuid);
		tabela.excluirAutor(autor);
	}

	private TabelaAutoresLivro buscarTabelaAutoresPorUuid(String uuid) {
		TabelaAutoresLivro tabela = tabelasAutoresLivros.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaAutoresLivro(uuid));
				tabelasAutoresLivros.add(tabela);
		return tabela;
	}

	public void adicionarCategoria(String uuid, Categoria categoria) {
		TabelaCategoriasLivro tabela = buscarTabelaCategoriasPorUuid(uuid);
		tabela.adicionarCategoria(categoria);	
	}

	public List<Categoria> getCategorias(String uuid) {
		TabelaCategoriasLivro tabela = buscarTabelaCategoriasPorUuid(uuid);
		return tabela.getCategorias();
	}

	public void excluirCategoria(String uuid, Categoria categoria) {
		TabelaCategoriasLivro tabela = buscarTabelaCategoriasPorUuid(uuid);
		tabela.excluirCategoria(categoria);
	}

	private TabelaCategoriasLivro buscarTabelaCategoriasPorUuid(String uuid) {
		TabelaCategoriasLivro tabela = tabelaCategoriasLivros.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaCategoriasLivro(uuid));
				tabelaCategoriasLivros.add(tabela);
		return tabela;
	}

}