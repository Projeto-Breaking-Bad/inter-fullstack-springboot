package com.breaking.ct.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Vaga {
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

	public Vaga() {
		this.listaIdsAlunosInscritos = new LinkedList<>();
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "").trim();
	}

	public List<String> getListaIdsAlunosAplicantes() {
		return listaIdsAlunosInscritos;
	}
}
