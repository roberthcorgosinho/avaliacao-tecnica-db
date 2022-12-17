package br.com.roberth.avaliacaoTecnica.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pauta {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "id")
	private Long id;
	
	@Column(nullable = false, name = "titulo")
	private String titulo;
	
	@Column(nullable = true, name = "descricao")
	private String descricao;
	
	@Column(nullable = true, name = "dataHoraCriacao")
	private Date dataHoraCriacao;
	
	public Pauta() {
		super();
	}
	
	/**
	 * @param titulo
	 * @param descricao
	 */
	public Pauta(String titulo, String descricao) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataHoraCriacao = new Date();
	}

	/**
	 * @param id
	 * @param titulo
	 * @param descricao
	 * @param dataHoraCriacao
	 */
	public Pauta(Long id, String titulo, String descricao, Date dataHoraCriacao) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataHoraCriacao = dataHoraCriacao;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the dataHoraCriacao
	 */
	public Date getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	/**
	 * @param dataHoraCriacao the dataHoraCriacao to set
	 */
	public void setDataHoraCriacao(Date dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataHoraCriacao, descricao, id, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Pauta)) {
			return false;
		}
		Pauta other = (Pauta) obj;
		return Objects.equals(dataHoraCriacao, other.dataHoraCriacao) && Objects.equals(descricao, other.descricao)
				&& Objects.equals(id, other.id) && Objects.equals(titulo, other.titulo);
	}

	@Override
	public String toString() {
		return "Pauta [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", dataHoraCriacao="
				+ dataHoraCriacao + "]";
	}	

}
