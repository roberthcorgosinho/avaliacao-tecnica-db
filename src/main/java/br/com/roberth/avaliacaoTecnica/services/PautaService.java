package br.com.roberth.avaliacaoTecnica.services;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaNotFoundException;
import br.com.roberth.avaliacaoTecnica.model.dto.PautaDadosDTO;
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

	/**
	 * Recupera uma pauta do banco de dados atraves do seu ID
	 * @param idPauta
	 * @return A pauta encontrada
	 */
	public PautaDadosDTO obterPauta(Long idPauta) {
		return new PautaDadosDTO(pautaRepository.findById(idPauta).orElseThrow(() -> new IllegalStateException(
				new PautaNotFoundException("A pauta " + idPauta + " não foi encontrada")
				)));
	}

	/**
	 * Lista, de maneira paginada, todas as pautas cadastradas no banco de dados
	 * @param paginacao
	 * @return Lista paginada contendo as pautas cadastradas
	 * @throws PautaNotFoundException
	 */
	public Page<PautaDadosDTO> listarPautas(Pageable paginacao) throws PautaNotFoundException {
		Page<PautaDadosDTO> listagemPautas = pautaRepository.findAll(paginacao).map(PautaDadosDTO::new);
		if (!listagemPautas.hasContent()) {
			throw new PautaNotFoundException("Não existem pautas cadastradas");
		}
		return listagemPautas;
	}

	/**
	 * Insere uma nova pauta no banco de dados
	 * @param pauta
	 * @return A pauta criada
	 * @throws PautaBadRequestException
	 */
	public PautaDadosDTO adicionarPauta(PautaDadosDTO pauta) throws PautaBadRequestException {
		try {
			Pauta novaPauta = new Pauta();
			novaPauta.setAssunto(pauta.assunto());
			return new PautaDadosDTO(pautaRepository.save(novaPauta));
		} catch (Exception ex) {
			throw new PautaBadRequestException("Ocorreu o seguinte erro ao tentar adicionar a pauta: " + ex.getMessage());
		}
	}

}
