package com.breaking.ct.services;

import com.breaking.ct.dto.VagaDTO;
import com.breaking.ct.mappers.VagaMapper;
import com.breaking.ct.models.Aluno;
import com.breaking.ct.models.Empresa;
import com.breaking.ct.models.Vaga;
import com.breaking.ct.repositories.VagaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VagaService {
	private AlunoService alunoService;
	private EmpresaService empresaService;
	private VagaRepository vagaRepository;
	private VagaMapper vagaMapper;

	public List<Vaga> getTodasVagas() {
		return vagaRepository.findAll();
	}

	public List<Vaga> getVagasByListaIds(List<String> listaIds) {
		ArrayList<Vaga> vagas = new ArrayList<>();
		vagaRepository.findAllById(listaIds).forEach(vagas::add);
		return vagas;
	}

	public Optional<Vaga> getVagaById(String idVaga) {
		return vagaRepository.findFirstById(idVaga);
	}

	public Vaga addVaga(VagaDTO dto) {
		Vaga vaga = vagaMapper.map(dto);
		Vaga vagaCriada = vagaRepository.save(vaga);
		Empresa empresaLogada = empresaService.getLogged();
		List<String> listaIdsVagasNova = empresaLogada.getListaIdVagasCriadas();
		listaIdsVagasNova.add(vagaCriada.getId());
		empresaLogada.setListaIdVagasCriadas(listaIdsVagasNova);
		empresaService.updateEmpresa(empresaLogada);
		return vagaCriada;
	}

	public void updateVaga(String idVaga, VagaDTO dto) {
		Vaga vaga = vagaMapper.map(dto);
		vaga.setId(idVaga);
		vagaRepository.save(vaga);
	}

	public void deleteVaga(String idVaga) {
		vagaRepository.deleteAllById(List.of(idVaga));
	}

	public void inscreverVaga(String idVaga) {
		Aluno alunoLogado = alunoService.getLogged();
		Optional<Vaga> consultaVaga = vagaRepository.findFirstById(idVaga);
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

	public void desinscreverVaga(String idVaga) {
		Aluno alunoLogado = alunoService.getLogged();
		Optional<Vaga> consultaVaga = vagaRepository.findFirstById(idVaga);
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
