package br.com.roberth.avaliacaoTecnica.enums;

public enum EnumRespostaVotacao {
	SIM(1, "Sim"), NAO(2, "Não");

	private Integer id;
	private String descricao;

	EnumRespostaVotacao(Integer pId, String pDescricao) {
		this.id = pId;
		this.descricao = pDescricao;
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
}
