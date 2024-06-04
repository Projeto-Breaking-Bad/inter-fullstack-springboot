package com.breaking.ct.controllers;

import com.breaking.ct.dto.AlunoDTO;
import com.breaking.ct.dto.EmpresaDTO;
import com.breaking.ct.services.AlunoService;
import com.breaking.ct.services.EmpresaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView novoAluno(AlunoDTO dto) {
		ModelAndView mv = new ModelAndView("redirect:/login");
		alunoService.addAluno(dto);
		return mv;
	}

	@GetMapping("/empresa")
	public ModelAndView formularioCadastroEmpresa() {
		return new ModelAndView("company/cadastroEmpresa");
	}

	@PostMapping("/empresa")
	public ModelAndView novaEmpresa(EmpresaDTO dto) {
		ModelAndView mv = new ModelAndView("redirect:/login");
		empresaService.addEmpresa(dto);
		return mv;
	}
}
