package com.breaking.ct.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Empresa {
	
	// ATRIBUTOS
	private String nome;
	@Id
	private String cnpj;
    private String areaNegocio;
	private String dataCriacao;
	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String telefone;
	private String whatsapp;
	private String email;
	private String senha;
	private String descricao;
	
	// CONSTRUCTORES
	public Empresa() {
		
	}
	public Empresa(String nome, String cnpj, String areaNegocio, String dataCriacao, String cep, String logradouro, String numero,
			String complemento, String bairro, String cidade, String uf, String telefone,String whatsapp, String email, String senha, String descricao) {
		super();
		this.nome = nome;
		this.cnpj = cnpj;
		this.dataCriacao = dataCriacao;
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.telefone = telefone;
		this.whatsapp = whatsapp;
		this.email = email;
		this.senha = senha;
		this.descricao = descricao;
	}
	
	// GETTERS E SETTERS
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getAreaNegocio() {
        return areaNegocio;
    }
    public void setAreaNegocio(String areaNegocio) {
        this.areaNegocio = areaNegocio;
    }
    public String dataCriacao() {
        return dataCriacao;
    }
    public void setdataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
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
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}