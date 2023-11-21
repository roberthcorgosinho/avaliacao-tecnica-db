package br.com.roberth.avaliacaoTecnica.model.entidades;

import br.com.roberth.avaliacaoTecnica.model.dto.PautaDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.PautaUpdateDTO;
import br.com.roberth.avaliacaoTecnica.model.dto.ResultadoVotacaoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

	public Pauta(PautaDTO pauta) {
		this.assunto = pauta.assunto();
	}

	public void atualizarDados(PautaUpdateDTO dados) {
		if (dados.assunto() != null) {
			this.assunto = dados.assunto();
		}
	}

}
