package com.breaking.ct.controllers;

import com.breaking.ct.models.Empresa;
import com.breaking.ct.models.Vaga;
import com.breaking.ct.services.EmpresaService;
import com.breaking.ct.services.VagaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/c")
@AllArgsConstructor
public class EmpresaController {

	private EmpresaService empresaService;
	private VagaService vagaService;
	private static final String EMPRESA_LOGADA = "empresaLogada";
	private static final String REDIRECT_LINK = "redirect:/redirect";

	@GetMapping
	public ModelAndView homeEmpresa() {
		ModelAndView mv = new ModelAndView("company/homeEmpresa");
		mv.addObject(EMPRESA_LOGADA, empresaService.getLogged());
		return mv;
	}

	@GetMapping("/empresas/consultar/{cnpj}")
	public ModelAndView perfilEmpresa(@PathVariable("cnpj") String cnpj) {
		ModelAndView mv = new ModelAndView("errors/empresaNaoEncontrada");
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		mv.addObject("empresa", empresaConsultada.get());

		Empresa empresaLogada = empresaService.getLogged();
		mv.addObject(EMPRESA_LOGADA, empresaLogada);

		if (empresaLogada.getCnpj().equals(cnpj))
			mv.setViewName("company/perfilEmpresaEdicao");
		else
			mv.setViewName("company/perfilEmpresaConsulta");

		return mv;
	}

	@GetMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView formularioAtualizacaoEmpresa(@PathVariable("cnpj") String cnpj) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		Empresa empresaLogada = empresaService.getLogged();
		mv.addObject(EMPRESA_LOGADA, empresaLogada);

		if (empresaLogada.getCnpj().equals(cnpj)) {
			mv.addObject("empresa", empresaConsultada.get());
			mv.setViewName("company/perfilEmpresaAtualizacao");
		}
		return mv;
	}

	@PostMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView atualizarEmpresa(@PathVariable("cnpj") String cnpj, Empresa empresa) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		empresa.setCnpj(empresa.getCnpj().replace(".", "").replace("-", "").replace("/", "").trim());

		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		if (empresaService.getLogged().getCnpj().equals(cnpj)) {
			if (empresa.getCnpj().equals(cnpj)) {
				empresaService.updateEmpresa(empresa);
			} else {
				empresaService.deleteEmpresa(cnpj);
				empresaService.addEmpresa(empresa);
			}
			mv.setViewName("redirect:/c/empresas/consultar/" + empresa.getCnpj());
		}
		return mv;
	}

	@PostMapping("/empresas/deletar/{cnpj}")
	public ModelAndView deletarEmpresa(@PathVariable("cnpj") String cnpj) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		if (empresaService.getLogged().getCnpj().equals(cnpj)) {
			empresaService.deleteEmpresa(cnpj);
			mv.setViewName("redirect:/logout");
		}
		return mv;
	}

	@GetMapping("/acessibilidade")
	public ModelAndView acessibilidade() {
		ModelAndView mv = new ModelAndView("company/acessibilidade");
		mv.addObject(EMPRESA_LOGADA, empresaService.getLogged());
		return mv;
	}

	@GetMapping("/vagas")
	public ModelAndView verVagas() {
		ModelAndView mv = new ModelAndView("company/listaVagas");
		mv.addObject("vagas", vagaService.getTodasVagas());
		mv.addObject(EMPRESA_LOGADA, empresaService.getLogged());
		return mv;
	}

	@GetMapping("/vagas/cadastro")
	public ModelAndView formularioCadastroVaga() {
		ModelAndView mv = new ModelAndView("company/cadastroVaga");
		mv.addObject(EMPRESA_LOGADA, empresaService.getLogged());
		return mv;
	}

	@PostMapping("/vagas/cadastro")
	public ModelAndView novoVaga(Vaga vaga) {
		vagaService.addVaga(vaga);
		return new ModelAndView("redirect:/c/vagas/consultar/" + vaga.getId());
	}

	@GetMapping("/vagas/consultar/{id}")
	public ModelAndView verVagasEspecifica(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(id);
		if (vagaConsultada.isEmpty())
			return mv;

		mv.addObject(EMPRESA_LOGADA, empresaService.getLogged());
		mv.addObject("vaga", vagaConsultada.get());
		mv.setViewName("company/perfilVaga");
		return mv;

	}

	@GetMapping("/vagas/atualizar/{id}")
	public ModelAndView formularioAtualizacaoVaga(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("company/perfilVagaAtualizacao");
		Optional<Vaga> vaga = vagaService.getVagaById(id);
		if (vaga.isEmpty())
			return new ModelAndView("errors/vagaNaoEncontrada");

		mv.addObject(EMPRESA_LOGADA, empresaService.getLogged());
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

	@GetMapping("/vagas/cadastradas")
	public ModelAndView verVagasCadastradas() {
		ModelAndView mv = new ModelAndView("company/listaVagasCadastradas");
		Empresa empresaLogada = empresaService.getLogged();
		mv.addObject(EMPRESA_LOGADA, empresaLogada);

		List<Vaga> vagas = vagaService.getVagasByListaIds(empresaLogada.getListaIdVagasCriadas());
		if (!vagas.isEmpty())
			mv.addObject("vagas", vagas);

		return mv;
	}

}
