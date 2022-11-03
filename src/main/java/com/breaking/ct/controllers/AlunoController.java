package com.breaking.ct.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.services.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	@Autowired
	private AlunoService alunoService;
	
	@GetMapping
	public ModelAndView getAlunos() {
		ModelAndView mv = new ModelAndView("listaAlunos");
		ArrayList<Aluno> alunos = alunoService.getTodosAlunos();
		if(!alunos.isEmpty())
			mv.addObject("alunos", alunos);
		return mv;
	}
	
	@GetMapping("/{cpf}")
	public ModelAndView getAlunoEspecifico(@PathVariable("cpf") String cpf) {
		Optional<Aluno> aluno = alunoService.getAlunoByCpf(cpf);
		ModelAndView mv = new ModelAndView("home");
		if(aluno.isEmpty()) 
			return new ModelAndView("alunoNaoEncontrado");
		mv.addObject("aluno", aluno.get());
		return mv;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView formularioCadastroAluno() {
		ModelAndView mv = new ModelAndView("cadastroAlunoNovo");
		return mv;
	}
	
	@PostMapping("/cadastrar")
	public ModelAndView novoAluno(Aluno aluno) {
		aluno.setCpf(aluno.getCpf().replace(".", "").replace("-", "").trim());
		alunoService.addAluno(aluno);
		return new ModelAndView("redirect:/alunos/" + aluno.getCpf());
	}
	
	@GetMapping("/atualizar/{cpf}")
	public ModelAndView formularioAtualizacaoAluno(@PathVariable("cpf") String cpf) {
		Optional<Aluno> aluno = alunoService.getAlunoByCpf(cpf);
		if(aluno.isEmpty())
			return new ModelAndView("alunoNaoEncontrado");
		ModelAndView mv = new ModelAndView("atualizacaoAlunoNovo");
		mv.addObject("aluno", aluno.get());
		return mv;
	}
	
	@PostMapping("/atualizar")
	public ModelAndView atualizaAluno(Aluno aluno) {
		aluno.setCpf(aluno.getCpf().replace(".", "").replace("-", "").trim());
		alunoService.updateAluno(aluno);
		return new ModelAndView("redirect:/alunos/" + aluno.getCpf());
	}
	
	@PostMapping("/deletar/{cpf}")
	public ModelAndView deletarAluno(@PathVariable("cpf") String cpf) {
		alunoService.deleteAluno(cpf);
		return new ModelAndView("redirect:/alunos");
	}
	
}
