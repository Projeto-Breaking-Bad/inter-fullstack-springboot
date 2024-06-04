package com.breaking.ct.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AlunoDTO {
	@Id
	private String cpf;
	private String nome;
	private String ra;
	private String dataNasc;
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
	private String curso;
	private String habilidades;
	private List<String> listaIdVagasAplicadas;

	public AlunoDTO() {
		this.listaIdVagasAplicadas = new ArrayList<>();
	}

	public void setCpf(String cpf) {
		this.cpf = cpf.replace(".", "").replace("-", "");
	}

	public void setSenha(String senha) {
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}
}
