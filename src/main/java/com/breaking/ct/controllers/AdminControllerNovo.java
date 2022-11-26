package com.breaking.ct.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.breaking.ct.models.Admin;
import com.breaking.ct.services.AdminService;

@RestController
@RequestMapping("/a")
public class AdminControllerNovo {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping
	public ModelAndView homeAdmin() {
		return new ModelAndView("admin/homeAdmin");
	}
	
	// TODO Colocar metodos de all alunos e all empresas
	
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
