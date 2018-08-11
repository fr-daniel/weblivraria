package com.weblivraria.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weblivraria.repository.listener.LivroEntityListener;

import org.hibernate.validator.constraints.NotBlank;

@EntityListeners(LivroEntityListener.class)
@Entity(name="livros")
public class Livro extends AbstractModelFoto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Informe o título do livro")
    @Size(min=3, max=150, message="O tamanho do título deve ser entre {min} e {max}")
    private String titulo;

    @NotBlank(message="Adicione o isbn do livro")
    private String isbn;

    @Column(columnDefinition="TEXT")
    private String resumo;

    @NotNull(message="Informe o ano de lançamento")
    private Short anoLancamento;

    @NotNull(message="Informe a edição do livro")
    private Short edicao;

    @NotNull(message="Informe a quantidade de páginas")
    private Short qtdPaginas;

    @NotBlank(message="Informe o idioma do livro")
    private String idioma;

    @NotNull(message="Informe o peso do livro")
    private Double peso;

    @NotNull(message="Informe a altura do livro")
    private Double altura;

    @NotNull(message="Informe a largura do livro")
    private Double largura;

    private String idUrl;

    @Transient
	private String uuid;

    @ManyToOne
    @JoinTable(name = "livro_editora", joinColumns = @JoinColumn(name = "livro_id"),
    inverseJoinColumns = @JoinColumn(name = "editora_id"))
    @NotNull(message = "Selecione uma editora na pesquisa rápida")
    @JsonIgnore
    private Editora editora;

    @ManyToMany
    @JoinTable(name = "livro_autor", joinColumns = @JoinColumn(name = "livro_id"),
    inverseJoinColumns = @JoinColumn(name = "autor_id"))
    @JsonIgnore
    private List<Autor> autores;

    @ManyToMany
    @JoinTable(name = "livro_categoria", joinColumns = @JoinColumn(name = "livro_id"),
    inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    @JsonIgnore
    private List<Categoria> categorias;

    @OneToMany(mappedBy = "livro")
    @JsonIgnore
	private List<Oferta> ofertas;


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public Short getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Short anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Short getEdicao() {
        return edicao;
    }

    public void setEdicao(Short edicao) {
        this.edicao = edicao;
    }

    public Short getQtdPaginas() {
        return qtdPaginas;
    }

    public void setQtdPaginas(Short qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getLargura() {
        return largura;
    }

    public void setLargura(Double largura) {
        this.largura = largura;
    }

    public String getIdUrl() {
        return idUrl;
    }

    public void setIdUrl(String idUrl) {
        this.idUrl = idUrl;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
    }
    
    public boolean isNovo() {
        return this.id == null;
    }
    
    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(List<Oferta> ofertas) {
        this.ofertas = ofertas;
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
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}