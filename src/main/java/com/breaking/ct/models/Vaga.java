package com.breaking.ct.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vaga {
    	// ATRIBUTOS
	private String horas;
	@Id
    private String cnpj;
	private String areaAtuacao;
    private String salario;
	private String requisitos;
	private String quantVagas;
	private String descricao;
	
	// CONSTRUCTORES
	public Vaga() {
		
	}
	public Vaga(String horas, String cnpj , String areaAtuacao, String salario, String requisitos, String quantVagas, String descricao) {
		super();
		this.horas = horas;
        this.cnpj = cnpj;
		this.areaAtuacao = areaAtuacao;
		this.salario = salario;
		this.requisitos = requisitos;
		this.quantVagas = quantVagas;
		this.descricao = descricao;
	}
	
	// GETTERS E SETTERS
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
}
