package br.com.airamcruz.response;

import java.math.BigDecimal;
import java.util.UUID;

public record QuartoResponse(UUID id, String tipoQuarto, Integer capacidade, BigDecimal valorDiaria) {
	
}
