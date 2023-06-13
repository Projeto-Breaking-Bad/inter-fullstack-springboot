package com.breaking.ct.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Vaga {
	
	// ATRIBUTOS
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String horas;
	private String cnpj;
	private String areaAtuacao;
	private String salario;
	private String requisitos;
	private String quantVagas;
	private String descricao;
	private List<String> listaIdsAlunosInscritos;
	
	// CONSTRUCTORES
	public Vaga() {
		this.listaIdsAlunosInscritos = new LinkedList<>();
	}

	// GETTERS E SETTERS
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getHoras() {
		return horas;
	}
	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAreaAtuacao() {
		return areaAtuacao;
	}
	public void setAreaAtuacao(String areaAtuacao) {
		this.areaAtuacao = areaAtuacao;
	}

	public String getSalario() {
		return salario;
	}
	public void setSalario(String salario) {
		this.salario = salario;
	}

	public String getRequisitos() {
		return requisitos;
	}
	public void setRequisitos(String requisitos) {
		this.requisitos = requisitos;
	}

	public String getQuantVagas() {
		return quantVagas;
	}
	public void setQuantVagas(String quantVagas) {
		this.quantVagas = quantVagas;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<String> getListaIdsAlunosAplicantes() {
		return listaIdsAlunosInscritos;
	}
	public void setListaIdsAlunosAplicantes(List<String> listaIdsAlunosInscritos) {
		this.listaIdsAlunosInscritos = listaIdsAlunosInscritos;
	}
	
}
