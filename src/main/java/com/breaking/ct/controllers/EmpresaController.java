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

import com.breaking.ct.models.Empresa;
import com.breaking.ct.services.EmpresaService;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping
	public ModelAndView getEmpresas() {
		ModelAndView mv = new ModelAndView("listaEmpresas");
		ArrayList<Empresa> empresas = empresaService.getTodosEmpresas();
		if(!empresas.isEmpty())
			mv.addObject("empresas", empresas);
		return mv;
	}
	
	@GetMapping("/{cnpj}")
	public ModelAndView getEmpresaEspecifico(@PathVariable("cnpj") String cnpj) {
		Optional<Empresa> empresa = empresaService.getEmpresaByCnpj(cnpj);
		ModelAndView mv = new ModelAndView("perfilEmpresa");
		if(empresa.isEmpty()) 
			return new ModelAndView("empresaNaoEncontrado");
		mv.addObject("empresa", empresa.get());
		return mv;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView formularioCadastroEmpresa() {
		ModelAndView mv = new ModelAndView("cadastroEmpresa");
		return mv;
	}
	
	@PostMapping("/cadastrar")
	public ModelAndView novoEmpresa(Empresa empresa) {
		empresa.setCnpj(empresa.getCnpj().replace(".", "").replace("-", "").trim());
		empresaService.addEmpresa(empresa);
		return new ModelAndView("redirect:/empresas/" + empresa.getCnpj());
	}
	
	@GetMapping("/atualizar/{cnpj}")
	public ModelAndView formularioAtualizacaoEmpresa(@PathVariable("cnpj") String cnpj) {
		Optional<Empresa> empresa = empresaService.getEmpresaByCnpj(cnpj);
		if(empresa.isEmpty())
			return new ModelAndView("empresaNaoEncontrado");
		ModelAndView mv = new ModelAndView("atualizacaoEmpresa");
		mv.addObject("empresa", empresa.get());
		return mv;
	}
	
	@PostMapping("/atualizar")
	public ModelAndView atualizaEmpresa(Empresa empresa) {
		empresaService.updateEmpresa(empresa);
		return new ModelAndView("redirect:/empresas/" + empresa.getCnpj());
	}
	
	@PostMapping("/deletar/{cnpj}")
	public ModelAndView deletarEmpresa(@PathVariable("cnpj") String cnpj) {
		empresaService.deleteEmpresa(cnpj);
		return new ModelAndView("redirect:/empresas");
	}

}
