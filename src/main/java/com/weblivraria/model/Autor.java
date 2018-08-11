package com.weblivraria.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import com.weblivraria.repository.listener.AutorEntityListener;

import org.hibernate.validator.constraints.NotBlank;

@EntityListeners(AutorEntityListener.class)
@Entity(name="autores")
public class Autor extends AbstractModelFoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Informe o nome do autor")
	@Size(min = 2, max = 50, message = "O tamanho do nome deve ser entre {min} e {max}")
	private String nome;

	@NotBlank(message = "Informe o sobrenome do autor")
	@Size(min = 2, max = 50, message = "O tamanho sobrenome deve ser entre {min} e {max}")
	private String sobrenome;

	@Column(columnDefinition = "TEXT")
	private String biografia;

	@NotBlank(message = "Inform o país do autor")
	private String paisOrigem;

	@NotBlank(message = "Informe os gêneros literários do autor")
	private String generosLiterarios;
	
	private String idUrl;

	@ManyToMany(mappedBy = "autores")
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public String getGenerosLiterarios() {
		return generosLiterarios;
	}

	public void setGenerosLiterarios(String generosLiterarios) {
		this.generosLiterarios = generosLiterarios;
	}

	public String getIdUrl() {
		return idUrl;
	}

	public void setIdUrl(String idUrl) {
		this.idUrl = idUrl;
	}

	public String getNomeCompleto() {
		return nome + " " + sobrenome;
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
		Autor other = (Autor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
