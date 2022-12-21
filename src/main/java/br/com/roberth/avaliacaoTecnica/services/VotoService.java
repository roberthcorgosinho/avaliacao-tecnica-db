package br.com.roberth.avaliacaoTecnica.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import br.com.roberth.avaliacaoTecnica.enums.EnumRespostaVotacao;
import br.com.roberth.avaliacaoTecnica.model.entidades.Voto;
import br.com.roberth.avaliacaoTecnica.model.entidades.VotoId;
import br.com.roberth.avaliacaoTecnica.repository.VotoRepository;

@Service
public class VotoService {
	
	@Autowired
	private VotoRepository votoRepository;
	
	public Voto adicionarVoto(Voto voto) {
		voto.setOpcao(voto.getOpcao().toUpperCase());
		return votoRepository.save(voto);
	}
	
	public Boolean existeVoto(Voto voto) {
		if (!votoRepository.findById(voto.getId()).isEmpty()) {
			return true;
		}
		return false;
	}
	
	public Long totalVotos(Long idSessao) {
		Voto voto = new Voto();
		voto.setId(new VotoId());
		voto.getId().setIdSessaoVotacao(idSessao);
		return votoRepository.count(Example.of(voto));
	}
	
	public Long totalVotosSim(Long idSessao) {
		Voto voto = new Voto();
		voto.setId(new VotoId());
		voto.getId().setIdSessaoVotacao(idSessao);
		voto.setOpcao(EnumRespostaVotacao.SIM.getDescricao().toUpperCase());
		return votoRepository.count(Example.of(voto));
	}
	
	public Long totalVotosNao(Long idSessao) {
		Voto voto = new Voto();
		voto.setId(new VotoId());
		voto.getId().setIdSessaoVotacao(idSessao);
		voto.setOpcao(EnumRespostaVotacao.NAO.getDescricao().toUpperCase());
		return votoRepository.count(Example.of(voto));
	}

}
