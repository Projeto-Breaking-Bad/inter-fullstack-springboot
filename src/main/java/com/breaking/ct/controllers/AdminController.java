package com.breaking.ct.controllers;

import com.breaking.ct.dto.AdminDTO;
import com.breaking.ct.dto.AlunoDTO;
import com.breaking.ct.dto.EmpresaDTO;
import com.breaking.ct.dto.VagaDTO;
import com.breaking.ct.models.Admin;
import com.breaking.ct.models.Aluno;
import com.breaking.ct.models.Empresa;
import com.breaking.ct.models.Vaga;
import com.breaking.ct.services.AdminService;
import com.breaking.ct.services.AlunoService;
import com.breaking.ct.services.EmpresaService;
import com.breaking.ct.services.VagaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/a")
@AllArgsConstructor
public class AdminController {
	private AlunoService alunoService;
	private EmpresaService empresaService;
	private AdminService adminService;
	private VagaService vagaService;
	private static final String VAGA = "vaga";
	private static final String VAGAS = "vagas";
	private static final String ADMIN = "admin";
	private static final String ADMINS = "admins";
	private static final String ADMIN_LOGADO = "adminLogado";
	private static final String REDIRECT_LINK = "redirect:/redirect";

	@GetMapping
	public ModelAndView homeAdmin() {
		ModelAndView mv = new ModelAndView("admin/homeAdmin");
		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		return mv;
	}

	@GetMapping("/acessibilidade")
	public ModelAndView acessibilidade() {
		ModelAndView mv = new ModelAndView("admin/acessibilidade");
		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		return mv;
	}

	@GetMapping("/alunos")
	public ModelAndView getTodosAlunos() {
		ModelAndView mv = new ModelAndView("admin/listaAlunos");
		mv.addObject("alunos", alunoService.getTodosAlunos());
		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		return mv;
	}

	@GetMapping("/alunos/consultar/{cpf}")
	public ModelAndView perfilAluno(@PathVariable String cpf) {
		ModelAndView mv = new ModelAndView("errors/alunoNaoEncontrado");
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		mv.addObject("aluno", alunoConsultado.get());
		mv.setViewName("admin/perfilAlunoEdicao");
		return mv;
	}

