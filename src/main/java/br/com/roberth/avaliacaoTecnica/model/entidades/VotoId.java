package br.com.roberth.avaliacaoTecnica.model.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class VotoId {
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "IdSessaoVotacao")
	private Long idSessaoVotacao;

}
