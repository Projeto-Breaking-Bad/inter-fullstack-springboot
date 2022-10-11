package com.breaking.ct.models;

import java.util.Optional;

public abstract class Usuario {
	
	// ATRIBUTOS
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String whatsapp;
	private String email;
	@SuppressWarnings("unused")
	private String senha;
	
	// CONTRUCTORES
	public Usuario() {
		
	}
	public Usuario(String cep, String logradouro, String numero, String complemento,
			String bairro, String cidade, String uf, String whatsapp, String email, String senha) {
		super();
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.whatsapp = whatsapp;
		this.email = email;
		this.senha = senha;
	}
	
	// GETTERS E SETTERS
	public String getCEP() {
		return cep;
	}
	public void setCEP(String cep) {
		this.cep = cep;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUF() {
		return uf;
	}
	public void setUF(String uf) {
		this.uf = uf;
	}
	public String getWhatsapp() {
		return whatsapp;
	}
	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
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
	public void setComplemento(Optional<String> complemento2) {
		if(complemento2.isEmpty())
			this.complemento = null;
		else
			this.complemento = complemento2.get();
	}
}
