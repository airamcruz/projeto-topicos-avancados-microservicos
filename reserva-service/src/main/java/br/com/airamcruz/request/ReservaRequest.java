package br.com.airamcruz.request;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservaRequest(UUID quartoId, LocalDateTime dataCheckIn, LocalDateTime dataCheckOut, String moeda) {

}
