package br.com.airamcruz.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import br.com.airamcruz.enums.SituacaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "reserva")
public class Reserva implements Serializable {

	private static final long serialVersionUID = 4797067879606985024L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(nullable = false)
	private UUID quartoId;

	@Column(nullable = false)
	private LocalDate dataCheckIn;

	@Column(nullable = false)
	private LocalDate dataCheckOut;

	@Column(nullable = false)
	private BigDecimal preco;

	@Column(nullable = false)
	private SituacaoEnum situacao;

	public Reserva() {
		super();
	}
	
	public Reserva(UUID id, UUID quartoId, LocalDate dataCheckIn, LocalDate dataCheckOut, BigDecimal preco,
			SituacaoEnum situacao) {
		super();
		this.id = id;
		this.quartoId = quartoId;
		this.dataCheckIn = dataCheckIn;
		this.dataCheckOut = dataCheckOut;
		this.preco = preco;
		this.situacao = situacao;
	}

	public Reserva(UUID quartoId, LocalDate dataCheckIn, SituacaoEnum situacao) {
		super();
		this.quartoId = quartoId;
		this.dataCheckIn = dataCheckIn;
		this.situacao = situacao;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getQuartoId() {
		return quartoId;
	}

	public void setQuartoId(UUID quartoId) {
		this.quartoId = quartoId;
	}

	public LocalDate getDataCheckIn() {
		return dataCheckIn;
	}

	public void setDataCheckIn(LocalDate dataCheckIn) {
		this.dataCheckIn = dataCheckIn;
	}

	public LocalDate getDataCheckOut() {
		return dataCheckOut;
	}

	public void setDataCheckOut(LocalDate dataCheckOut) {
		this.dataCheckOut = dataCheckOut;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public SituacaoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoEnum situacao) {
		this.situacao = situacao;
	}
}
