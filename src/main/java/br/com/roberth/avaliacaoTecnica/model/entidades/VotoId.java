package br.com.roberth.avaliacaoTecnica.model.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoId implements Serializable {

	@NotBlank
	@Column(name = "cpf")
	private String cpf;

	@NotNull
	@Column(name = "IdSessaoVotacao")
	private Long idSessaoVotacao;

}
