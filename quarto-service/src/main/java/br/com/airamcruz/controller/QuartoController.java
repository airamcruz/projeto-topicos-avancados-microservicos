package br.com.airamcruz.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.airamcruz.request.QuartoRequest;
import br.com.airamcruz.response.QuartoResponse;
import br.com.airamcruz.service.QuartoService;

@RestController
@RequestMapping(value = "/quarto")
public class QuartoController {

	@Autowired
	private QuartoService service;

//	@GetMapping("/")
//	public String getHome() {
//		return "Ola Mundo!!";
//	}

	@PostMapping
	public ResponseEntity<QuartoResponse> create(@RequestBody QuartoRequest request) {

		QuartoResponse response = service.create(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<QuartoResponse> update(@PathVariable UUID id, @RequestBody QuartoRequest request) {

		QuartoResponse response = service.update(id, request);

		if (response != null) {
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {

		boolean deletado = service.delete(id);

		if (deletado) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<QuartoResponse> findById(@PathVariable UUID id,
			@RequestParam(value = "moeda") Optional<String> moeda) {

		Optional<QuartoResponse> response = service.findById(id, moeda);

		if (response.isPresent()) {
			return ResponseEntity.ok(response.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = { "", "/" })
	public ResponseEntity<List<QuartoResponse>> findAll(
			@RequestParam(value = "moeda") Optional<String> moeda) {

		return ResponseEntity.ok(service.findAll(moeda));
	}
}
