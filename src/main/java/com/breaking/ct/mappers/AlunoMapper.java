package com.breaking.ct.mappers;

import com.breaking.ct.dto.AlunoDTO;
import com.breaking.ct.models.Aluno;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AlunoMapper {
	public Aluno map(AlunoDTO dto) {
		return Aluno.builder()
			.cpf(dto.getCpf())
			.nome(dto.getNome())
			.ra(dto.getRa())
			.dataNasc(dto.getDataNasc())
			.cep(dto.getCep())
			.logradouro(dto.getLogradouro())
			.numero(dto.getNumero())
			.complemento(dto.getComplemento())
			.bairro(dto.getBairro())
			.cidade(dto.getCidade())
			.uf(dto.getUf())
			.telefone(dto.getTelefone())
			.whatsapp(dto.getWhatsapp())
			.email(dto.getEmail())
			.senha(dto.getSenha())
			.curso(dto.getCurso())
			.habilidades(dto.getHabilidades())
			.listaIdVagasAplicadas(dto.getListaIdVagasAplicadas())
			.build();
	}

	public Aluno map(AlunoDTO dto, Aluno aluno) {
		if (dto.getCpf() != null) aluno.setCpf(dto.getCpf());
		if (dto.getComplemento() != null) aluno.setComplemento(dto.getComplemento());
		if (dto.getEmail() != null) aluno.setEmail(dto.getEmail());
		if (dto.getNome() != null) aluno.setNome(dto.getNome());
		if (dto.getRa() != null) aluno.setRa(dto.getRa());
		if (dto.getDataNasc() != null) aluno.setDataNasc(dto.getDataNasc());
		if (dto.getCep() != null) aluno.setCep(dto.getCep());
		if (dto.getLogradouro() != null) aluno.setLogradouro(dto.getLogradouro());
		if (dto.getNumero() != null) aluno.setNumero(dto.getNumero());
		if (dto.getBairro() != null) aluno.setBairro(dto.getBairro());
		if (dto.getCidade() != null) aluno.setCidade(dto.getCidade());
		if (dto.getUf() != null) aluno.setUf(dto.getUf());
		if (dto.getTelefone() != null) aluno.setTelefone(dto.getTelefone());
		if (dto.getWhatsapp() != null) aluno.setWhatsapp(dto.getWhatsapp());
		if (dto.getSenha() != null) aluno.setSenha(dto.getSenha());
		if (dto.getCurso() != null) aluno.setCurso(dto.getCurso());
		if (dto.getHabilidades() != null) aluno.setHabilidades(dto.getHabilidades());
		if (dto.getListaIdVagasAplicadas() != null) aluno.setListaIdVagasAplicadas(dto.getListaIdVagasAplicadas());
		return aluno;
	}
}
