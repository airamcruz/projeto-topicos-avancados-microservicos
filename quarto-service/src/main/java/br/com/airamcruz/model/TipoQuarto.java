package br.com.airamcruz.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tipo_quarto")
public class TipoQuarto implements Serializable {

	private static final long serialVersionUID = -801778669075882021L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private String descricao;

	public TipoQuarto() {
		super();
	}

	public TipoQuarto(UUID id) {
		super();
		this.id = id;
	}

	public TipoQuarto(String descricao) {
		super();
		this.descricao = descricao;
	}

	public TipoQuarto(UUID id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoQuarto other = (TipoQuarto) obj;
		return Objects.equals(id, other.id);
	}

}
