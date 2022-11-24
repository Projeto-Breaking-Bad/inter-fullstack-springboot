package com.breaking.ct.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrudAdminsController {
	
	@GetMapping("/a")
	public String teste1() {
		return "admin";
	}
	
	@GetMapping("/c")
	public String teste2() {
		return "company";
	}
	
	@GetMapping("/s")
	public String teste3() {
		return "student";
	}
	
}
