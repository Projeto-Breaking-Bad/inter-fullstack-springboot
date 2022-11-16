package com.breaking.ct.models;
import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Admin{
    
	// ATRIBUTOS
	private String nome;
	@Id
	private String instituicao;
    private String login;
	private String email;
	private String senha;
	
	// CONSTRUCTORES
	public Admin() {
		
	}
	public Admin(String nome, String instituicao, String login, String email, String senha) {
		super();
		this.nome = nome;
        this.instituicao = instituicao;
        this.login = login;
		this.email = email;
		this.senha = senha;
	}
	
	// GETTERS E SETTERS
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
    public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
    public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
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
}
