package com.breaking.ct.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.models.Vaga;
import com.breaking.ct.services.AlunoService;
import com.breaking.ct.services.VagaService;

@RestController
@RequestMapping("/s")
public class AlunoController {
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private VagaService vagaService;
	
	@GetMapping 
	public ModelAndView homeAluno() {
		ModelAndView mv = new ModelAndView("student/homeAluno");
		Aluno alunoLogado = alunoService.getLogged();
		alunoLogado.setSenha("");
		mv.addObject("alunoLogado", alunoLogado);
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
		
		Aluno alunoLogado = alunoService.getLogged();
		alunoLogado.setSenha("");
		mv.addObject("alunoLogado", alunoLogado);
		
		if(alunoLogado.getCpf().equals(cpf))
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
		
		Aluno alunoLogado = alunoService.getLogged();
		alunoLogado.setSenha("");
		mv.addObject("alunoLogado", alunoLogado);
		
		if(alunoService.getLogged().getCpf().equals(cpf)) {
			mv.addObject("aluno", aluno);
			mv.setViewName("student/perfilAlunoAtualizacao");
		}
		
		return mv;
	}
	
	@PostMapping("/alunos/atualizar/{cpf}")
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
	
	@PostMapping("/alunos/deletar/{cpf}")
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
	
	@GetMapping("/acessibilidade")
	public ModelAndView acessibilidade() {
		ModelAndView mv = new ModelAndView("student/acessibilidade");
		Aluno alunoLogado = alunoService.getLogged();
		alunoLogado.setSenha("");
		mv.addObject("alunoLogado", alunoLogado);
		return mv;
	}
	
	/**
	 * VAGAS ALUNO
	 */
	@GetMapping("/vagas")
	public ModelAndView verVagas() {
		
		ModelAndView mv = new ModelAndView("student/listaVagas");
		List<Vaga> vagas = vagaService.getTodasVagas();
		mv.addObject("vagas", vagas);
		
		Aluno alunoLogado = alunoService.getLogged();
		alunoLogado.setSenha("");
		mv.addObject("alunoLogado", alunoLogado);
		
		return mv;
	}
	
	@GetMapping("/vagas/consultar/{id}")
	public ModelAndView verVagasEspecifica(@PathVariable("id") String id) {
		
		ModelAndView mv = new ModelAndView("vagaNaoEncontrada");
		
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(id);
		
		if(vagaConsultada.isEmpty())
			return mv;
		
		Aluno alunoLogado = alunoService.getLogged();
		alunoLogado.setSenha("");
		mv.addObject("alunoLogado", alunoLogado);
		
		mv.addObject("vaga", vagaConsultada.get());
		mv.setViewName("student/perfilVaga");
		return mv;
		
	}
	
	
	
	/**
	 *  So criar estas rotas depois de mexer melhor nos
	 * models e no banco de dados
	 */
	
	// @GetMapping("/vagas/inscritas")
	// @GetMapping("/vagas/inscritas/{id}")
	// @PostMapping("/vagas/inscrever/{id}")
	// @PostMapping("/vagas/desinscrever/{id}")
	
}
