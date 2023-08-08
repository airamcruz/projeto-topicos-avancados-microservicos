package br.com.airamcruz.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.airamcruz.model.Quarto;
import br.com.airamcruz.proxy.CotacaoProxy;
import br.com.airamcruz.repository.QuartoRepository;
import br.com.airamcruz.repository.TipoQuartoRepository;
import br.com.airamcruz.request.QuartoRequest;
import br.com.airamcruz.response.CotacaoResponse;
import br.com.airamcruz.response.QuartoResponse;

@Service
public class QuartoService {

	@Autowired
	private CotacaoProxy cotacaoProxy;

	@Autowired
	private QuartoRepository repository;

	@Autowired
	private TipoQuartoRepository tipoQuartoRepository;

	public QuartoResponse create(QuartoRequest request) {

		var model = new Quarto();

		BeanUtils.copyProperties(request, model);

		var tipoQuarto = tipoQuartoRepository.findById(request.tipoQuartoId());

		if (tipoQuarto.isPresent()) {
			model.setTipoQuarto(tipoQuarto.get());
		}

		repository.save(model);

		return this.createResponse(model, Optional.ofNullable(null));
	}

	public QuartoResponse update(UUID id, QuartoRequest request) {
		Optional<Quarto> optionalQuarto = repository.findById(id);

		if (optionalQuarto.isPresent()) {

			Quarto model = optionalQuarto.get();

			BeanUtils.copyProperties(request, model);

			var tipoQuarto = tipoQuartoRepository.findById(request.tipoQuartoId());

			if (tipoQuarto.isPresent()) {
				model.setTipoQuarto(tipoQuarto.get());
			}

			repository.save(model);

			return this.createResponse(model, Optional.ofNullable(null));
		} else {
			return null;
		}
	}

	public boolean delete(UUID id) {
		Optional<Quarto> optionalQuarto = repository.findById(id);

		if (optionalQuarto.isPresent()) {
			repository.delete(optionalQuarto.get());
			return true;
		} else {
			return false;
		}
	}

	public List<QuartoResponse> findAll(Optional<String> moeda) {

		List<Quarto> lista = repository.findAll(Sort.by(Direction.ASC, "tipoQuarto", "capacidade", "valorDiaria"));

		return lista.stream().map(model -> this.createResponse(model, moeda)).collect(Collectors.toList());
	}

	public Optional<QuartoResponse> findById(UUID id, Optional<String> moeda) {

		var model = repository.findById(id);

		if (model.isPresent()) {
			return Optional.of(this.createResponse(model.get(), moeda));

		} else {
			return Optional.empty();
		}

	}

	private QuartoResponse createResponse(Quarto model, Optional<String> moeda) {

		final BigDecimal valorCotacao = this.getValorCotacao(moeda);

		return new QuartoResponse(model.getId(), model.getTipoQuarto().getDescricao(), model.getCapacidade(),
				model.getValorDiaria().multiply(valorCotacao).setScale(2, RoundingMode.CEILING));
	}

	private BigDecimal getValorCotacao(Optional<String> moeda) {

		Optional<BigDecimal> valorCotacaoTemp = Optional.empty();
		
		String sigla = moeda.orElse("");

		if (!sigla.isBlank() && !sigla.equalsIgnoreCase("USD")) {

			String parMoeda = String.format("USD-%s", moeda.get());

			List<CotacaoResponse> cotacao = cotacaoProxy.getCotacao("json", parMoeda);

			if (!cotacao.isEmpty()) {
				valorCotacaoTemp = Optional.of(cotacao.get(0).bid());
			} else {
				valorCotacaoTemp = Optional.empty();
			}
		}

		return valorCotacaoTemp.orElse(BigDecimal.ONE);
	}
}
