package com.breaking.ct.models;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Document
public class Empresa implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
	@Id
	private String cnpj;
	private String nome;
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
	private List<String> listaIdVagasCriadas;
	
	// CONSTRUCTORES
	public Empresa() {
		this.setListaIdVagasCriadas(new LinkedList<>());
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
		this.cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "").trim();
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
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<String> getListaIdVagasCriadas() {
		return listaIdVagasCriadas;
	}
	public void setListaIdVagasCriadas(List<String> listaIdVagasCriadas) {
		this.listaIdVagasCriadas = listaIdVagasCriadas;
	}
	
	// SEGURANCA
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for(String role: Arrays.asList("EMPRESA")) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
	@Override
	public String getUsername() {
		// Este metodo devolve o dado utilizado para identificacao do usuario, que eh o email
		return this.email;
	}
	@Override
	public String getPassword() {
		return this.senha;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
}
