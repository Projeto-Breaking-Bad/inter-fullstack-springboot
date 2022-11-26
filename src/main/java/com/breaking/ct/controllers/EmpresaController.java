package com.breaking.ct.controllers;

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
@RequestMapping("/c")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping
	public ModelAndView homeEmpresa() {
		ModelAndView mv = new ModelAndView("company/homeEmpresa");
		Empresa empresa = empresaService.getLogged();
		empresa.setSenha("");
		mv.addObject("empresa", empresa);
		return mv;
	}
	
	@GetMapping("/empresas/consultar/{cnpj}")
	public ModelAndView perfilEmpresa(@PathVariable("cnpj") String cnpj) {

		ModelAndView mv = new ModelAndView("errors/empresaNaoEncontrada");
		
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		
		if(empresaConsultada.isEmpty())
			return mv;
		
		Empresa empresa = empresaConsultada.get();
		empresa.setSenha("");
		mv.addObject("empresa", empresa);
		
		if(empresaService.getLogged().getCnpj().equals(cnpj))
			mv.setViewName("company/perfilEmpresaEdicao");
		else
			mv.setViewName("company/perfilEmpresaConsulta");
		
		return mv;
	}
	
	@GetMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView formularioAtualizacaoEmpresa(@PathVariable("cnpj") String cnpj) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");
		
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		
		if(empresaConsultada.isEmpty())
			return mv;
		
		Empresa empresa = empresaConsultada.get();
		empresa.setSenha("");
		
		if(empresaService.getLogged().getCnpj().equals(cnpj)) {
			mv.addObject("empresa", empresa);
			mv.setViewName("company/perfilEmpresaAtualizacao");
		}
		
		return mv;
	}
	
	@PostMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView atualizarEmpresa(@PathVariable("cnpj") String cnpj, Empresa empresa) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");

		empresa.setCnpj(empresa.getCnpj().replace(".", "").replace("-", "").replace("/", "").trim());
		
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);

		if(empresaConsultada.isEmpty())
			return mv;
		
		if(empresaService.getLogged().getCnpj().equals(cnpj)) {
			if(empresa.getCnpj().equals(cnpj)) {
				empresaService.updateEmpresa(empresa);
			} else {
				empresaService.deleteEmpresa(cnpj);
				empresaService.addEmpresa(empresa);
			}
			mv.setViewName("redirect:/c/empresas/consultar/"+empresa.getCnpj());
		}
		
		return mv;
	}
	
	@PostMapping("/empresas/deletar/{cnpj}")
	public ModelAndView deletarEmpresa(@PathVariable("cnpj") String cnpj) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");
		
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		
		if(empresaConsultada.isEmpty())
			return mv;
		
		if(empresaService.getLogged().getCnpj().equals(cnpj)) {
			empresaService.deleteEmpresa(cnpj);
			mv.setViewName("redirect:/logout");
		}
		
		return mv;
	}
	
	@GetMapping("/acessibilidade")
	public ModelAndView acessibilidade() {
		ModelAndView mv = new ModelAndView("company/acessibilidade");
		return mv;
	}
	
	// Vagas...
	
}
