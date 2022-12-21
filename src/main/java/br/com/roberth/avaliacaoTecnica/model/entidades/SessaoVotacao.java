package br.com.roberth.avaliacaoTecnica.model.entidades;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class SessaoVotacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "id")
	private Long id;
	
	@Column(nullable = false, name = "duracaoMinutos")
	private Integer duracaoEmMinutos;
	
	@Column(nullable = false, name = "dataHoraCriacao")
	private Date dataHoraCriacao;
	
	@OneToOne(mappedBy = "sessaoVotacao")
	@JsonIgnore
	private Pauta pauta;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sessaoVotacao")    
	private List<Voto> votos;
	
	@PrePersist
	public void preInsert() {
		if (this.duracaoEmMinutos == null) {
			this.duracaoEmMinutos = 1;
		}
		this.dataHoraCriacao = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		if (this.duracaoEmMinutos == null) {
			this.duracaoEmMinutos = 1;
		}
	}

}
