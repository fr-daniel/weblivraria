package com.weblivraria.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.weblivraria.repository.listener.EditoraEntityListener;

import org.hibernate.validator.constraints.NotBlank;

@EntityListeners(EditoraEntityListener.class)
@Entity
@Table(name = "editoras")
public class Editora extends AbstractModelFoto {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
	
	@NotBlank(message = "O nome não pode ser vazio")
    @Size(min=3, max=50, message="O tamanho do nome deve ser entre {min} e {max}")
    private String nome;
	
	@Column(columnDefinition="TEXT")
	private String descricao;
	
	private String idUrl;

	private String urlSite;

	@OneToMany(mappedBy = "editora")
	private List<Livro> livros;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getIdUrl() {
		return idUrl;
	}


	public void setIdUrl(String idUrl) {
		this.idUrl = idUrl;
	}

	public String getUrlSite() {
		return urlSite;
	}

	public void setUrlSite(String urlSite) {
		this.urlSite = urlSite;
	}

	public boolean isNova() {
		return this.id == null;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Editora other = (Editora) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}