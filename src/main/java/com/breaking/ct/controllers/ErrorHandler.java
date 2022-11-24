package com.breaking.ct.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrorHandler implements ErrorController {
	
	@GetMapping("/error")
	public ModelAndView error(HttpServletRequest request) {
			return new ModelAndView("errors/erroHttp");
	}
	
}
