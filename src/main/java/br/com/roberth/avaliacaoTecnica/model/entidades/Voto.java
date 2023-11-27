package br.com.roberth.avaliacaoTecnica.model.entidades;

import br.com.roberth.avaliacaoTecnica.enums.EnumRespostaVotacao;
import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voto {
	
	@EmbeddedId
	private VotoId id;

	@Enumerated(EnumType.STRING)
	private EnumRespostaVotacao opcao;
	
	@ManyToOne
	@JoinColumn(name="IdSessaoVotacao", insertable=false, updatable=false)
	@JsonIgnore
	private SessaoVotacao sessaoVotacao;

	@PrePersist
	public void preInsert() throws PautaBadRequestException {
		if (sessaoVotacao == null) {
			throw new PautaBadRequestException("Para computar um voto é preciso informar a sessão de votação");
		}
	}

	@PreUpdate
	public void preUpdate() throws PautaBadRequestException {
		throw new PautaBadRequestException("Voto não pode ser alterado");
	}

	@PreRemove
	public void preRemove() throws PautaBadRequestException {
		throw new PautaBadRequestException("Voto não pode ser removido");
	}

}
