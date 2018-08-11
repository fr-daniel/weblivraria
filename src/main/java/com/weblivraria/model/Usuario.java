package com.weblivraria.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import com.weblivraria.model.validation.AtributoConfirmacao;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.format.Formatter;

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "senhaConf", message = "Confirmação da senha não confere!")

@Entity
@Table(name = "usuario")
@DynamicUpdate
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 3, max = 50, message = "O tamanho do nome deve ser entre {min} e {max}")
	private String nome;
	
	@NotBlank(message = "Sobrenome é obrigatório")
	@Size(min=2, max=50, message="O tamanho do sobrenome deve ser entre {min} e {max}")
	private String sobrenome;
	
	@NotBlank(message = "E-mail é obrigatório")
	@Email(message = "E-mail inválido")
	private String email;
	
	@NotBlank(message = "CPF é obrigatório")
	@CPF
	private String cpf;
	
	private String senha;
	
	@Transient
    private String senhaConf;
	
	private Boolean ativo;
	
	@NotNull(message = "Data  de Nascimento é obrigatória")
	private LocalDate dataNascimento;
	
	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "codigo_usuario"),
	inverseJoinColumns = @JoinColumn(name = "codigo_grupo"))
	private List<Grupo> grupos;
	
	@PrePersist @PreUpdate
	private void prePersistPreUpdate() {
		Formatter formatter = new CPFFormatter();;
		this.cpf = formatter.unformat(this.cpf); 
		this.senhaConf = senha;
	}
	
	@PostLoad
	private void postLoad() {
		Formatter formatter = new CPFFormatter();;
		this.cpf = formatter.format(this.cpf);
	}
	
	public Long getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenhaConf() {
		return senhaConf;
	}

	public void setSenhaConf(String senhaConf) {
		this.senhaConf = senhaConf;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNomeCompleto() {
		return this.nome + " " + this.sobrenome;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public boolean isNovo() {
		return this.codigo == null;
	}
	
}
