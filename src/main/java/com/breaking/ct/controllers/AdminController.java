package com.breaking.ct.controllers;

import java.util.ArrayList;
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
@RequestMapping("/admins")
public class AdminController {
    
    @Autowired
	private AdminService adminService;

    @GetMapping
	public ModelAndView getAdmins() {
		ModelAndView mv = new ModelAndView("listaAdmins");
		ArrayList<Admin> Admins = adminService.getTodosAdmins();
		if(!Admins.isEmpty())
			mv.addObject("admins", Admins);
		return mv;
	}

    @GetMapping("/{login}")
	public ModelAndView getAdminEspecifico(@PathVariable("login") String login) {
		Optional<Admin> admin = adminService.getAdminByLogin(login);
		ModelAndView mv = new ModelAndView("perfilAdmin");
		if(admin.isEmpty())
			return new ModelAndView("adminNaoEncontrado");
		mv.addObject("admin", admin.get());
		return mv;
	}

    @GetMapping("/cadastrar")
	public ModelAndView formularioCadastroAdmin() {
		ModelAndView mv = new ModelAndView("cadastroAdmin");
		return mv;
	}

    @PostMapping("/cadastrar")
	public ModelAndView novoAdmin(Admin admin) {
		adminService.addAdmin(admin);
		return new ModelAndView("redirect:/admins/" + admin.getLogin());
	}

    @GetMapping("/atualizar/{login}")
	public ModelAndView formularioAtualizacaoLogin(@PathVariable("login") String login) {
		Optional<Admin> admin = adminService.getAdminByLogin(login);
		if(admin.isEmpty())
			return new ModelAndView("adminNaoEncontrado");
		ModelAndView mv = new ModelAndView("atualizacaoAdmin");
		mv.addObject("admin", admin.get());
		return mv;
	}

	@PostMapping("/atualizar")
	public ModelAndView atualizaAdmin(Admin admin) {
		adminService.updateAdmin(admin);
		return new ModelAndView("redirect:/admins/" + admin.getLogin());
	}
	
	@PostMapping("/deletar/{login}")
	public ModelAndView deletarAdmin(@PathVariable("login") String login) {
		adminService.deleteAdmin(login);
		return new ModelAndView("redirect:/admins");
	}

}
