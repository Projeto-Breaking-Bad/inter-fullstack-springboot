package com.breaking.ct.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class VagaDTO {
	@Id
	private String id;
	private String horas;
	private String cnpj;
	private String areaAtuacao;
	private String salario;
	private String requisitos;
	private String quantVagas;
	private String descricao;
	private List<String> listaIdsAlunosInscritos;

	public VagaDTO() {
		this.listaIdsAlunosInscritos = new LinkedList<>();
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "").trim();
	}
}
