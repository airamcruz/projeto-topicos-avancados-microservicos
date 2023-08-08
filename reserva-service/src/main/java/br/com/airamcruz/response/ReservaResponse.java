package br.com.airamcruz.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReservaResponse(UUID id, QuartoResponse quarto, LocalDate dataCheckIn, LocalDate dataCheckOut,
		BigDecimal preco, String situacao) {

}
