package br.com.roberth.avaliacaoTecnica.model.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoId implements Serializable {

	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "IdSessaoVotacao")
	private Long idSessaoVotacao;

}
