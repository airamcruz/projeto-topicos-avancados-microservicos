package br.com.airamcruz.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.airamcruz.request.TipoQuartoRequest;
import br.com.airamcruz.response.TipoQuartoResponse;
import br.com.airamcruz.service.TipoQuartoService;

@RestController
@RequestMapping(value = "/tipo-quarto")
public class TipoQuartoController {

	@Autowired
	private TipoQuartoService service;
	
	@Autowired
	private Environment environment;

//	@GetMapping("/")
//	public String getHome() {
//		return "Ola Mundo!!";
//	}

	@PostMapping
	public ResponseEntity<TipoQuartoResponse> create(@RequestBody TipoQuartoRequest request) {

		TipoQuartoResponse response = service.create(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TipoQuartoResponse> update(@PathVariable UUID id, @RequestBody TipoQuartoRequest request) {

		TipoQuartoResponse response = service.update(id, request);

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
	public ResponseEntity<TipoQuartoResponse> findById(@PathVariable UUID id) {

		Optional<TipoQuartoResponse> response = service.findById(id);

		if (response.isPresent()) {

		    HttpHeaders responseHeaders = new HttpHeaders();
		    responseHeaders.set("port", environment.getProperty("local.server.port"));
			
			return new ResponseEntity<TipoQuartoResponse>(response.get(), responseHeaders, HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(value = { "", "/" })
	public ResponseEntity<List<TipoQuartoResponse>> findAll() {
		return ResponseEntity.ok(service.findAll());
	}
}
