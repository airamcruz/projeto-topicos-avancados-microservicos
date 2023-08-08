package br.com.airamcruz.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "quarto")
public class Quarto implements Serializable {
	
	private static final long serialVersionUID = 7432106704740421949L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    /**
     * Tipos de quartos:
     * Quarto de Solteiro
     * Duplo Solteiro
     * Quarto Casal
     * Dormitório
     * Apartamento
     */
    @ManyToOne
    @JoinColumn(name = "tipo_quarto_id", nullable = false)
    private TipoQuarto tipoQuarto;
    
    @Column(nullable = false)
    private Integer capacidade;
    
    @Column(nullable = false)
    private BigDecimal valorDiaria;

	public Quarto() {
		super();
	}
	

	public Quarto(UUID id, TipoQuarto tipoQuarto, Integer capacidade, BigDecimal valorDiaria) {
		super();
		this.id = id;
		this.tipoQuarto = tipoQuarto;
		this.capacidade = capacidade;
		this.valorDiaria = valorDiaria;
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public TipoQuarto getTipoQuarto() {
		return tipoQuarto;
	}

	public void setTipoQuarto(TipoQuarto tipoQuarto) {
		this.tipoQuarto = tipoQuarto;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	public BigDecimal getValorDiaria() {
		return valorDiaria;
	}

	public void setValorDiaria(BigDecimal valorDiaria) {
		this.valorDiaria = valorDiaria;
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
		Quarto other = (Quarto) obj;
		return Objects.equals(id, other.id);
	}   
	
}
