package com.breaking.ct.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastrar")
	public ModelAndView formularioCadastroAluno() {
		ModelAndView mv = new ModelAndView("cadastroAluno");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView novoAluno(
			@RequestParam("nome") String nome, 
			@RequestParam("cpf") String cpf,
			@RequestParam("ra") Optional<String> ra,
			@RequestParam("dataNasc") String dataNasc,
			@RequestParam("logradouro") String logradouro,
			@RequestParam("cep") String cep,
			@RequestParam("bairro") String bairro,
			@RequestParam("cidade") String cidade,
			@RequestParam("numero") String numero,
			@RequestParam("complemento") Optional<String> complemento,
			@RequestParam("uf") String uf,
			@RequestParam("whatsapp") String whatsapp,
			@RequestParam("email") String email,
			@RequestParam("senha") String senha) {
		Aluno aluno = new Aluno();
		aluno.setNome(nome);
		aluno.setCPF(cpf);
		aluno.setRa(ra);
		aluno.setDataNasc(dataNasc);
		aluno.setLogradouro(logradouro);
		aluno.setCEP(cep);
		aluno.setBairro(bairro);
		aluno.setCidade(cidade);
		aluno.setNumero(numero);
		aluno.setComplemento(complemento);
		aluno.setUF(uf);
		aluno.setWhatsapp(whatsapp);
		aluno.setEmail(email);
		aluno.setSenha(senha);
		alunoService.addAluno(aluno);
		return new ModelAndView("redirect:/alunos");
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
