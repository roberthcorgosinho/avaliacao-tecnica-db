package br.com.roberth.avaliacaoTecnica.model.entidades;

import java.util.Date;
import java.util.List;

import br.com.roberth.avaliacaoTecnica.exceptions.PautaBadRequestException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessaoVotacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "id")
	private Long id;
	
	@Column(nullable = false, name = "duracaoMinutos")
	private Integer duracaoEmMinutos;
	
	@Column(nullable = false, name = "dataHoraCriacao")
	private Date dataHoraCriacao;
	
	@OneToOne(mappedBy = "sessaoVotacao", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Pauta pauta;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sessaoVotacao")    
	private List<Voto> votos;

	@PrePersist
	public void preInsert() throws PautaBadRequestException {
		if (pauta == null) {
			throw new PautaBadRequestException("Para abrir uma sessão de votação é necessário indicar a pauta cuja sessão será aberta");
		}
		if (this.duracaoEmMinutos == null || this.duracaoEmMinutos <= 0) {
			this.duracaoEmMinutos = 1;
		}
		this.dataHoraCriacao = new Date();
	}

	@PreRemove
	public void preRemove() throws PautaBadRequestException {
		throw new PautaBadRequestException("Uma sessao de votação não pode ser removida");
	}

}
