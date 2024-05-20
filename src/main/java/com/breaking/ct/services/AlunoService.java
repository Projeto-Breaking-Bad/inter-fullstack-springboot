package com.breaking.ct.services;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.repositories.AlunoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AlunoService {

	private AlunoRepository alunoRepository;

	public List<Aluno> getTodosAlunos() {
		return alunoRepository.findAll();
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
		alunoRepository.save(novoAluno);
	}

	public void deleteAluno(String cpf) {
		alunoRepository.deleteByCpf(cpf);
	}

	public Aluno getLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;

		if (principal instanceof UserDetails)
			email = ((UserDetails) principal).getUsername();
		else
			email = principal.toString();

		Optional<Aluno> alunoOp = getAlunoByEmail(email);
		return alunoOp.orElseGet(Aluno::new);
	}
}
