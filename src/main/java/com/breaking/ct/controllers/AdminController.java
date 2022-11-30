package com.breaking.ct.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.breaking.ct.models.Admin;
import com.breaking.ct.models.Aluno;
import com.breaking.ct.models.Empresa;
import com.breaking.ct.models.Vaga;
import com.breaking.ct.services.AdminService;
import com.breaking.ct.services.AlunoService;
import com.breaking.ct.services.EmpresaService;
import com.breaking.ct.services.VagaService;

@RestController
@RequestMapping("/a")
public class AdminController {
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private VagaService vagaService;
	
	@GetMapping
	public ModelAndView homeAdmin() {
		ModelAndView mv = new ModelAndView("admin/homeAdmin");
		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		return mv;
	}
	
	@GetMapping("/acessibilidade")
	public ModelAndView acessibilidade() {
		ModelAndView mv = new ModelAndView("admin/acessibilidade");
		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		return mv;
	}
	
	/**
	 * CRUD ALUNO
	 */
	@GetMapping("/alunos")
	public ModelAndView getTodosAlunos() {
		ModelAndView mv = new ModelAndView("admin/listaAlunos");
		List<Aluno> alunos = alunoService.getTodosAlunos();
		mv.addObject("alunos", alunos);
		
		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		return mv;
	}
	
	@GetMapping("/alunos/consultar/{cpf}")
	public ModelAndView perfilAluno(@PathVariable("cpf") String cpf) {

		ModelAndView mv = new ModelAndView("errors/alunoNaoEncontrado");
		
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		
		if(alunoConsultado.isEmpty())
			return mv;

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		Aluno aluno = alunoConsultado.get();
		aluno.setSenha("");
		mv.addObject("aluno", aluno);
		
		mv.setViewName("admin/perfilAlunoEdicao");
		
		return mv;
	}
	
	@GetMapping("/alunos/atualizar/{cpf}")
	public ModelAndView formularioAtualizacaoAluno(@PathVariable("cpf") String cpf) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");
		
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		
		if(alunoConsultado.isEmpty())
			return mv;

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		Aluno aluno = alunoConsultado.get();
		aluno.setSenha("");
		mv.addObject("aluno", aluno);
		
		mv.setViewName("admin/perfilAlunoAtualizacao");
		
