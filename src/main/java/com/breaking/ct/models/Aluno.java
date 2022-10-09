package com.breaking.ct.models;

public class Aluno extends Usuario{

	private String nome;
	private String ra;
	private String cpf;
	private String dataNasc;
	private String curso;
	private String habilidades;
	
	// CONSTRUCTOR
	public Aluno(String cep, String logradouro, String numero, String complemento, String bairro,
			String cidade, String UF, String whatsapp, String email, String nome, String ra, String cpf,
			String dataNasc, String curso, String habilidades) {
		super(cep, logradouro, numero, complemento, bairro, cidade, UF, whatsapp, email);
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

}
