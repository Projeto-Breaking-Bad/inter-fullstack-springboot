package com.breaking.ct.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		return alunoRepository.findById(cpf);
	}

	public void addAluno(Aluno aluno) {
		alunoRepository.save(aluno);
	}

	public void updateAluno(Aluno aluno) {
		alunoRepository.save(aluno);
	}

	public void deleteAluno(String cpf) {
		alunoRepository.deleteById(cpf);
	}
	
}
