package com.breaking.ct.services;

import com.breaking.ct.mappers.AlunoMapper;
import com.breaking.ct.dto.AlunoDTO;
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
	private AlunoMapper alunoMapper;

	public List<Aluno> getTodosAlunos() {
		return alunoRepository.findAll();
	}

	public Optional<Aluno> getAlunoByCpf(String cpf) {
		String cleanCpf = cleanCpf(cpf);
		return alunoRepository.findFirstByCpf(cleanCpf);
	}

	public Optional<Aluno> getAlunoByEmail(String email) {
		return alunoRepository.findFirstByEmail(email);
	}

	public void addAluno(AlunoDTO dto) {
		String cleanCpf = cleanCpf(dto.getCpf());
		dto.setCpf(cleanCpf);
		Optional<Aluno> teste1 = alunoRepository.findFirstByCpf(dto.getCpf());
		if (teste1.isPresent()) return;
		Optional<Aluno> teste2 = alunoRepository.findFirstByEmail(dto.getEmail());
		if (teste2.isPresent()) return;
		Aluno newAluno = alunoMapper.map(dto);
		alunoRepository.save(newAluno);
	}

	public void updateAluno(Aluno aluno) {
		Aluno alunoLogado = getLogged();
		if (alunoLogado.getCpf().equals(aluno.getCpf())) {
			alunoRepository.deleteAllByCpf(aluno.getCpf());
			alunoRepository.save(aluno);
		}
	}

	public void updateAluno(AlunoDTO dto) {
		String cleanCpf = cleanCpf(dto.getCpf());
		dto.setCpf(cleanCpf);
		Optional<Aluno> teste1 = alunoRepository.findFirstByCpf(dto.getCpf());
		if (teste1.isPresent()) {
			alunoRepository.deleteAllByCpf(dto.getCpf());
			Aluno alunoAtualizado = alunoMapper.map(dto, teste1.get());
			alunoRepository.save(alunoAtualizado);
			return;
		}
		Optional<Aluno> teste2 = alunoRepository.findFirstByEmail(dto.getEmail());
		if (teste2.isPresent()) {
			alunoRepository.deleteAllByEmail(dto.getEmail());
			Aluno alunoAtualizado = alunoMapper.map(dto, teste2.get());
			alunoRepository.save(alunoAtualizado);
		}
	}

	public void deleteAluno(String cpf) {
		String cleanCpf = cleanCpf(cpf);
		alunoRepository.deleteAllByCpf(cleanCpf);
	}

	public Aluno getLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;
		if (principal instanceof UserDetails)
			email = ((UserDetails) principal).getUsername();
		else
			email = principal.toString();
		Optional<Aluno> alunoOp = getAlunoByEmail(email);
		return alunoOp.orElse(null);
	}

	private String cleanCpf(String cpf) {
		return cpf
			.replace(".", "")
			.replace("-", "")
			.trim();
	}
}
