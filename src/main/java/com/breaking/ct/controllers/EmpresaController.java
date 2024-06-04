package com.breaking.ct.controllers;

import com.breaking.ct.dto.EmpresaDTO;
import com.breaking.ct.dto.VagaDTO;
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
	private static final String VAGA = "vaga";
	private static final String VAGAS = "vagas";
	private static final String EMPRESA = "empresa";
	private static final String EMPRESA_LOGADA = "empresaLogada";
	private static final String REDIRECT_LINK = "redirect:/redirect";

	@GetMapping
	public ModelAndView homeEmpresa() {
		ModelAndView mv = new ModelAndView("company/homeEmpresa");
		mv.addObject(EMPRESA_LOGADA, empresaService.getLogged());
		return mv;
	}

	@GetMapping("/empresas/consultar/{cnpj}")
	public ModelAndView perfilEmpresa(@PathVariable String cnpj) {
		ModelAndView mv = new ModelAndView("errors/empresaNaoEncontrada");
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		Empresa empresaLogada = empresaService.getLogged();
		mv.addObject(EMPRESA, empresaConsultada.get());
		mv.addObject(EMPRESA_LOGADA, empresaLogada);

		if (empresaLogada.getCnpj().equals(cnpj))
			mv.setViewName("company/perfilEmpresaEdicao");
		else
			mv.setViewName("company/perfilEmpresaConsulta");
		return mv;
	}

	@GetMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView formularioAtualizacaoEmpresa(@PathVariable String cnpj) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		Empresa empresaLogada = empresaService.getLogged();
		mv.addObject(EMPRESA_LOGADA, empresaLogada);

		if (empresaLogada.getCnpj().equals(cnpj)) {
			mv.addObject(EMPRESA, empresaConsultada.get());
			mv.setViewName("company/perfilEmpresaAtualizacao");
		}
		return mv;
	}

	@PostMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView atualizarEmpresa(@PathVariable String cnpj, EmpresaDTO dto) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		String cleanCnpj = cnpj.replace(".", "").replace("-", "").replace("/", "").trim();
		dto.setCnpj(dto.getCnpj().replace(".", "").replace("-", "").replace("/", "").trim());
		if (empresaService.getLogged().getCnpj().equals(cleanCnpj)) {
			if (dto.getCnpj().equals(cleanCnpj)) {
				empresaService.updateEmpresa(dto);
			} else {
				empresaService.deleteEmpresa(cleanCnpj);
				empresaService.addEmpresa(dto);
			}
			mv.setViewName("redirect:/c/empresas/consultar/" + dto.getCnpj());
		}
		return mv;
	}

	@PostMapping("/empresas/deletar/{cnpj}")
	public ModelAndView deletarEmpresa(@PathVariable String cnpj) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		String cleanCnpj = cnpj.replace(".", "").replace("-", "").replace("/", "").trim();
		if (empresaService.getLogged().getCnpj().equals(cleanCnpj)) {
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
		mv.addObject(VAGAS, vagaService.getTodasVagas());
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
	public ModelAndView novoVaga(VagaDTO dto) {
		Vaga vagaCadastrada = vagaService.addVaga(dto);
		return new ModelAndView("redirect:/c/vagas/consultar/" + vagaCadastrada.getId());
	}

	@GetMapping("/vagas/consultar/{idVaga}")
	public ModelAndView verVagasEspecifica(@PathVariable String idVaga) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(idVaga);
		if (vagaConsultada.isEmpty())
			return mv;

		mv.addObject(VAGA, vagaConsultada.get());
		mv.addObject(EMPRESA_LOGADA, empresaService.getLogged());
		mv.setViewName("company/perfilVaga");
		return mv;
	}

	@GetMapping("/vagas/atualizar/{idVaga}")
	public ModelAndView formularioAtualizacaoVaga(@PathVariable String idVaga) {
		ModelAndView mv = new ModelAndView("company/perfilVagaAtualizacao");
		Optional<Vaga> vaga = vagaService.getVagaById(idVaga);
		if (vaga.isEmpty())
			return new ModelAndView("errors/vagaNaoEncontrada");

		mv.addObject(VAGA, vaga.get());
		mv.addObject(EMPRESA_LOGADA, empresaService.getLogged());
		return mv;
	}

	@PostMapping("/vagas/atualizar/{idVaga}")
	public ModelAndView atualizaVaga(@PathVariable String idVaga, VagaDTO dto) {
		vagaService.updateVaga(idVaga, dto);
		return new ModelAndView("redirect:/c/vagas/consultar/" + dto.getId());
	}

	@PostMapping("/vagas/deletar/{idVaga}")
	public ModelAndView deletarVaga(@PathVariable String idVaga) {
		vagaService.deleteVaga(idVaga);
		return new ModelAndView("redirect:/c/vagas");
	}

	@GetMapping("/vagas/cadastradas")
	public ModelAndView verVagasCadastradas() {
		ModelAndView mv = new ModelAndView("company/listaVagasCadastradas");
		Empresa empresaLogada = empresaService.getLogged();
		mv.addObject(EMPRESA_LOGADA, empresaLogada);

		List<Vaga> vagas = vagaService.getVagasByListaIds(empresaLogada.getListaIdVagasCriadas());
		mv.addObject(VAGAS, vagas);

		return mv;
	}
}
