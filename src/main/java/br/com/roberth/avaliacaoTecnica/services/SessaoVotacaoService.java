package br.com.roberth.avaliacaoTecnica.services;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaConflictException;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaInternalErrorException;
import br.com.roberth.avaliacaoTecnica.model.dto.UserStatusResponseRestApiDTO;
import br.com.roberth.avaliacaoTecnica.model.entidades.SessaoVotacao;
import br.com.roberth.avaliacaoTecnica.repository.SessaoVotacaoRepository;
import br.com.roberth.avaliacaoTecnica.util.StringUtils;

@Service
public class SessaoVotacaoService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;
	
	public SessaoVotacao adicionarSessao(SessaoVotacao sessao) throws PautaConflictException {
		if (sessao.getPauta().getSessaoVotacao() != null && sessao.getPauta().getSessaoVotacao().getId() != null) {
			throw new PautaConflictException("Já existe uma sessão de votação para a Pauta " + sessao.getPauta().getId());
		}
		return sessaoVotacaoRepository.save(sessao);
	}
	
	public Boolean isSessaoVotacaoAberta(SessaoVotacao sessao) {
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
	}
	
	public Boolean podeVotar(String cpf) {
		String uri = env.getProperty("userInfo.url") + "/" + StringUtils.removeCaracteresEspeciais(cpf);
		RestTemplate rest = new RestTemplate();
		UserStatusResponseRestApiDTO userInfo = rest.getForObject(uri, UserStatusResponseRestApiDTO.class);
		return (userInfo.status().equalsIgnoreCase("ABLE_TO_VOTE") ? true : false) ;
	}

}
