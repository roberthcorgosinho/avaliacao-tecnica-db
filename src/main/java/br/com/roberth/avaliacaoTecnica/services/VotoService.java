package br.com.roberth.avaliacaoTecnica.services;

import br.com.roberth.avaliacaoTecnica.enums.EnumRespostaVotacao;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaConflictException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaForbbidenExeception;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaNotFoundException;
import br.com.roberth.avaliacaoTecnica.model.dto.PautaDadosDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.ResultadoVotacaoDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.UserStatusResponseRestApiDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.VotoDadosDTO;
import br.com.roberth.avaliacaoTecnica.model.entidades.Pauta;
import br.com.roberth.avaliacaoTecnica.model.entidades.Voto;
import br.com.roberth.avaliacaoTecnica.model.entidades.VotoId;
import br.com.roberth.avaliacaoTecnica.repository.PautaRepository;
import br.com.roberth.avaliacaoTecnica.repository.VotoRepository;
import br.com.roberth.avaliacaoTecnica.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class VotoService {

	@Autowired
	private Environment env;

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private PautaService pautaService;

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	/**
	 * Computa um voto em na sessao de votacao aberta de uma determinada pauta
	 * @param voto
	 * @return o voto computado
	 * @throws Exception
	 */
	public VotoDadosDTO adicionarVoto(VotoDadosDTO voto) throws Exception {
		try {
			if (this.podeVotar(voto.cpf())) {
				Optional<Pauta> pauta = pautaRepository.findById(voto.idPauta());
				if (!pauta.isPresent()) {
					throw new PautaNotFoundException("A pauta informada não existe");
				}
				if (pauta.get().getSessaoVotacao() == null) {
					throw new PautaNotFoundException("Não existe sessão de votação para a pauta " + pauta.get().getId() + " - " + pauta.get().getAssunto());
				} else if (!sessaoVotacaoService.isSessaoVotacaoAberta(pauta.get().getSessaoVotacao().getId())) {
					throw new PautaConflictException("Não existe sessão de votação aberta para a pauta " + pauta.get().getId() + " - " + pauta.get().getAssunto());
				}
				Voto novoVoto = new Voto();
				novoVoto.setId(new VotoId());
				novoVoto.getId().setCpf(voto.cpf());
				novoVoto.getId().setIdSessaoVotacao(pauta.get().getSessaoVotacao().getId());
				novoVoto.setSessaoVotacao(pauta.get().getSessaoVotacao());
				if (!existeVoto(novoVoto)) {
					novoVoto.setOpcao(EnumRespostaVotacao.valueOf(voto.opcao()));
					novoVoto = votoRepository.save(novoVoto);
					return new VotoDadosDTO(novoVoto);
				} else {
					throw new PautaConflictException("O usuário " + voto.cpf() + " já votou na sessão de votação atual da pauta informada");
				}
			} else {
				throw new PautaForbbidenExeception(
						"O usuário " + voto.cpf() +
							 " não está autorizado a votar na sessão de votação atual da pauta informada"
						);
			}
		} catch (Exception exception) {
			throw new Exception(exception);
		}
	}

	/**
	 * Verifica se um determinado usuario ja realizou a votacao em uma sessao de votacao aberta em uma determinada pauta
	 * @param voto
	 * @return Verdadeiro se ja realizou o voto ou falso se ainda nao votou
	 * @throws Exception
	 */
	public Boolean existeVoto(Voto voto) throws Exception {
		try {
			if (!votoRepository.findById(voto.getId()).isEmpty()) {
				return true;
			}
			return false;
		} catch (Exception exception) {
			throw new Exception(exception);
		}
	}

	/**
	 * Totaliza os votos computados com a opcao SIM
	 * @param idSessao
	 * @return total de votos com a opcao SIM
	 */
	public Long totalVotosSim(Long idSessao) {
		Voto voto = new Voto();
		voto.setId(new VotoId());
		voto.getId().setIdSessaoVotacao(idSessao);
		voto.setOpcao(EnumRespostaVotacao.SIM);
		return votoRepository.count(Example.of(voto));
	}

	/**
	 * Totaliza os votos computados com a opcao NAO
	 * @param idSessao
	 * @return total de votos com a opcao NAO
	 */
	public Long totalVotosNao(Long idSessao) {
		Voto voto = new Voto();
		voto.setId(new VotoId());
		voto.getId().setIdSessaoVotacao(idSessao);
		voto.setOpcao(EnumRespostaVotacao.NAO);
		return votoRepository.count(Example.of(voto));
	}

	/**
	 * Totaliza os votos da sessao de votacao atual de uma pauta especifica
	 * @param idPauta
	 * @return o total de votos geral e o total de votos de cada opcao
	 */
	public ResultadoVotacaoDTO resultadoVotacao(Long idPauta) {
		PautaDadosDTO pauta = pautaService.obterPauta(idPauta);
		Long votosSim = this.totalVotosSim(pauta.idSessaoVotacao());
		Long votosNao = this.totalVotosNao(pauta.idSessaoVotacao());
		return new ResultadoVotacaoDTO(pauta.id(), pauta.assunto(), (votosSim + votosNao), votosSim, votosNao);
	}

	/**
	 * Consome uma API externa para verificar se um determinado usuario pode votar na sessao atual de votacao
	 * @param cpf
	 * @return Verdadeiro se pode votar e falso caso contrario
	 */
	public Boolean podeVotar(String cpf) {
		String uri = env.getProperty("userInfo.url") + "/" + StringUtils.removeCaracteresEspeciais(cpf);
		RestTemplate rest = new RestTemplate();
		UserStatusResponseRestApiDTO userInfo = rest.getForObject(uri, UserStatusResponseRestApiDTO.class);
		return (userInfo.status().equalsIgnoreCase("ABLE_TO_VOTE") ? true : false) ;
	}

}
