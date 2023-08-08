package br.com.airamcruz.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.airamcruz.response.CotacaoResponse;

//https://docs.awesomeapi.com.br/api-de-moedas
@FeignClient(name = "cotacao-service", url = "https://economia.awesomeapi.com.br")
public interface CotacaoProxy {
	
	@GetMapping(value = "/{format}/{moeda}")
	public List<CotacaoResponse> getCotacao(
			@PathVariable(value = "format") String format,
			@PathVariable(value = "moeda") String moeda);
}
