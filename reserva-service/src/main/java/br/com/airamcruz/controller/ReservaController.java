package br.com.airamcruz.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.airamcruz.response.ReservaResponse;
import br.com.airamcruz.service.ReservaService;

@RestController
@RequestMapping(value = "/reserva")
public class ReservaController {

	@Autowired
	private ReservaService service;

//	@GetMapping("/")
//	public String getHome() {
//		return "Ola Mundo!!";
//	}

	@GetMapping("/{id}")
	public ResponseEntity<ReservaResponse> findById(@PathVariable UUID id,
			@RequestParam(value = "moeda") Optional<String> moeda) {

		Optional<ReservaResponse> response = service.findById(id, moeda);

		if (response.isPresent()) {
			return ResponseEntity.ok(response.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = { "", "/" })
	public ResponseEntity<List<ReservaResponse>> findAll(@RequestParam(value = "moeda") Optional<String> moeda) {
		return ResponseEntity.ok(service.findAll(moeda));
	}

	@GetMapping("/quarto/{quartoId}")
	public ResponseEntity<List<ReservaResponse>> reservasQuarto(@PathVariable UUID quartoId,
			@RequestParam(value = "moeda") Optional<String> moeda) {
		return ResponseEntity.ok(service.findAllReservaQuarto(quartoId, moeda));
	}

	@GetMapping("/quarto/disponivel")
	public ResponseEntity<Object> quartosDisponiveis(
			@RequestParam(value = "checkin", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkin,
			@RequestParam(value = "checkout") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> checkout,
			@RequestParam(value = "moeda") Optional<String> moeda) {

		return ResponseEntity.ok(service.quartosDisponiveis(checkin, checkout, moeda));
	}

	@GetMapping("/simular/{quartoId}")
	public ResponseEntity<Object> simular(@PathVariable UUID quartoId,
			@RequestParam(value = "checkin", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkin,
			@RequestParam(value = "checkout") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> checkout,
			@RequestParam(value = "moeda") Optional<String> moeda) {

		Optional<ReservaResponse> response = service.simular(quartoId, checkin, checkout, moeda);

		if (response.isPresent()) {
			return ResponseEntity.ok(response.get());
		} else {
			return ResponseEntity.ok("Reserva não disponivel para a data escolhida.");
		}
	}

	@PostMapping("/simular/{quartoId}")
	public ResponseEntity<Object> confirmar(@PathVariable UUID quartoId,
			@RequestParam(value = "checkin", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkin,
			@RequestParam(value = "checkout") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<LocalDate> checkout,
			@RequestParam(value = "moeda") Optional<String> moeda) {

		Optional<ReservaResponse> response = service.confirmar(quartoId, checkin, checkout, moeda);

		if (response.isPresent()) {
			return ResponseEntity.ok(response.get());
		} else {
			return ResponseEntity.ok("Reserva não disponivel para a data escolhida.");
		}
	}

	@PostMapping("/cancelar/{id}")
	public ResponseEntity<ReservaResponse> cancelar(@PathVariable UUID id,
			@RequestParam(value = "moeda") Optional<String> moeda) {

		Optional<ReservaResponse> response = service.cancelar(id, moeda);

		if (response.isPresent()) {
			return ResponseEntity.ok(response.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
