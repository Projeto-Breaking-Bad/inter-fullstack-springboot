package com.breaking.ct.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.breaking.ct.models.Vaga;
import com.breaking.ct.services.VagaService;

@RestController
public class HomeController {

	@Autowired
	private VagaService vagaService;

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("user/home");
		
		ArrayList<Vaga> vagas = vagaService.getTodasVagas(); 
		mv.addObject("vagas", vagas); 

		try { mv.setViewName(tryRedirection()); }
		catch (Exception e) {}
		
		return mv;
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("user/login");
		
		try { mv.setViewName(tryRedirection()); }
		catch (Exception e) {}
		
		return mv;
	}

	@GetMapping("/vagas/consultar/{id}")
	public ModelAndView verVagasEspecifica(@PathVariable String id) {
		
		ModelAndView mv = new ModelAndView("vagaNaoEncontrada");
		
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(id);
		
		if(vagaConsultada.isEmpty())
			return mv;
		
		mv.addObject("vaga", vagaConsultada.get());
		mv.setViewName("user/perfilVaga");
		return mv;
		
	}

	@GetMapping("/acessibilidade")
	public ModelAndView acessibilidade() {
		return new ModelAndView("user/acessibilidade");
	}

	@GetMapping("/redirect")
	public ModelAndView middleware() {
		ModelAndView mv = new ModelAndView("redirect:/");
		
		try { mv.setViewName(tryRedirection()); }
		catch (Exception e) {}
		
		return mv;
	}
	
	private String tryRedirection() throws Exception {
		/**
		 *  Este metodo eh feito para verificar se existe algum
		 * usuario logado. Caso haja, ele retornara o caminho
		 * do usuario equivalente, senao, uma excecao eh jogada
		 * para ser tratada externamente
		 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) authentication.getPrincipal();
		
		ArrayList<String> roles = new ArrayList<>();
		for (GrantedAuthority ga : user.getAuthorities()) {
			roles.add(ga.getAuthority());
		}
		
		if (roles.contains("ADMIN"))
			return "redirect:/a";
		else if(roles.contains("EMPRESA"))
			return "redirect:/c";
		else
			return "redirect:/s";
	}
	
}
