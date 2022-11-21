package com.breaking.ct.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.repositories.AlunoRepository;

@Service
public class AlunoService {

	@Autowired
	private AlunoRepository alunoRepository;
	
	public ArrayList<Aluno> getTodosAlunos() {
		ArrayList<Aluno> alunos = new ArrayList<>();
		alunoRepository.findAll().forEach(alunos::add);
		return alunos;
	}

	public Optional<Aluno> getAlunoByCpf(String cpf) {
		return alunoRepository.findByCpf(cpf);
	}

	public void addAluno(Aluno aluno) {
		alunoRepository.save(aluno);
	}

	public void updateAluno(Aluno novoAluno) {
		deleteAluno(novoAluno.getCpf());
		alunoRepository.save(novoAluno);
	}

	public void deleteAluno(String cpf) {
		alunoRepository.deleteByCpf(cpf);
	}
	
	public Aluno getLogged(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String cpf = "";
		if (principal instanceof UserDetails) {
			cpf = ((UserDetails)principal).getUsername();
		} else {
			cpf = principal.toString();
		}
		
		Optional<Aluno> alunoOp = getAlunoByCpf(cpf);
		if(alunoOp.isEmpty())
			return null;
		return alunoOp.get();
	}
}