	@GetMapping("/alunos/atualizar/{cpf}")
	public ModelAndView formularioAtualizacaoAluno(@PathVariable String cpf) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		mv.addObject("aluno", alunoConsultado.get());
		mv.setViewName("admin/perfilAlunoAtualizacao");
		return mv;
	}

	@PostMapping("/alunos/atualizar/{cpf}")
	public ModelAndView atualizarAluno(@PathVariable String cpf, AlunoDTO dto) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isEmpty())
			return mv;

		if (dto.getCpf().equals(cpf)) {
			alunoService.updateAluno(dto);
		} else {
			alunoService.deleteAluno(cpf);
			alunoService.addAluno(dto);
		}
		mv.setViewName("redirect:/a/alunos/consultar/" + dto.getCpf());
		return mv;
	}

	@PostMapping("/alunos/deletar/{cpf}")
	public ModelAndView deletarAluno(@PathVariable String cpf) {
		ModelAndView mv = new ModelAndView("redirect:/a/alunos");
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		if (alunoConsultado.isPresent())
			alunoService.deleteAluno(cpf);
		return mv;
	}

	@GetMapping("/empresas")
	public ModelAndView getTodasEmpresas() {
		ModelAndView mv = new ModelAndView("admin/listaEmpresas");
		mv.addObject("empresas", empresaService.getTodosEmpresas());
		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		return mv;
	}

	@GetMapping("/empresas/consultar/{cnpj}")
	public ModelAndView perfilEmpresa(@PathVariable String cnpj) {
		ModelAndView mv = new ModelAndView("errors/empresaNaoEncontrada");
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		mv.addObject("empresa", empresaConsultada.get());
		mv.setViewName("admin/perfilEmpresaEdicao");
		return mv;
	}

	@GetMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView formularioAtualizacaoEmpresa(@PathVariable String cnpj) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		mv.addObject("empresa", empresaConsultada.get());
		mv.setViewName("admin/perfilEmpresaAtualizacao");
		return mv;
	}

	@PostMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView atualizarEmpresa(@PathVariable String cnpj, EmpresaDTO dto) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isEmpty())
			return mv;

		if (dto.getCnpj().equals(cnpj)) {
			empresaService.updateEmpresa(dto);
		} else {
			empresaService.deleteEmpresa(cnpj);
			empresaService.addEmpresa(dto);
		}
		mv.setViewName("redirect:/a/empresas/consultar/" + dto.getCnpj());
		return mv;
	}

	@PostMapping("/empresas/deletar/{cnpj}")
	public ModelAndView deletarEmpresa(@PathVariable String cnpj) {
		ModelAndView mv = new ModelAndView("redirect:/a/empresas");
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		if (empresaConsultada.isPresent())
			empresaService.deleteEmpresa(cnpj);
		return mv;
	}

	@GetMapping("/admins")
	public ModelAndView getTodosAdmin() {
		ModelAndView mv = new ModelAndView("admin/listaAdmins");
		mv.addObject(ADMINS, adminService.getTodosAdmins());
		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		return mv;
	}

	@GetMapping("/admins/cadastro")
	public ModelAndView formularioCadastroAdmin() {
		ModelAndView mv = new ModelAndView("admin/cadastroAdmin");
		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		return mv;
	}

	@PostMapping("/admins/cadastro")
	public ModelAndView novoAdmin(AdminDTO dto) {
		adminService.addAdmin(dto);
		return new ModelAndView("redirect:/a/admins/consultar/" + dto.getLogin());
	}

	@GetMapping("/admins/consultar/{login}")
	public ModelAndView perfilAdmin(@PathVariable String login) {
		ModelAndView mv = new ModelAndView("errors/adminNaoEncontrado");
		Optional<Admin> adminConsultado = adminService.getAdminByLogin(login);
		if (adminConsultado.isEmpty())
			return mv;

		Admin adminLogado = adminService.getLogged();
		mv.addObject(ADMIN, adminConsultado.get());
		mv.addObject(ADMIN_LOGADO, adminLogado);

		if (adminLogado.getLogin().equals(login))
			mv.setViewName("admin/perfilAdminEdicao");
		else
			mv.setViewName("admin/perfilAdminConsulta");
		return mv;
	}

	@GetMapping("/admins/atualizar/{login}")
	public ModelAndView formularioAtualizacaoAdmin(@PathVariable String login) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Admin> adminConsultado = adminService.getAdminByLogin(login);
		if (adminConsultado.isEmpty())
			return mv;

		Admin adminLogado = adminService.getLogged();
		mv.addObject(ADMIN_LOGADO, adminLogado);

		if (adminLogado.getLogin().equals(login)) {
			mv.addObject(ADMIN, adminConsultado.get());
			mv.setViewName("admin/perfilAdminAtualizacao");
		}
		return mv;
	}

	@PostMapping("/admins/atualizar/{login}")
	public ModelAndView atualizarAdmin(@PathVariable String login, AdminDTO dto) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		Optional<Admin> adminConsultado = adminService.getAdminByLogin(login);
		if (adminConsultado.isEmpty())
			return mv;

		if (adminService.getLogged().getLogin().equals(login)) {
			if (dto.getLogin().equals(login)) {
				adminService.updateAdmin(dto);
			} else {
				adminService.deleteAdmin(login);
				adminService.addAdmin(dto);
			}
			mv.setViewName("redirect:/a/admins/consultar/" + dto.getLogin());
		}
		return mv;
	}

	@PostMapping("/admins/deletar/{login}")
	public ModelAndView deletarAdmin(@PathVariable String login) {
		ModelAndView mv = new ModelAndView(REDIRECT_LINK);
		List<Admin> teste = adminService.getTodosAdmins();
		if (teste.size() <= 1)
			return mv;

		Optional<Admin> adminConsultado = adminService.getAdminByLogin(login);
		if (adminConsultado.isEmpty())
			return mv;

		if (adminConsultado.get().getLogin().equals(login)) {
			adminService.deleteAdmin(login);
			mv.setViewName("redirect:/logout");
		}
		return mv;
	}

	@GetMapping("/vagas")
	public ModelAndView verVagas() {
		ModelAndView mv = new ModelAndView("admin/listaVagas");
		mv.addObject(VAGAS, vagaService.getTodasVagas());
		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		return mv;
	}

	@GetMapping("/vagas/consultar/{idVaga}")
	public ModelAndView verVagasEspecifica(@PathVariable String idVaga) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(idVaga);
		if (vagaConsultada.isEmpty())
			return mv;

		mv.addObject(VAGA, vagaConsultada.get());
		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		mv.setViewName("admin/perfilVaga");
		return mv;

	}

	@GetMapping("/vagas/atualizar/{idVaga}")
	public ModelAndView formularioAtualizacaoVaga(@PathVariable String idVaga) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Optional<Vaga> vaga = vagaService.getVagaById(idVaga);
		if (vaga.isEmpty())
			return mv;

		mv.addObject(VAGA, vaga.get());
		mv.addObject(ADMIN_LOGADO, adminService.getLogged());
		mv.setViewName("admin/perfilVagaAtualizacao");
		return mv;
	}

	@PostMapping("/vagas/atualizar/{idVaga}")
	public ModelAndView atualizaVaga(@PathVariable String idVaga, VagaDTO dto) {
		vagaService.updateVaga(idVaga, dto);
		return new ModelAndView("redirect:/a/vagas/consultar/" + dto.getId());
	}

	@PostMapping("/vagas/deletar/{idVaga}")
	public ModelAndView deletarVaga(@PathVariable String idVaga) {
		vagaService.deleteVaga(idVaga);
		return new ModelAndView("redirect:/a/vagas");
	}
}
