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
	
	public Optional<Aluno> getAlunoByEmail(String email) {
		return alunoRepository.findByEmail(email);
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

		String email = "";
		if (principal instanceof UserDetails) {
			email = ((UserDetails)principal).getUsername();
		} else {
			email = principal.toString();
		}
		
		Optional<Aluno> alunoOp = getAlunoByEmail(email);
		if(alunoOp.isEmpty())
			return null;
		return alunoOp.get();
	}
}
