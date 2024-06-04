package com.breaking.ct.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Id;

@Getter
@Setter
public class AdminDTO {
	@Id
	private String login;
	private String email;
	private String senha;
	private String nome;
	private String instituicao;
	private boolean deleted = false;

	public void setSenha(String senha) {
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}
}
