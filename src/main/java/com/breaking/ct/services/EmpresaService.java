package com.breaking.ct.services;

import com.breaking.ct.models.Empresa;
import com.breaking.ct.repositories.EmpresaRepository;
import com.breaking.ct.repositories.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmpresaService {

	private EmpresaRepository empresaRepository;
	private VagaRepository vagaRepository;

	public List<Empresa> getTodosEmpresas() {
		return new ArrayList<>(empresaRepository.findAll());
	}

	public Optional<Empresa> getEmpresaByCnpj(String cnpj) {
		return empresaRepository.findByCnpj(cnpj);
	}

	public Optional<Empresa> getEmpresaByEmail(String email) {
		return empresaRepository.findByEmail(email);
	}

	public void addEmpresa(Empresa empresa) {
		empresaRepository.save(empresa);
	}

	public void updateEmpresa(Empresa novaEmpresa) {
		empresaRepository.save(novaEmpresa);
	}

	public void deleteEmpresa(String cnpj) {
		var empresaDeletada = empresaRepository.findByCnpj(cnpj);
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

}
