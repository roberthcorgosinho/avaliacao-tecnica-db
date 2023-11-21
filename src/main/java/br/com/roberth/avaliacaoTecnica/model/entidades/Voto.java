package br.com.roberth.avaliacaoTecnica.model.entidades;

import br.com.roberth.avaliacaoTecnica.enums.EnumRespostaVotacao;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Voto {
	
	@EmbeddedId
	private VotoId id;

	@Enumerated(EnumType.STRING)
	private EnumRespostaVotacao opcao;
	
	@ManyToOne
	@JoinColumn(name="IdSessaoVotacao", insertable=false, updatable=false)
	@JsonIgnore
	private SessaoVotacao sessaoVotacao;

}
