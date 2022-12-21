package br.com.roberth.avaliacaoTecnica.model.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Voto {
	
	@EmbeddedId
	private VotoId id;
	
	private String opcao;
	
	@ManyToOne
	@JoinColumn(name="IdSessaoVotacao", insertable=false, updatable=false)
	@JsonIgnore
	private SessaoVotacao sessaoVotacao;

}
