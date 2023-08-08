package br.com.airamcruz.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.airamcruz.model.TipoQuarto;
import br.com.airamcruz.repository.TipoQuartoRepository;
import br.com.airamcruz.request.TipoQuartoRequest;
import br.com.airamcruz.response.TipoQuartoResponse;

@Service
public class TipoQuartoService {

	@Autowired
	private TipoQuartoRepository repository;

	public TipoQuartoResponse create(TipoQuartoRequest request) {

		var model = new TipoQuarto();

		BeanUtils.copyProperties(request, model);

		repository.save(model);

		return this.createResponse(model);
	}

	public TipoQuartoResponse update(UUID id, TipoQuartoRequest request) {
		Optional<TipoQuarto> optionalTipoQuarto = repository.findById(id);

		if (optionalTipoQuarto.isPresent()) {

			TipoQuarto model = optionalTipoQuarto.get();

			BeanUtils.copyProperties(request, model);

			repository.save(model);

			return this.createResponse(model);
		} else {
			return null;
		}
	}

	public boolean delete(UUID id) {
		Optional<TipoQuarto> optionalTipoQuarto = repository.findById(id);

		if (optionalTipoQuarto.isPresent()) {
			repository.delete(optionalTipoQuarto.get());
			return true;
		} else {
			return false;
		}
	}

	public List<TipoQuartoResponse> findAll() {

		List<TipoQuarto> lista = repository.findAll(Sort.by(Direction.ASC, "descricao"));

		return lista.stream().map(model -> this.createResponse(model)).collect(Collectors.toList());
	}

	public Optional<TipoQuartoResponse> findById(UUID id) {

		var model = repository.findById(id);

		if (model.isPresent()) {
			return Optional.of(this.createResponse(model.get()));

		} else {
			return Optional.empty();
		}

	}

	private TipoQuartoResponse createResponse(TipoQuarto model) {
		return new TipoQuartoResponse(model.getId(), model.getDescricao());
	}
}
