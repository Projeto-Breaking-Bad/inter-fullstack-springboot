package com.breaking.ct.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.services.AlunoService;

@RequestMapping("/alunos")
@RestController
public class AlunoController {

	@Autowired
	private AlunoService alunoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Aluno> getAlunos() {
		return alunoService.getTodosAlunos();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{cpf}")
	public Aluno getAlunoEspecifico(@PathVariable String cpf) {
		return alunoService.getAlunoByCPF(cpf);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void novoAluno(@RequestBody Aluno aluno) {
		alunoService.addAluno(aluno);
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/{cpf}")
	public void atualizaAluno(@PathVariable String cpf, @RequestBody Aluno aluno) {
		alunoService.updateAluno(cpf, aluno);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{cpf}")
	public void deletarAluno(@PathVariable String cpf) {
		alunoService.deleteAluno(cpf);
	}
	
}
