package com.breaking.ct.services;

import java.util.ArrayList;
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

	public Optional<Vaga> getVagaByCnpj(String cnpj){
		return vagaRepository.findById(cnpj);
	}

	public void addVaga(Vaga vaga) {
		vagaRepository.save(vaga);
	}

	public void updateVaga(Vaga vaga) {
		vagaRepository.save(vaga);
	}

	public void deleteVaga(String cnpj) {
		vagaRepository.deleteById(cnpj);
	}
	
}
