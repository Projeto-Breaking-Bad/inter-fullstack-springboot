package com.breaking.ct.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Empresa;
import com.breaking.ct.repositories.EmpresaRepository;
import com.breaking.ct.repositories.VagaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private VagaRepository vagaRepository;
	
	public ArrayList<Empresa> getTodosEmpresas() {
		ArrayList<Empresa> empresas = new ArrayList<>();
		empresaRepository.findAll().forEach(empresas::add);
		return empresas;
	}

	public Optional<Empresa> getEmpresaByCnpj(String cnpj){
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
		vagaRepository.deleteAllById(getEmpresaByCnpj(cnpj).get().getListaIdVagasCriadas());
		empresaRepository.deleteByCnpj(cnpj);
	}
	
	
	public Empresa getLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email = "";
		if (principal instanceof UserDetails) {
			email = ((UserDetails)principal).getUsername();
		} else {
			email = principal.toString();
		}
		
		Optional<Empresa> empresaOp = getEmpresaByEmail(email);
		if(empresaOp.isEmpty())
			return null;
		return empresaOp.get();
	}

}
