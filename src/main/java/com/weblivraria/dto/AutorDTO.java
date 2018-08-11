package com.weblivraria.dto;

public class AutorDTO {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String foto;
    private String paisOrigem;

    public AutorDTO() {}

    public AutorDTO(Integer id, String nome, String sobrenome, String foto, String paisOrigem) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.foto = foto;
        this.paisOrigem = paisOrigem;
    }

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public String getNomeCompleto() {
        return this.nome + " " + this.sobrenome;
    }
    
}