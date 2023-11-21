package br.com.roberth.avaliacaoTecnica.services;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaInternalErrorException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaNotFoundException;
import br.com.roberth.avaliacaoTecnica.model.dto.PautaDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.PautaUpdateDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.ResultadoVotacaoDTO;
import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PautaService {
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VotoService votoService;
	
	
	public ResultadoVotacaoDTO resultadoVotacaoPauta(Long id) {
		ResultadoVotacaoDTO resultado;
		Pauta pauta = this.obterPauta(id);
		if (pauta.getSessaoVotacao() != null) {
			resultado = new ResultadoVotacaoDTO(
					pauta.getAssunto(),
					votoService.totalVotos(pauta.getSessaoVotacao().getId()),
					votoService.totalVotosSim(pauta.getSessaoVotacao().getId()),
					votoService.totalVotosNao(pauta.getSessaoVotacao().getId())
			);
		} else {
			resultado = new ResultadoVotacaoDTO(pauta.getAssunto(), 0L, 0L, 0L);
		}
		return resultado;
	}
	
	public ResultadoVotacaoDTO resultadoVotacaoPauta(Pauta pauta) {
		ResultadoVotacaoDTO resultado;
		if (pauta.getSessaoVotacao() != null) {
			resultado = new ResultadoVotacaoDTO(
					pauta.getAssunto(),
					votoService.totalVotos(pauta.getSessaoVotacao().getId()),
					votoService.totalVotosSim(pauta.getSessaoVotacao().getId()),
					votoService.totalVotosNao(pauta.getSessaoVotacao().getId())
			);
		} else {
			resultado = new ResultadoVotacaoDTO(pauta.getAssunto(), 0L, 0L, 0L);
		}
		return resultado;
	}
	
	public Pauta obterPauta(Long id) {
		Pauta pauta = pautaRepository.findById(id).orElseThrow(() -> new IllegalStateException(
				new PautaNotFoundException("A pauta " + id + " não foi encontrada")
				));
		pauta.setResultado(this.resultadoVotacaoPauta(pauta));
		return pauta;
	}
	
	public Page<PautaDTO> listarPautas(Pageable paginacao) {
		return pautaRepository.findAll(paginacao).map(PautaDTO::new);
	}
	
	public Pauta adicionarPauta(Pauta pauta) {
		return pautaRepository.save(pauta);
	}
	
	public Pauta atualizarPauta(PautaUpdateDTO pautaDTO) {
		Pauta pauta = this.obterPauta(pautaDTO.id());
		pauta.atualizarDados(pautaDTO);
		return pautaRepository.save(pauta);
	}

	public Pauta atualizarPauta(Pauta pauta) {
		return pautaRepository.save(pauta);
	}

}
