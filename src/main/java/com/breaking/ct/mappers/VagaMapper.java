package com.breaking.ct.mappers;

import com.breaking.ct.dto.VagaDTO;
import com.breaking.ct.models.Vaga;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VagaMapper {
	public Vaga map(VagaDTO dto) {
		return Vaga.builder()
			.id(dto.getId())
			.horas(dto.getHoras())
			.cnpj(dto.getCnpj())
			.areaAtuacao(dto.getAreaAtuacao())
			.salario(dto.getSalario())
			.requisitos(dto.getRequisitos())
			.quantVagas(dto.getQuantVagas())
			.descricao(dto.getDescricao())
			.listaIdsAlunosInscritos(dto.getListaIdsAlunosInscritos())
			.build();
	}

	public Vaga map(VagaDTO dto, Vaga vaga) {
		if (dto.getId() != null) vaga.setId(dto.getId());
		if (dto.getHoras() != null) vaga.setHoras(dto.getHoras());
		if (dto.getCnpj() != null) vaga.setCnpj(dto.getCnpj());
		if (dto.getAreaAtuacao() != null) vaga.setAreaAtuacao(dto.getAreaAtuacao());
		if (dto.getSalario() != null) vaga.setSalario(dto.getSalario());
		if (dto.getRequisitos() != null) vaga.setRequisitos(dto.getRequisitos());
		if (dto.getQuantVagas() != null) vaga.setQuantVagas(dto.getQuantVagas());
		if (dto.getDescricao() != null) vaga.setDescricao(dto.getDescricao());
		if (dto.getListaIdsAlunosInscritos() != null) vaga.setListaIdsAlunosInscritos(dto.getListaIdsAlunosInscritos());
		return vaga;
	}
}
