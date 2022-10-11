package com.breaking.ct.models;

import java.util.Optional;

public class Aluno extends Usuario{

	private String nome;
	private String ra;
	private String cpf;
	private String dataNasc;
	private String curso;
	private String habilidades;
	
	// CONSTRUCTOR
	public Aluno() {
		super();
	}
	public Aluno(String cep, String logradouro, String numero, String complemento, String bairro,
			String cidade, String UF, String whatsapp, String email, String senha, String nome, String ra, String cpf,
			String dataNasc, String curso, String habilidades) {
		super(cep, logradouro, numero, complemento, bairro, cidade, UF, whatsapp, email, senha);
		this.nome = nome;
		this.ra = ra;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
		this.curso = curso;
		this.habilidades = habilidades;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRa() {
		return ra;
	}
	public void setRa(String ra) {
		this.ra = ra;
	}
	public String getCPF() {
		return cpf;
	}
	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
	public String getDataNasc() {
		return dataNasc;
	}
	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public String getHabilidades() {
		return habilidades;
	}
	public void setHabilidades(String habilidades) {
		this.habilidades = habilidades;
	}
	public void setRa(Optional<String> ra2) {
		if(ra2.isEmpty())
			this.ra = null;
		else
			this.ra = ra2.get();
	}

}
