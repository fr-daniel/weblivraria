package com.weblivraria.dto;

public class CategoriaDTO {

	private Integer id;
	private String titulo;
	private String idUrl;

	public CategoriaDTO() {}
	
	public CategoriaDTO(Integer id, String titulo, String idUrl) {
		this.id = id;
		this.titulo = titulo;
		this.idUrl = idUrl;
	}

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

	public String getIdUrl() {
		return idUrl;
	}

	public void setIdUrl(String idUrl) {
		this.idUrl = idUrl;
	}	
}
