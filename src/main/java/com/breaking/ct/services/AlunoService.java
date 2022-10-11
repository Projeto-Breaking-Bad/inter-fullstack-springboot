package com.breaking.ct.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.breaking.ct.models.Aluno;

@Service
public class AlunoService {

	private List<Aluno> alunos = new ArrayList<>(Arrays.asList(
			new Aluno("40028-922", "Rua imaginária", "123", "", "Jardim das flores",
					"Itaquaquecetuba", "SP", "(11)94002-8922", "usadordesitesestranhos@hotmail.com",
					"senha", "Fulano da Silva", "000123345", "12312312312",
					"01/01/1900", "DSM", "Sei Python, Javascript apenas"),
			new Aluno("89224-002", "Rua 2 imaginária", "321", "Sala 103", "Beco das rosa",
					"Paraná", "PR", "(21)94002-8922", "criadordesitesestranhos@gmail.com",
					"senha", "Ciclano Santos", "000123346", "23423423423",
					"31/12/1999", "Comex", "Sei espanhos, português apenas")
			));
	
	public List<Aluno> getTodosAlunos() {
		return alunos;
	}

	public Aluno getAlunoByCPF(String cpf) {
		return alunos.stream().filter(aluno -> aluno.getCPF().equals(cpf)).findFirst().get();
	}

	public void addAluno(Aluno aluno) {
		alunos.add(aluno);
	}

	public void updateAluno(String cpf, Aluno aluno) {
		for(int i = 0;i<alunos.size();i++) {
			Aluno t = alunos.get(i);
			if(t.getCPF().equals(cpf)) {
				alunos.set(i, aluno);
				return;
			}
		}
	}

	public void deleteAluno(String cpf) {
		alunos.removeIf(aluno -> aluno.getCPF().equals(cpf));
	}
	
}
