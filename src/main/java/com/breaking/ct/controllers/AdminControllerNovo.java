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
import com.breaking.ct.services.AdminService;
import com.breaking.ct.services.AlunoService;
import com.breaking.ct.services.EmpresaService;

@RestController
@RequestMapping("/a")
public class AdminControllerNovo {
	
	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping
	public ModelAndView homeAdmin() {
		return new ModelAndView("admin/homeAdmin");
	}
	
	/**
	 * CRUD ALUNO
	 */
	@GetMapping("/alunos")
	public ModelAndView getTodosAlunos() {
		ModelAndView mv = new ModelAndView("admin/listaAlunos");
		List<Aluno> alunos = alunoService.getTodosAlunos();
		mv.addObject("alunos", alunos);
		return mv;
	}
	
	@GetMapping("/alunos/consultar/{cpf}")
	public ModelAndView perfilAluno(@PathVariable("cpf") String cpf) {

		ModelAndView mv = new ModelAndView("errors/alunoNaoEncontrado");
		
		Optional<Aluno> alunoConsultado = alunoService.getAlunoByCpf(cpf);
		
		if(alunoConsultado.isEmpty())
			return mv;
		
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
		mv.setViewName("admin/perfilEmpresaEdicao");
		
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
	@GetMapping("/admins/consultar/{login}")
	public ModelAndView perfilAdmin(@PathVariable("login") String login) {

		ModelAndView mv = new ModelAndView("errors/adminNaoEncontrado");
		
		Optional<Admin> adminConsultado = adminService.getAdminByLogin(login);
		
		if(adminConsultado.isEmpty())
			return mv;
		
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
	
	// Vagas...
	
}
