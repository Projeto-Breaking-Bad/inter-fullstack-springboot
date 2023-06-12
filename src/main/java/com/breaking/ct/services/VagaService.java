package com.breaking.ct.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.models.Empresa;
import com.breaking.ct.models.Vaga;
import com.breaking.ct.repositories.VagaRepository;

@Service
public class VagaService {

	@Autowired
	private AlunoService alunoService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private VagaRepository vagaRepository;

	public ArrayList<Vaga> getTodasVagas() {
		ArrayList<Vaga> vagas = new ArrayList<>();
		vagaRepository.findAll().forEach(vagas::add);
		return vagas;
	}

	public List<Vaga> getVagasByListaIds(List<String> listaIds) {
		ArrayList<Vaga> vagas = new ArrayList<>();
		vagaRepository.findAllById(listaIds).forEach(vagas::add);
		return vagas;
	}

	public List<Vaga> getVagasByCnpj(String cnpj) {
		return vagaRepository.findByCnpj(cnpj);
	}

	public Optional<Vaga> getVagaById(String id) {
		return vagaRepository.findById(id);
	}

	public void addVaga(Vaga vaga) {
		Vaga vagaCriada = vagaRepository.save(vaga);
		
		Empresa empresaLogada = empresaService.getLogged();
		
		List<String> listaIdsVagasNova = empresaLogada.getListaIdVagasCriadas();
		listaIdsVagasNova.add(vagaCriada.getId());
		empresaLogada.setListaIdVagasCriadas(listaIdsVagasNova);
		empresaService.updateEmpresa(empresaLogada);
	}

	public void updateVaga(Vaga vaga) {
		vagaRepository.save(vaga);
	}

	public void deleteVaga(String id) {
		vagaRepository.deleteById(id);
	}

	public void inscreverVaga(String id) {
		
		Aluno alunoLogado = alunoService.getLogged();

		Optional<Vaga> consultaVaga = vagaRepository.findById(id);
		if (consultaVaga.isEmpty())
			return;

		Vaga vagaParaAplicar = consultaVaga.get();
		if (alunoLogado.getListaIdVagasAplicadas().contains(vagaParaAplicar.getId()))
			return;

		List<String> listaIdVagasNova = alunoLogado.getListaIdVagasAplicadas();
		listaIdVagasNova.add(vagaParaAplicar.getId());
		alunoLogado.setListaIdVagasAplicadas(listaIdVagasNova);
		alunoService.updateAluno(alunoLogado);

		List<String> listaIdAlunosNova = vagaParaAplicar.getListaIdsAlunosAplicantes();
		listaIdAlunosNova.add(alunoLogado.getCpf());
		alunoLogado.setListaIdVagasAplicadas(listaIdAlunosNova);

		vagaRepository.save(vagaParaAplicar);
	}

	public void desinscreverVaga(String id) {

		Aluno alunoLogado = alunoService.getLogged();

		Optional<Vaga> consultaVaga = vagaRepository.findById(id);
		if (consultaVaga.isEmpty())
			return;

		Vaga vagaParaAplicar = consultaVaga.get();
		if (!alunoLogado.getListaIdVagasAplicadas().contains(vagaParaAplicar.getId()))
			return;

		List<String> listaIdVagasNova = alunoLogado.getListaIdVagasAplicadas();
		listaIdVagasNova.remove(vagaParaAplicar.getId());
		alunoLogado.setListaIdVagasAplicadas(listaIdVagasNova);
		alunoService.updateAluno(alunoLogado);

		List<String> listaIdAlunosNova = vagaParaAplicar.getListaIdsAlunosAplicantes();
		listaIdAlunosNova.remove(alunoLogado.getCpf());
		alunoLogado.setListaIdVagasAplicadas(listaIdAlunosNova);

		vagaRepository.save(vagaParaAplicar);
	}

}
