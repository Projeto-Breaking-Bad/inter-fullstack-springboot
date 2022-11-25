package com.breaking.ct.controllers;

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
@RequestMapping("/s")
public class AlunoControllerNovo {
	
	@Autowired
	private AlunoService alunoService;
	
	@GetMapping
	public ModelAndView homeAluno() {
		ModelAndView mv = new ModelAndView("student/homeAluno");
		Aluno aluno = alunoService.getLogged();
		aluno.setSenha("");
		mv.addObject("aluno", aluno);
		return mv;
	}
	
	@GetMapping("/alunos/consultar/{cpf}")
	public ModelAndView perfilAluno(@PathVariable("cpf") String cpf) {

		ModelAndView mv = new ModelAndView("errors/alunoNaoEncontrado");
		
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		
		if(alunoConsultado.isEmpty())
			return mv;
		
		Aluno aluno = alunoConsultado.get();
		aluno.setSenha("");
		mv.addObject("aluno", aluno);
		
		if(alunoService.getLogged().getCpf().equals(cpf))
			mv.setViewName("student/perfilAlunoEdicao");
		else
			mv.setViewName("student/perfilAlunoConsulta");
		
		return mv;
	}
	
	@GetMapping("/alunos/atualizar/{cpf}")
	public ModelAndView formularioAtualizacaoAluno(@PathVariable("cpf") String cpf) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");
		
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		
		if(alunoConsultado.isEmpty())
			return mv;
		
		Aluno aluno = alunoConsultado.get();
		aluno.setSenha("");
		
		if(alunoService.getLogged().getCpf().equals(cpf)) {
			mv.addObject("aluno", aluno);
			mv.setViewName("student/perfilAlunoAtualizacao");
		}
		
		return mv;
	}
	
	@PostMapping("/atualizar/{cpf}")
	public ModelAndView atualizarAluno(@PathVariable("cpf") String cpf, Aluno aluno) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");

		aluno.setCpf(aluno.getCpf().replace(".", "").replace("-", "").trim());
		
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);

		if(alunoConsultado.isEmpty())
			return mv;
		
		if(alunoService.getLogged().getCpf().equals(cpf)) {
			if(aluno.getCpf().equals(cpf)) {
				alunoService.updateAluno(aluno);
			} else {
				alunoService.deleteAluno(cpf);
				alunoService.addAluno(aluno);
			}
			mv.setViewName("redirect:/s/alunos/consultar/"+aluno.getCpf());
		}
		
		return mv;
	}
	
	@PostMapping("/deletar/{cpf}")
	public ModelAndView deletarAluno(@PathVariable("cpf") String cpf) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");
		
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		
		if(alunoConsultado.isEmpty())
			return mv;
		
		if(alunoService.getLogged().getCpf().equals(cpf)) {
			alunoService.deleteAluno(cpf);
			mv.setViewName("redirect:/logout");
		}
		
		return mv;
	}
	
	// Vagas...
	
}
