package com.weblivraria.repository.filter;

public class OfertaFilter {

    private Long id;
	private Double precoMin;
	private Double precoMax;
	private Integer estoqueMin;
	private Integer estoqueMax;
    private String livro;
	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrecoMin() {
		return precoMin;
	}

	public void setPrecoMin(Double precoMin) {
		this.precoMin = precoMin;
	}

	public Double getPrecoMax() {
		return precoMax;
	}

	public void setPrecoMax(Double precoMax) {
		this.precoMax = precoMax;
	}

	public Integer getEstoqueMin() {
		return estoqueMin;
	}

	public void setEstoqueMin(Integer estoqueMin) {
		this.estoqueMin = estoqueMin;
	}
	
	public Integer getEstoqueMax() {
		return estoqueMax;
	}

	public void setEstoqueMax(Integer estoqueMax) {
		this.estoqueMax = estoqueMax;
	}

	public String getLivro() {
		return livro;
	}

	public void setLivro(String livro) {
		this.livro = livro;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}