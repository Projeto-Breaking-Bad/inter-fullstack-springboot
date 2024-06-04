package com.breaking.ct.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrorHandler implements ErrorController {
	@GetMapping("/error")
	public ModelAndView error() {
		return new ModelAndView("errors/erroHttp");
	}
}
