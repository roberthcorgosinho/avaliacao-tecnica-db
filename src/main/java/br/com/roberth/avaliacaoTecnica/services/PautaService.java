package br.com.roberth.avaliacaoTecnica.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaInternalErrorException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaNotFoundException;
import br.com.roberth.avaliacaoTecnica.model.dto.ResultadoVotacaoDTO;
import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.repository.PautaRepository;

@Service
public class PautaService {
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VotoService votoService;
	
	
	public ResultadoVotacaoDTO resultadoVotacaoPauta(Long id) throws Exception {
		ResultadoVotacaoDTO resultado = new ResultadoVotacaoDTO();
		resultado.setTotalNao(0L);
		resultado.setTotalSim(0L);
		resultado.setTotalNao(0L);
		Pauta pauta = this.obterPauta(id);
		if (pauta.getSessaoVotacao() != null && pauta.getSessaoVotacao().getId() != null  && pauta.getSessaoVotacao().getVotos() != null) {
			resultado.setTotalVotos(votoService.totalVotos(pauta.getSessaoVotacao().getId()));
			resultado.setTotalSim(votoService.totalVotosSim(pauta.getSessaoVotacao().getId()));
			resultado.setTotalNao(votoService.totalVotosNao(pauta.getSessaoVotacao().getId()));
		}
		return resultado;
	}
	
	public ResultadoVotacaoDTO resultadoVotacaoPauta(Pauta pauta) throws Exception {
		ResultadoVotacaoDTO resultado = new ResultadoVotacaoDTO();
		resultado.setTotalNao(0L);
		resultado.setTotalSim(0L);
		resultado.setTotalNao(0L);
		if (pauta.getSessaoVotacao() != null && pauta.getSessaoVotacao().getId() != null  && pauta.getSessaoVotacao().getVotos() != null) {
			resultado.setTotalVotos(votoService.totalVotos(pauta.getSessaoVotacao().getId()));
			resultado.setTotalSim(votoService.totalVotosSim(pauta.getSessaoVotacao().getId()));
			resultado.setTotalNao(votoService.totalVotosNao(pauta.getSessaoVotacao().getId()));
		}
		return resultado;
	}
	
	public Pauta obterPauta(Long id) throws Exception {
		try {
			Pauta pauta = pautaRepository.findById(id).orElseThrow(() -> new IllegalStateException(
					new PautaNotFoundException("A pauta " + id + " não foi encontrada")
					));
			pauta.setResultado(this.resultadoVotacaoPauta(pauta));
			return pauta;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public List<Pauta> listarPautas() throws Exception {
		try {
			List<Pauta> pautas = new ArrayList<>();
			for(Pauta pauta : pautaRepository.findAll()) {
				pauta.setResultado(this.resultadoVotacaoPauta(pauta));
				pautas.add(pauta);
			}
			return pautas;
		} catch (Exception e) {
			throw new PautaInternalErrorException("Erro ao tentar listar as pautas", e);
		}
	}
	
	public Pauta adicionarPauta(Pauta pauta) throws Exception {
		try {
			return pautaRepository.save(pauta);
		} catch (Exception e) {
			throw new PautaBadRequestException("Não foi possível criar a pauta", e);
		}
	}
	
	public Pauta atualizarPauta(Pauta pauta) throws Exception {
		try {
			return pautaRepository.save(pauta);
		} catch (Exception e) {
			throw new PautaBadRequestException("Não foi possível atualizar a pauta " + pauta.getId(), e);
		}
	}

}
