package com.breaking.ct.mappers;

import com.breaking.ct.dto.AdminDTO;
import com.breaking.ct.models.Admin;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdminMapper {
	public Admin map(AdminDTO dto) {
		return Admin.builder()
			.login(dto.getLogin())
			.email(dto.getEmail())
			.senha(dto.getSenha())
			.nome(dto.getNome())
			.instituicao(dto.getInstituicao())
			.deleted(dto.isDeleted())
			.build();
	}

	public Admin map(AdminDTO dto, Admin admin) {
		if (dto.getLogin() != null) admin.setLogin(dto.getLogin());
		if (dto.getEmail() != null) admin.setEmail(dto.getEmail());
		if (dto.getSenha() != null) admin.setSenha(dto.getSenha());
		if (dto.getNome() != null) admin.setNome(dto.getNome());
		if (dto.getInstituicao() != null) admin.setInstituicao(dto.getInstituicao());
		admin.setDeleted(dto.isDeleted());
		return admin;
	}
}
