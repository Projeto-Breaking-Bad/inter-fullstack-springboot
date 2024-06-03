package com.breaking.ct.controllers.aluno;

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
	public ModelAndView perfilAluno(@PathVariable("cpf") String cpf) {
		ModelAndView mv = new ModelAndView("errors/alunoNaoEncontrado");
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		mv.addObject(ALUNO, alunoConsultado.get());
		Aluno alunoLogado = alunoService.getLogged();
		mv.addObject(ALUNO_LOGADO, alunoLogado);

		if (alunoLogado.getCpf().equals(cpf))
			mv.setViewName("student/perfilAlunoEdicao");
		else
			mv.setViewName("student/perfilAlunoConsulta");
		return mv;
	}

	@GetMapping("/alunos/atualizar/{cpf}")
	public ModelAndView formularioAtualizacaoAluno(@PathVariable("cpf") String cpf) {
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
	public ModelAndView atualizarAluno(@PathVariable("cpf") String cpf, AlunoDTO dto) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		String cleanCpf = dto.getCpf().replace(".", "").replace("-", "").trim();
		dto.setCpf(cleanCpf);

		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		if (alunoService.getLogged().getCpf().equals(cpf)) {
			if (dto.getCpf().equals(cpf))
				alunoService.deleteAluno(cpf);
			alunoService.updateAluno(dto);
			mv.setViewName("redirect:/s/alunos/consultar/" + dto.getCpf());
		}
		return mv;
	}

	@PostMapping("/alunos/deletar/{cpf}")
	public ModelAndView deletarAluno(@PathVariable("cpf") String cpf) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		if (alunoService.getLogged().getCpf().equals(cpf)) {
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
		mv.addObject("vagas", vagaService.getTodasVagas());
		mv.addObject(ALUNO_LOGADO, alunoService.getLogged());
		return mv;
	}

	@GetMapping("/vagas/consultar/{id}")
	public ModelAndView verVagasEspecifica(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(id);
		if (vagaConsultada.isEmpty())
			return mv;

		Aluno alunoLogado = alunoService.getLogged();
		mv.addObject(ALUNO_LOGADO, alunoLogado);
		mv.addObject("vaga", vagaConsultada.get());
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
			mv.addObject("vagas", vagasInscritas);
		}
		return mv;
	}

	@GetMapping("/vagas/inscritas/{id}")
	public ModelAndView verVagaInscritaEspecifica(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Aluno alunoLogado = alunoService.getLogged();
		mv.addObject(ALUNO_LOGADO, alunoLogado);

		Optional<Vaga> vagaConsultada = vagaService.getVagaById(id);
		if (vagaConsultada.isEmpty())
			return mv;

		mv.setViewName("student/perfilVagaInscrita");
		mv.addObject("vaga", vagaConsultada.get());

		List<String> listaIdsVagasInscritas = alunoLogado.getListaIdVagasAplicadas();
		mv.addObject("estaInscrito", listaIdsVagasInscritas.contains(vagaConsultada.get().getId()));

		return mv;
	}

	@PostMapping("/vagas/consulta/inscrever/{id}")
	public ModelAndView inscreverEmVagaConsulta(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("redirect:/s/vagas/consultar/" + id);
		vagaService.inscreverVaga(id);
		return mv;
	}

	@PostMapping("/vagas/consulta/desinscrever/{id}")
	public ModelAndView desinscreverEmVagaConsulta(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("redirect:/s/vagas/consultar/" + id);
		vagaService.desinscreverVaga(id);
		return mv;
	}

	@PostMapping("/vagas/inscritas/inscrever/{id}")
	public ModelAndView inscreverEmVagaInscrita(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("redirect:/s/vagas/inscritas/" + id);
		vagaService.inscreverVaga(id);
		return mv;
	}

	@PostMapping("/vagas/inscritas/desinscrever/{id}")
	public ModelAndView desinscreverEmVagaInscrita(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("redirect:/s/vagas/inscritas/" + id);
		vagaService.desinscreverVaga(id);
		return mv;
	}
}
