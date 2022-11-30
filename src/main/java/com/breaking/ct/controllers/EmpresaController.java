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

import com.breaking.ct.models.Empresa;
import com.breaking.ct.models.Vaga;
import com.breaking.ct.services.EmpresaService;
import com.breaking.ct.services.VagaService;

@RestController
@RequestMapping("/c")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private VagaService vagaService;
	
	@GetMapping
	public ModelAndView homeEmpresa() {
		ModelAndView mv = new ModelAndView("company/homeEmpresa");
		Empresa empresaLogada = empresaService.getLogged();
		empresaLogada.setSenha("");
		mv.addObject("empresaLogada", empresaLogada);
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

		Empresa empresaLogada = empresaService.getLogged();
		empresaLogada.setSenha("");
		mv.addObject("empresaLogada", empresaLogada);
		
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

		Empresa empresaLogada = empresaService.getLogged();
		empresaLogada.setSenha("");
		mv.addObject("empresaLogada", empresaLogada);
		
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
		Empresa empresaLogada = empresaService.getLogged();
		empresaLogada.setSenha("");
		mv.addObject("empresaLogada", empresaLogada);
		return mv;
	}
	
	/**
	 * VAGAS EMPRESA
	 */
	@GetMapping("/vagas")
	public ModelAndView verVagas() {
		
		ModelAndView mv = new ModelAndView("company/listaVagas");
		List<Vaga> vagas = vagaService.getTodasVagas();
		mv.addObject("vagas", vagas);
		
		Empresa empresaLogada = empresaService.getLogged();
		empresaLogada.setSenha("");
		mv.addObject("empresaLogada", empresaLogada);
		
		return mv;
	}
	
	@GetMapping("/vagas/cadastro")
	public ModelAndView formularioCadastroVaga() {
		ModelAndView mv = new ModelAndView("company/cadastroVaga");
		Empresa empresaLogada = empresaService.getLogged();
		empresaLogada.setSenha("");
		mv.addObject("empresaLogada", empresaLogada);
		return mv;
	}
	
	@PostMapping("/vagas/cadastro")
	public ModelAndView novoVaga(Vaga vaga) {
		vagaService.addVaga(vaga);
		return new ModelAndView("redirect:/c/vagas/consultar/" + vaga.getId());
	}
	
	@GetMapping("/vagas/consultar/{id}")
	public ModelAndView verVagasEspecifica(@PathVariable("id") String id) {
		
		ModelAndView mv = new ModelAndView("vagaNaoEncontrada");
		
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(id);
		
		if(vagaConsultada.isEmpty())
			return mv;

		Empresa empresaLogada = empresaService.getLogged();
		empresaLogada.setSenha("");
		mv.addObject("empresaLogada", empresaLogada);
		
		mv.addObject("vaga", vagaConsultada.get());
		mv.setViewName("company/perfilVaga");
		return mv;
		
	}
	
	@GetMapping("/vagas/atualizar/{id}")
	public ModelAndView formularioAtualizacaoVaga(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("company/perfilVagaAtualizacao");
		
		Optional<Vaga> vaga = vagaService.getVagaById(id);
		if(vaga.isEmpty())
			return new ModelAndView("vagaNaoEncontrada");
		
		Empresa empresaLogada = empresaService.getLogged();
		empresaLogada.setSenha("");
		mv.addObject("empresaLogada", empresaLogada);
		
		mv.addObject("vaga", vaga.get());
		return mv;
	}
	
	@PostMapping("/vagas/atualizar/{id}")
	public ModelAndView atualizaVaga(@PathVariable("id") String id, Vaga vaga) {
		vagaService.updateVaga(vaga);
		return new ModelAndView("redirect:/c/vagas/consultar/" + vaga.getId());
	}
	
	@PostMapping("/vagas/deletar/{id}")
	public ModelAndView deletarVaga(@PathVariable("id") String id) {
		vagaService.deleteVaga(id);
		return new ModelAndView("redirect:/c/vagas");
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
