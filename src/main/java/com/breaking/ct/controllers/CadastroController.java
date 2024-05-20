package com.breaking.ct.controllers;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.models.Empresa;
import com.breaking.ct.services.AlunoService;
import com.breaking.ct.services.EmpresaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
@RequestMapping("/cadastro")
@AllArgsConstructor
public class CadastroController {

	private AlunoService alunoService;
	private EmpresaService empresaService;
	
	@GetMapping("/aluno")
	public ModelAndView formularioCadastroAluno() {
		return new ModelAndView("student/cadastroAluno");
	}
	
	@PostMapping("/aluno")
	public ModelAndView novoAluno(Aluno aluno) {
		ModelAndView mv = new ModelAndView("redirect:/login");
		
		aluno.setCpf(aluno.getCpf().replace(".", "").replace("-", "").trim());
		
		Optional<Aluno> teste1 = alunoService.getAlunoByCpf(aluno.getCpf());
		Optional<Aluno> teste2 = alunoService.getAlunoByEmail(aluno.getEmail());
		
		if(teste1.isEmpty() && teste2.isEmpty())
			alunoService.addAluno(aluno);
		return mv;
	}
	
	@GetMapping("/empresa")
	public ModelAndView formularioCadastroEmpresa() {
		return new ModelAndView("company/cadastroEmpresa");
	}
	
	@PostMapping("/empresa")
	public ModelAndView novaEmpresa(Empresa empresa) {
		ModelAndView mv = new ModelAndView("redirect:/login");
		empresa.setCnpj(empresa.getCnpj().replace(".", "").replace("-", "").replace("/", "").trim());
		
		Optional<Empresa> teste1 = empresaService.getEmpresaByCnpj(empresa.getCnpj());
		Optional<Empresa> teste2 = empresaService.getEmpresaByEmail(empresa.getEmail());
		
		if(teste1.isEmpty() && teste2.isEmpty())
			empresaService.addEmpresa(empresa);
		return mv;
	}
	
}
