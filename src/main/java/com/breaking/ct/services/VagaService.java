package com.breaking.ct.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Vaga;
import com.breaking.ct.repositories.VagaRepository;

@Service
public class VagaService {
    @Autowired
	private VagaRepository vagaRepository;
	
	public ArrayList<Vaga> getTodosVagas() {
		ArrayList<Vaga> vagas = new ArrayList<>();
		vagaRepository.findAll().forEach(vagas::add);
		return vagas;
	}

	public List<Vaga> getVagaByCnpj(String cnpj){
		return vagaRepository.findByCnpj(cnpj);
	}

	public Optional<Vaga> getVagaById(String id){
		return vagaRepository.getVagaById(id);
	}

	public void addVaga(Vaga vaga) {
		vagaRepository.save(vaga);
	}

	public void updateVaga(Vaga vaga) {
		vagaRepository.save(vaga);
	}

	public void deleteVaga(String id) {
		vagaRepository.deleteById(id);
	}
	
}
