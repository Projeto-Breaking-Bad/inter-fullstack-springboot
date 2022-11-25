package com.breaking.ct.controllers;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeControllerNovo {

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("user/home");
		
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

	@GetMapping("/acessibilidade")
	public ModelAndView acessibilidade() {
		return new ModelAndView("acessibilidade");
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
