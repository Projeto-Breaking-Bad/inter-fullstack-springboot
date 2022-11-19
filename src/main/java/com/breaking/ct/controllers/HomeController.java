package com.breaking.ct.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
	
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@GetMapping("/acessibilidade")
	public ModelAndView acessibilidade() {
		return new ModelAndView("acessibilidade");
	}
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("home");
	}
}
