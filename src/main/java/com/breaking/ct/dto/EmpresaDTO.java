package com.breaking.ct.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmpresaDTO {
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

	public EmpresaDTO() {
		this.listaIdVagasCriadas = new ArrayList<>();
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj.replace(".", "").replace("-", "").replace("/", "").trim();
	}

	public void setSenha(String senha) {
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}
}
