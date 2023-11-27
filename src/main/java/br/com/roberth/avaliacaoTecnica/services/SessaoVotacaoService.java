package br.com.roberth.avaliacaoTecnica.services;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaConflictException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaNotFoundException;
import br.com.roberth.avaliacaoTecnica.model.dto.SessaoVotacaoDadosDTO;
import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import br.com.roberth.avaliacaoTecnica.repository.PautaRepository;
import br.com.roberth.avaliacaoTecnica.repository.SessaoVotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class SessaoVotacaoService {

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Autowired
	private PautaRepository pautaRepository;

	/**
	 * Insere uma nova sessao de votacao e vincula a uma pauta especifica
	 * @param sessao
	 * @return a sessao de votacao criada
	 * @throws Exception
	 */
	public SessaoVotacaoDadosDTO adicionarSessao(SessaoVotacaoDadosDTO sessao) throws Exception {
		try {
			SessaoVotacao sessaoCriada = null;
			Optional<Pauta> pauta = pautaRepository.findById(sessao.idPauta());
			if (!pauta.isPresent()) {
				throw new PautaNotFoundException("A pauta informada não existe");
			}
			if (pauta.get().getSessaoVotacao() != null) {
				sessaoCriada = pauta.get().getSessaoVotacao();
				if (isSessaoVotacaoAberta(sessaoCriada.getId())) {
					throw new PautaConflictException("Já existe uma sessão de votação para a Pauta " + sessaoCriada.getPauta().getId());
				}
			}
			sessaoCriada = new SessaoVotacao();
			sessaoCriada.setDuracaoEmMinutos(sessao.duracaoEmMinutos());
			sessaoCriada.setPauta(pauta.get());
			sessaoCriada = sessaoVotacaoRepository.save(sessaoCriada);
			pauta.get().setSessaoVotacao(sessaoCriada);
			pautaRepository.save(pauta.get());
			return new SessaoVotacaoDadosDTO(sessaoCriada);
		} catch (Exception ex) {
			throw new PautaBadRequestException("Ocorreu um erro ao tentar abrir a sessão de votação: " + ex.getMessage());
		}
	}

	/**
	 * Verifica se uma determinada sessao de votacao ainda esta aberta
	 * @param idSessaoVotacao
	 * @return Verdadeiro caso a sessao esteja aberta e falso caso contrario
	 */
	public Boolean isSessaoVotacaoAberta(Long idSessaoVotacao) {
		SessaoVotacao sessao = sessaoVotacaoRepository.findById(idSessaoVotacao).orElseThrow(() -> new IllegalStateException(
				new PautaNotFoundException("A sessão de votação " + idSessaoVotacao + " não foi encontrada")
		));
		if (sessao.getPauta() != null) {
			Calendar agora = Calendar.getInstance();
			Calendar duracaoSessao = Calendar.getInstance();
			duracaoSessao.setTime(sessao.getPauta().getSessaoVotacao().getDataHoraCriacao());
			duracaoSessao.add(Calendar.MINUTE, sessao.getPauta().getSessaoVotacao().getDuracaoEmMinutos());
			if (agora.getTime().after(duracaoSessao.getTime())) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

}
