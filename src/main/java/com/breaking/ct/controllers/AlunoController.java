package com.breaking.ct.controllers;

import com.breaking.ct.dto.AlunoDTO;
import com.breaking.ct.models.Aluno;
import com.breaking.ct.models.Vaga;
import com.breaking.ct.services.AlunoService;
import com.breaking.ct.services.VagaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/s")
@AllArgsConstructor
public class AlunoController {
	private AlunoService alunoService;
	private VagaService vagaService;
	private static final String VAGA = "vaga";
	private static final String VAGAS = "vagas";
	private static final String ALUNO = "aluno";
	private static final String ALUNO_LOGADO = "alunoLogado";
	private static final String REDIRECT_LINK = "redirect:/redirect";

	@GetMapping
	public ModelAndView homeAluno() {
		ModelAndView mv = new ModelAndView("student/homeAluno");
		mv.addObject(ALUNO_LOGADO, alunoService.getLogged());
		return mv;
	}

	@GetMapping("/alunos/consultar/{cpf}")
	public ModelAndView perfilAluno(@PathVariable String cpf) {
		ModelAndView mv = new ModelAndView("errors/alunoNaoEncontrado");
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		Aluno alunoLogado = alunoService.getLogged();
		mv.addObject(ALUNO, alunoConsultado.get());
		mv.addObject(ALUNO_LOGADO, alunoLogado);

		if (alunoLogado.getCpf().equals(cpf))
			mv.setViewName("student/perfilAlunoEdicao");
		else
			mv.setViewName("student/perfilAlunoConsulta");
		return mv;
	}

	@GetMapping("/alunos/atualizar/{cpf}")
	public ModelAndView formularioAtualizacaoAluno(@PathVariable String cpf) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		Aluno alunoLogado = alunoService.getLogged();
		mv.addObject(ALUNO_LOGADO, alunoLogado);

		if (alunoLogado.getCpf().equals(cpf)) {
			mv.addObject(ALUNO, alunoConsultado.get());
			mv.setViewName("student/perfilAlunoAtualizacao");
		}
		return mv;
	}

	@PostMapping("/alunos/atualizar/{cpf}")
	public ModelAndView atualizarAluno(@PathVariable String cpf, AlunoDTO dto) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		String cleanCpf = cpf.replace(".", "").replace("-", "").trim();
		dto.setCpf(dto.getCpf().replace(".", "").replace("-", "").trim());
		if (alunoService.getLogged().getCpf().equals(cleanCpf)) {
			if (dto.getCpf().equals(cleanCpf)) {
				alunoService.updateAluno(dto);
			} else {
				alunoService.deleteAluno(cleanCpf);
				alunoService.addAluno(dto);
			}
			mv.setViewName("redirect:/s/alunos/consultar/" + dto.getCpf());
		}
		return mv;
	}

	@PostMapping("/alunos/deletar/{cpf}")
	public ModelAndView deletarAluno(@PathVariable String cpf) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		String cleanCpf = cpf.replace(".", "").replace("-", "").trim();
		if (alunoService.getLogged().getCpf().equals(cleanCpf)) {
			alunoService.deleteAluno(cpf);
			mv.setViewName("redirect:/logout");
		}
		return mv;
	}

	@GetMapping("/acessibilidade")
	public ModelAndView acessibilidade() {
		ModelAndView mv = new ModelAndView("student/acessibilidade");
		mv.addObject(ALUNO_LOGADO, alunoService.getLogged());
		return mv;
	}

	@GetMapping("/vagas")
	public ModelAndView verVagas() {
		ModelAndView mv = new ModelAndView("student/listaVagas");
		mv.addObject(VAGAS, vagaService.getTodasVagas());
		mv.addObject(ALUNO_LOGADO, alunoService.getLogged());
		return mv;
	}

	@GetMapping("/vagas/consultar/{idVaga}")
	public ModelAndView verVagaEspecifica(@PathVariable String idVaga) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(idVaga);
		if (vagaConsultada.isEmpty())
			return mv;

		Aluno alunoLogado = alunoService.getLogged();
		mv.addObject(ALUNO_LOGADO, alunoLogado);
		mv.addObject(VAGA, vagaConsultada.get());
		mv.setViewName("student/perfilVaga");

		List<String> listaIdsVagasInscritas = alunoLogado.getListaIdVagasAplicadas();
		mv.addObject("estaInscrito", listaIdsVagasInscritas.contains(vagaConsultada.get().getId()));

		return mv;
	}

	@GetMapping("/vagas/inscritas")
	public ModelAndView verVagasInscritas() {
		ModelAndView mv = new ModelAndView("student/listaVagasInscritas");
		Aluno alunoLogado = alunoService.getLogged();
		mv.addObject(ALUNO_LOGADO, alunoLogado);

		List<String> listaIdsVagasInscritas = alunoLogado.getListaIdVagasAplicadas();
		if (!listaIdsVagasInscritas.isEmpty()) {
			List<Vaga> vagasInscritas = vagaService.getVagasByListaIds(listaIdsVagasInscritas);
			mv.addObject(VAGAS, vagasInscritas);
		}
		return mv;
	}

	@GetMapping("/vagas/inscritas/{idVaga}")
	public ModelAndView verVagaInscritaEspecifica(@PathVariable String idVaga) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Aluno alunoLogado = alunoService.getLogged();
		mv.addObject(ALUNO_LOGADO, alunoLogado);

		Optional<Vaga> vagaConsultada = vagaService.getVagaById(idVaga);
		if (vagaConsultada.isEmpty())
			return mv;

		List<String> listaIdsVagasInscritas = alunoLogado.getListaIdVagasAplicadas();
		mv.addObject("estaInscrito", listaIdsVagasInscritas.contains(vagaConsultada.get().getId()));
		mv.addObject(VAGA, vagaConsultada.get());
		mv.setViewName("student/perfilVagaInscrita");
		return mv;
	}

	@PostMapping("/vagas/consulta/inscrever/{idVaga}")
	public ModelAndView inscreverEmVagaConsulta(@PathVariable String idVaga) {
		vagaService.inscreverVaga(idVaga);
		return new ModelAndView("redirect:/s/vagas/consultar/" + idVaga);
	}

	@PostMapping("/vagas/consulta/desinscrever/{idVaga}")
	public ModelAndView desinscreverEmVagaConsulta(@PathVariable String idVaga) {
		vagaService.desinscreverVaga(idVaga);
		return new ModelAndView("redirect:/s/vagas/consultar/" + idVaga);
	}

	@PostMapping("/vagas/inscritas/inscrever/{idVaga}")
	public ModelAndView inscreverEmVagaInscrita(@PathVariable String idVaga) {
		vagaService.inscreverVaga(idVaga);
		return new ModelAndView("redirect:/s/vagas/inscritas/" + idVaga);
	}

	@PostMapping("/vagas/inscritas/desinscrever/{idVaga}")
	public ModelAndView desinscreverEmVagaInscrita(@PathVariable String idVaga) {
		vagaService.desinscreverVaga(idVaga);
		return new ModelAndView("redirect:/s/vagas/inscritas/" + idVaga);
	}
}