		return mv;
	}
	
	@PostMapping("/alunos/atualizar/{cpf}")
	public ModelAndView atualizarAluno(@PathVariable("cpf") String cpf, Aluno aluno) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");

		aluno.setCpf(aluno.getCpf().replace(".", "").replace("-", "").trim());
		
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);

		if(alunoConsultado.isEmpty())
			return mv;
		
		if(aluno.getCpf().equals(cpf)) {
			alunoService.updateAluno(aluno);
		} else {
			alunoService.deleteAluno(cpf);
			alunoService.addAluno(aluno);
		}
		mv.setViewName("redirect:/a/alunos/consultar/"+aluno.getCpf());
		
		return mv;
	}
	
	@PostMapping("/alunos/deletar/{cpf}")
	public ModelAndView deletarAluno(@PathVariable("cpf") String cpf) {
		
		ModelAndView mv = new ModelAndView("redirect:/a/alunos");
		
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		
		if(alunoConsultado.isEmpty())
			return mv;
		
		alunoService.deleteAluno(cpf);
		
		return mv;
	}
	
	/**
	 * CRUD EMPRESA
	 */
	@GetMapping("/empresas")
	public ModelAndView getTodasEmpresas() {
		ModelAndView mv = new ModelAndView("admin/listaEmpresas");
		List<Empresa> empresas = empresaService.getTodosEmpresas();
		mv.addObject("empresas", empresas);

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		return mv;
	}
	
	@GetMapping("/empresas/consultar/{cnpj}")
	public ModelAndView perfilEmpresa(@PathVariable("cnpj") String cnpj) {

		ModelAndView mv = new ModelAndView("errors/empresaNaoEncontrada");
		
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		
		if(empresaConsultada.isEmpty())
			return mv;

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		Empresa empresa = empresaConsultada.get();
		empresa.setSenha("");
		mv.addObject("empresa", empresa);
		mv.setViewName("admin/perfilEmpresaEdicao");
		
		return mv;
	}
	
	@GetMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView formularioAtualizacaoEmpresa(@PathVariable("cnpj") String cnpj) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");
		
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		
		if(empresaConsultada.isEmpty())
			return mv;

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		Empresa empresa = empresaConsultada.get();
		empresa.setSenha("");
		
		mv.addObject("empresa", empresa);
		mv.setViewName("admin/perfilEmpresaAtualizacao");
		
		return mv;
	}
	
	@PostMapping("/empresas/atualizar/{cnpj}")
	public ModelAndView atualizarEmpresa(@PathVariable("cnpj") String cnpj, Empresa empresa) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");

		empresa.setCnpj(empresa.getCnpj().replace(".", "").replace("-", "").replace("/", "").trim());
		
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);

		if(empresaConsultada.isEmpty())
			return mv;
		
		if(empresa.getCnpj().equals(cnpj)) {
			empresaService.updateEmpresa(empresa);
		} else {
			empresaService.deleteEmpresa(cnpj);
			empresaService.addEmpresa(empresa);
		}
		mv.setViewName("redirect:/a/empresas/consultar/"+empresa.getCnpj());
		
		return mv;
	}
	
	@PostMapping("/empresas/deletar/{cnpj}")
	public ModelAndView deletarEmpresa(@PathVariable("cnpj") String cnpj) {
		
		ModelAndView mv = new ModelAndView("redirect:/a/empresas");
		
		Optional<Empresa> empresaConsultada = empresaService.getEmpresaByCnpj(cnpj);
		
		if(empresaConsultada.isEmpty())
			return mv;
		
		empresaService.deleteEmpresa(cnpj);
		
		return mv;
	}
	
	/**
	 * CRUD ADMIN
	 */
	@GetMapping("/admins")
	public ModelAndView getTodosAdmin() {
		ModelAndView mv = new ModelAndView("admin/listaAdmins");
		List<Admin> admins = adminService.getTodosAdmins();
		mv.addObject("admins", admins);

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		return mv;
	}
	
	@GetMapping("/admins/cadastro")
	public ModelAndView formularioCadastroAdmin() {
		ModelAndView mv = new ModelAndView("admin/cadastroAdmin");

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		return mv;
	}

    @PostMapping("/admins/cadastro")
	public ModelAndView novoAdmin(Admin admin) {
		Optional<Admin> teste1 = adminService.getAdminByLogin(admin.getLogin());
		Optional<Admin> teste2 = adminService.getAdminByEmail(admin.getEmail());
		
		if(teste1.isEmpty() && teste2.isEmpty()) 
			adminService.addAdmin(admin);
		
		return new ModelAndView("redirect:/a/admins/consultar/" + admin.getLogin());
	}
    
	@GetMapping("/admins/consultar/{login}")
	public ModelAndView perfilAdmin(@PathVariable("login") String login) {

		ModelAndView mv = new ModelAndView("errors/adminNaoEncontrado");
		
		Optional<Admin> adminConsultado = adminService.getAdminByLogin(login);
		
		if(adminConsultado.isEmpty())
			return mv;

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		Admin admin = adminConsultado.get();
		admin.setSenha("");
		mv.addObject("admin", admin);
		
		if(adminService.getLogged().getLogin().equals(login))
			mv.setViewName("admin/perfilAdminEdicao");
		else
			mv.setViewName("admin/perfilAdminConsulta");
		
		return mv;
	}
	
	@GetMapping("/admins/atualizar/{login}")
	public ModelAndView formularioAtualizacaoAdmin(@PathVariable("login") String login) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");
		
		Optional<Admin> adminConsultado = adminService.getAdminByLogin(login);
		
		if(adminConsultado.isEmpty())
			return mv;

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		Admin admin = adminConsultado.get();
		admin.setSenha("");
		
		if(adminService.getLogged().getLogin().equals(login)) {
			mv.addObject("admin", admin);
			mv.setViewName("admin/perfilAdminAtualizacao");
		}
		
		return mv;
	}
	
	@PostMapping("/admins/atualizar/{login}")
	public ModelAndView atualizarAdmin(@PathVariable("login") String login, Admin admin) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");
		
		Optional<Admin> adminConsultado = adminService.getAdminByLogin(login);

		if(adminConsultado.isEmpty())
			return mv;
		
		if(adminService.getLogged().getLogin().equals(login)) {
			if(admin.getLogin().equals(login)) {
				adminService.updateAdmin(admin);
			} else {
				adminService.deleteAdmin(login);
				adminService.addAdmin(admin);
			}
			mv.setViewName("redirect:/a/admins/consultar/"+admin.getLogin());
		}
		
		return mv;
	}
	
	@PostMapping("/admins/deletar/{login}")
	public ModelAndView deletarAdmin(@PathVariable("login") String login) {
		
		ModelAndView mv = new ModelAndView("redirect:/redirect");
		ArrayList<Admin> teste = adminService.getTodosAdmins();
		if(!(teste.size()>1))
			return mv;
		
		Optional<Admin> adminConsultado = adminService.getAdminByLogin(login);
		
		if(adminConsultado.isEmpty())
			return mv;
		
		if(adminService.getLogged().getLogin().equals(login)) {
			adminService.deleteAdmin(login);
			mv.setViewName("redirect:/logout");
		}
		
		return mv;
	}
	
	/**
	 * VAGAS ADMIN
	 */
	@GetMapping("/vagas")
	public ModelAndView verVagas() {
		ModelAndView mv = new ModelAndView("admin/listaVagas");
		List<Vaga> vagas = vagaService.getTodasVagas();
		mv.addObject("vagas", vagas);

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		return mv;
	}
	
	@GetMapping("/vagas/consultar/{id}")
	public ModelAndView verVagasEspecifica(@PathVariable("id") String id) {
		
		ModelAndView mv = new ModelAndView("vagaNaoEncontrada");
		
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(id);
		
		if(vagaConsultada.isEmpty())
			return mv;

		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		mv.addObject("vaga", vagaConsultada.get());
		mv.setViewName("admin/perfilVaga");
		return mv;
		
	}
	
	@GetMapping("/vagas/atualizar/{id}")
	public ModelAndView formularioAtualizacaoVaga(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Optional<Vaga> vaga = vagaService.getVagaById(id);
		
		if(vaga.isEmpty())
			return mv;
		
		Admin adminLogado = adminService.getLogged();
		adminLogado.setSenha("");
		mv.addObject("adminLogado", adminLogado);
		
		mv.setViewName("admin/perfilVagaAtualizacao");
		mv.addObject("vaga", vaga.get());
		return mv;
	}
	
	@PostMapping("/vagas/atualizar/{id}")
	public ModelAndView atualizaVaga(Vaga vaga) {
		vagaService.updateVaga(vaga);
		return new ModelAndView("redirect:/a/vagas/consultar/" + vaga.getId());
	}
	
	@PostMapping("/vagas/deletar/{id}")
	public ModelAndView deletarVaga(@PathVariable("id") String id) {
		vagaService.deleteVaga(id);
		return new ModelAndView("redirect:/a/vagas");
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
