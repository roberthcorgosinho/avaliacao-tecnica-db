package br.com.roberth.avaliacaoTecnica.model.entidades;

import br.com.roberth.avaliacaoTecnica.model.dto.ResultadoVotacaoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class Pauta {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "id")
	private Long id;
	
	@Column(nullable = false, name = "assunto")
	private String assunto;
	
	@OneToOne
    @JoinColumn(name = "IdSessaoVotacao")
	private SessaoVotacao sessaoVotacao;
	
	@Transient
	private ResultadoVotacaoDTO resultado;

}
