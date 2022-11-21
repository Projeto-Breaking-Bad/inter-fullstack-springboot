package com.breaking.ct.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Empresa;
import com.breaking.ct.repositories.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	public ArrayList<Empresa> getTodosEmpresas() {
		ArrayList<Empresa> empresas = new ArrayList<>();
		empresaRepository.findAll().forEach(empresas::add);
		return empresas;
	}

	public Optional<Empresa> getEmpresaByCnpj(String cnpj){
		return empresaRepository.findByCnpj(cnpj);
	}

	public void addEmpresa(Empresa empresa) {
		empresaRepository.save(empresa);
	}

	public void updateEmpresa(Empresa novaEmpresa) {
		deleteEmpresa(novaEmpresa.getCnpj());
		empresaRepository.save(novaEmpresa);
	}

	public void deleteEmpresa(String cnpj) {
		empresaRepository.deleteByCnpj(cnpj);
	}
	
	
	public Empresa getLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String cnpj = "";
		if (principal instanceof UserDetails) {
			cnpj = ((UserDetails)principal).getUsername();
		} else {
			cnpj = principal.toString();
		}
		
		Optional<Empresa> empresaOp = getEmpresaByCnpj(cnpj);
		if(empresaOp.isEmpty())
			return null;
		return empresaOp.get();
	}
}
