package com.breaking.ct.mappers;

import com.breaking.ct.dto.EmpresaDTO;
import com.breaking.ct.models.Empresa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EmpresaMapper {
	public Empresa map(EmpresaDTO dto) {
		return Empresa.builder()
			.cnpj(dto.getCnpj())
			.nome(dto.getNome())
			.areaNegocio(dto.getAreaNegocio())
			.dataCriacao(dto.getDataCriacao())
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
			.descricao(dto.getDescricao())
			.listaIdVagasCriadas(dto.getListaIdVagasCriadas())
			.build();
	}

	public Empresa map(EmpresaDTO dto, Empresa empresa) {
		if (dto.getCnpj() != null) empresa.setCnpj(dto.getCnpj());
		if (dto.getNome() != null) empresa.setNome(dto.getNome());
		if (dto.getAreaNegocio() != null) empresa.setAreaNegocio(dto.getAreaNegocio());
		if (dto.getDataCriacao() != null) empresa.setDataCriacao(dto.getDataCriacao());
		if (dto.getCep() != null) empresa.setCep(dto.getCep());
		if (dto.getLogradouro() != null) empresa.setLogradouro(dto.getLogradouro());
		if (dto.getNumero() != null) empresa.setNumero(dto.getNumero());
		if (dto.getComplemento() != null) empresa.setComplemento(dto.getComplemento());
		if (dto.getBairro() != null) empresa.setBairro(dto.getBairro());
		if (dto.getCidade() != null) empresa.setCidade(dto.getCidade());
		if (dto.getUf() != null) empresa.setUf(dto.getUf());
		if (dto.getTelefone() != null) empresa.setTelefone(dto.getTelefone());
		if (dto.getWhatsapp() != null) empresa.setWhatsapp(dto.getWhatsapp());
		if (dto.getEmail() != null) empresa.setEmail(dto.getEmail());
		if (dto.getSenha() != null) empresa.setSenha(dto.getSenha());
		if (dto.getDescricao() != null) empresa.setDescricao(dto.getDescricao());
		if (dto.getListaIdVagasCriadas() != null) empresa.setListaIdVagasCriadas(dto.getListaIdVagasCriadas());
		return empresa;
	}
}
