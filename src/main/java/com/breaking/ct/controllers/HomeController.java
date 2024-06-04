package com.breaking.ct.controllers;

import com.breaking.ct.models.Vaga;
import com.breaking.ct.services.VagaService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class HomeController {
	private VagaService vagaService;

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		List<Vaga> vagas = vagaService.getTodasVagas();
		mv.addObject("vagas", vagas);
		mv.setViewName(checkLoggedUser("user/home"));
		return mv;
	}

	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName(checkLoggedUser("user/login"));
		return mv;
	}

	@GetMapping("/vagas/consultar/{idVaga}")
	public ModelAndView verVagasEspecifica(@PathVariable String idVaga) {
		ModelAndView mv = new ModelAndView("errors/vagaNaoEncontrada");
		Optional<Vaga> vagaConsultada = vagaService.getVagaById(idVaga);
		if (vagaConsultada.isEmpty())
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
		ModelAndView mv = new ModelAndView();
		mv.setViewName(checkLoggedUser("redirect:/"));
		return mv;
	}

	private String checkLoggedUser(String originalPath) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ArrayList<String> roles = new ArrayList<>();
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails user = (UserDetails) authentication.getPrincipal();
			for (GrantedAuthority ga : user.getAuthorities())
				roles.add(ga.getAuthority());
		}
		if (roles.contains("ADMIN"))
			return "redirect:/a";
		else if (roles.contains("EMPRESA"))
			return "redirect:/c";
		else if (roles.contains("ALUNO"))
			return "redirect:/s";
		else
			return originalPath;
	}
}
