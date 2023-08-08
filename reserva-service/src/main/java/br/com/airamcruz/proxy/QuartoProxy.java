package br.com.airamcruz.proxy;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.airamcruz.response.QuartoResponse;

@FeignClient(name = "quarto-service")
public interface QuartoProxy {

	@GetMapping("quarto/{id}")
	public QuartoResponse findById(@PathVariable UUID id, @RequestParam(value = "moeda") String string);

	@GetMapping("quarto/")
	public List<QuartoResponse> findAll(@RequestParam(value = "moeda") String moeda);
}
