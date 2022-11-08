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

import com.breaking.ct.models.Vaga;
import com.breaking.ct.services.VagaService;

@RestController
@RequestMapping("/vagas")
public class VagaController {

	@Autowired
	private VagaService vagaService;
	
	@GetMapping
	public ModelAndView getVagas() {
		ModelAndView mv = new ModelAndView("listaVagas");
		ArrayList<Vaga> vagas = vagaService.getTodosVagas();
		if(!vagas.isEmpty())
			mv.addObject("vagas", vagas);
		return mv;
	}
	
	@GetMapping("/{areaAtuacao}")
	public ModelAndView getVagaEspecifico(@PathVariable("areaAtuacao") String areaAtuacao) {
		Optional<Vaga> vaga = vagaService.getVagaByAreaAtuacao(areaAtuacao);
		ModelAndView mv = new ModelAndView("perfilVaga");
		if(vaga.isEmpty()) 
			return new ModelAndView("vagaNaoEncontrado");
		mv.addObject("vaga", vaga.get());
		return mv;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView formularioCadastroVaga() {
		ModelAndView mv = new ModelAndView("home");
		return mv;
	}
	
	@PostMapping("/cadastrar")
	public ModelAndView novoVaga(Vaga vaga) {
		// vaga.setAreaAtuacao(vaga.getAreaAtuacao().replace(".", "").replace("-", "").trim());
		vagaService.addVaga(vaga);
		return new ModelAndView("redirect:/vagas/" + vaga.getAreaAtuacao());
	}
	
	@GetMapping("/atualizar/{areaAtuacao}")
	public ModelAndView formularioAtualizacaoVaga(@PathVariable("areaAtuacao") String areaAtuacao) {
		Optional<Vaga> vaga = vagaService.getVagaByAreaAtuacao(areaAtuacao);
		if(vaga.isEmpty())
			return new ModelAndView("vagaNaoEncontrado");
		ModelAndView mv = new ModelAndView("atualizacaoVaga");
		mv.addObject("vaga", vaga.get());
		return mv;
	}
	
	@PostMapping("/atualizar")
	public ModelAndView atualizaVaga(Vaga vaga) {
		// vaga.setAreaAtuacao(vaga.getAreaAtuacao().replace("-", "").trim());
		vagaService.updateVaga(vaga);
		return new ModelAndView("redirect:/vagas/" + vaga.getAreaAtuacao());
	}
	
	@PostMapping("/deletar/{areaAtuacao}")
	public ModelAndView deletarVaga(@PathVariable("areaAtuacao") String areaAtuacao) {
		vagaService.deleteVaga(areaAtuacao);
		return new ModelAndView("redirect:/vagas");
	}
	
}
