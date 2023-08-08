package br.com.airamcruz.enums;

public enum SituacaoEnum {
	CANCELADA("Cancelada"),
	CONFIRMADA("Confirmada");
	
	private final String descricao;

	SituacaoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
