package br.com.roberth.avaliacaoTecnica.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaConflictException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaInternalErrorException;
import br.com.roberth.avaliacaoTecnica.model.dto.UserStatusResponseRestApiDataModel;
import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import br.com.roberth.avaliacaoTecnica.repository.SessaoVotacaoRepository;
import br.com.roberth.avaliacaoTecnica.util.StringUtils;

@Service
public class SessaoVotacaoService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	public SessaoVotacao adicionarSessao(SessaoVotacao sessao) throws Exception {
		try {
			if (sessao.getPauta().getSessaoVotacao() != null && sessao.getPauta().getSessaoVotacao().getId() != null) {
				throw new PautaConflictException("Já existe uma sessão de votação para a Pauta " + sessao.getPauta().getId());
			}
			return sessaoVotacaoRepository.save(sessao);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	public Boolean isSessaoVotacaoAberta(SessaoVotacao sessao) throws Exception {
		try {
			if (sessao.getPauta().getSessaoVotacao() != null) {
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
		} catch (Exception e) {
			throw new PautaInternalErrorException(e);
		}
	}
	
	public Boolean podeVotar(String cpf) throws Exception {
		try {
			String uri = env.getProperty("userInfo.url") + "/" + StringUtils.removeCaracteresEspeciais(cpf);
			RestTemplate rest = new RestTemplate();
			UserStatusResponseRestApiDataModel userInfo = rest.getForObject(uri, UserStatusResponseRestApiDataModel.class);
			return (userInfo.getStatus().equalsIgnoreCase("ABLE_TO_VOTE") ? true : false) ;
		} catch (Exception e) {
			throw new PautaInternalErrorException("Não foi possível acessar as informações do usuário para validar sua autorização para votar");
		}
	}	

}
