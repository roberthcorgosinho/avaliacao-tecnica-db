package br.com.roberth.avaliacaoTecnica.model.entidades;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import br.com.roberth.avaliacaoTecnica.model.dto.ResultadoVotacaoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

	@NotBlank
	@Column(nullable = false, name = "assunto")
	private String assunto;
	
	@OneToOne
    @JoinColumn(name = "IdSessaoVotacao")
	@JsonBackReference
	private SessaoVotacao sessaoVotacao;
	
	@Transient
	private ResultadoVotacaoDTO resultado;

	@PreRemove
	public void preRemove() throws PautaBadRequestException {
		throw new PautaBadRequestException("Uma pauta não pode ser removida");
	}

}
