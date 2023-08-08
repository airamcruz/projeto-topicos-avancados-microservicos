package br.com.airamcruz.request;

import java.math.BigDecimal;
import java.util.UUID;

public record QuartoRequest(UUID tipoQuartoId, Integer capacidade, BigDecimal valorDiaria) {

}
