package com.breaking.ct.services;

import com.breaking.ct.dto.EmpresaDTO;
import com.breaking.ct.mappers.EmpresaMapper;
import com.breaking.ct.models.Empresa;
import com.breaking.ct.repositories.EmpresaRepository;
import com.breaking.ct.repositories.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpresaService {
	private final EmpresaMapper empresaMapper;
	private EmpresaRepository empresaRepository;
	private VagaRepository vagaRepository;

	public List<Empresa> getTodosEmpresas() {
		return empresaRepository.findAll();
	}

	public Optional<Empresa> getEmpresaByCnpj(String cnpj) {
		String cleanCnpj = cleanCnpj(cnpj);
		return empresaRepository.findByCnpj(cleanCnpj);
	}

	public Optional<Empresa> getEmpresaByEmail(String email) {
		return empresaRepository.findByEmail(email);
	}

	public void addEmpresa(EmpresaDTO dto) {
		String cleanCnpj = cleanCnpj(dto.getCnpj());
		dto.setCnpj(cleanCnpj);
		Optional<Empresa> teste1 = empresaRepository.findByCnpj(dto.getCnpj());
		if (teste1.isPresent()) return;
		Optional<Empresa> teste2 = empresaRepository.findByEmail(dto.getEmail());
		if (teste2.isPresent()) return;
		Empresa newEmpresa = empresaMapper.map(dto);
		empresaRepository.save(newEmpresa);
	}

	public void updateEmpresa(Empresa empresa) {
		Empresa empresaLogada = getLogged();
		if (empresaLogada.getCnpj().equals(empresa.getCnpj())) {
			empresaRepository.deleteByCnpj(empresa.getCnpj());
			empresaRepository.save(empresa);
		}
	}

	public void updateEmpresa(EmpresaDTO dto) {
		String cleanCnpj = cleanCnpj(dto.getCnpj());
		dto.setCnpj(cleanCnpj);
		Optional<Empresa> teste1 = empresaRepository.findByCnpj(dto.getCnpj());
		if (teste1.isPresent()) {
			empresaRepository.deleteByCnpj(dto.getCnpj());
			Empresa empresaAtualizada = empresaMapper.map(dto, teste1.get());
			empresaRepository.save(empresaAtualizada);
			return;
		}
		Optional<Empresa> teste2 = empresaRepository.findByEmail(dto.getEmail());
		if (teste2.isPresent()) {
			empresaRepository.deleteByEmail(dto.getEmail());
			Empresa empresaAtualizada = empresaMapper.map(dto, teste2.get());
			empresaRepository.save(empresaAtualizada);
		}
	}

	public void deleteEmpresa(String cnpj) {
		String cleanCnpj = cleanCnpj(cnpj);
		Optional<Empresa> empresaDeletada = empresaRepository.findByCnpj(cleanCnpj);
		empresaDeletada.ifPresent(empresa -> vagaRepository.deleteAllById(empresa.getListaIdVagasCriadas()));
		empresaRepository.deleteByCnpj(cnpj);
	}

	public Empresa getLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;
		if (principal instanceof UserDetails)
			email = ((UserDetails) principal).getUsername();
		else
			email = principal.toString();
		Optional<Empresa> empresaOp = getEmpresaByEmail(email);
		return empresaOp.orElse(null);
	}

	private String cleanCnpj(String cnpj) {
		return cnpj
			.replace(".", "")
			.replace("-", "")
			.replace("/", "")
			.trim();
	}
}
