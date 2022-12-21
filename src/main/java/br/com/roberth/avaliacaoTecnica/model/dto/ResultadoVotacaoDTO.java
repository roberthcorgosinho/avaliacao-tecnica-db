package br.com.roberth.avaliacaoTecnica.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadoVotacaoDTO {
	
	private Long totalVotos;
	
	private Long totalSim;
	
	private Long totalNao;

}
