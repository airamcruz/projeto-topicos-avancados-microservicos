package br.com.airamcruz.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.airamcruz.enums.SituacaoEnum;
import br.com.airamcruz.model.Reserva;
import br.com.airamcruz.proxy.CotacaoProxy;
import br.com.airamcruz.proxy.QuartoProxy;
import br.com.airamcruz.repository.ReservaRepository;
import br.com.airamcruz.response.CotacaoResponse;
import br.com.airamcruz.response.QuartoResponse;
import br.com.airamcruz.response.ReservaResponse;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository repository;

	@Autowired
	private QuartoProxy quartoProxy;

	@Autowired
	private CotacaoProxy cotacaoProxy;

	public Optional<ReservaResponse> findById(UUID id, Optional<String> moeda) {

		var model = repository.findById(id);

		if (model.isPresent()) {
			return Optional.of(this.createResponse(model.get(), moeda));

		} else {
			return Optional.empty();
		}

	}

	public List<ReservaResponse> findAll(Optional<String> moeda) {

		List<Reserva> lista = repository.findAll(Sort.by(Direction.DESC, "dataCheckIn", "preco"));

		return lista.stream().map(model -> this.createResponse(model, moeda)).collect(Collectors.toList());
	}

	public List<ReservaResponse> findAllReservaQuarto(UUID quartoId, Optional<String> moeda) {

		List<Reserva> lista = repository.findAllByQuartoId(quartoId, Sort.by(Direction.DESC, "dataCheckIn", "preco"));

		return lista.stream().map(model -> this.createResponse(model, moeda)).collect(Collectors.toList());
	}

	public List<QuartoResponse> quartosDisponiveis(LocalDate checkin, Optional<LocalDate> checkout,
			Optional<String> moeda) {

		final LocalDate dataCheckout = checkout.orElse(checkin.plusDays(1));

		return quartoProxy.findAll(moeda.orElse(null)).stream()
				.filter(quarto -> this.checarDisponibilidade(quarto.id(), checkin, dataCheckout))
				.collect(Collectors.toList());
	}

	public Optional<ReservaResponse> simular(UUID quartoId, LocalDate checkin, Optional<LocalDate> checkout,
			Optional<String> moeda) {
		
		var optionalModel = this.createReserva(quartoId, checkin, checkout, moeda);
		
		if(optionalModel.isPresent()) {
			return Optional.of(this.createResponse(optionalModel.get(), moeda));
		}
		
		return Optional.empty();
	}

	public Optional<ReservaResponse> confirmar(UUID quartoId, LocalDate checkin, Optional<LocalDate> checkout,
			Optional<String> moeda) {
		
		var optionalModel = this.createReserva(quartoId, checkin, checkout, moeda);
		
		if(optionalModel.isPresent()) {
			var model = optionalModel.get();			
			repository.save(model);			
			return Optional.of(this.createResponse(model, moeda));
		}
		
		return Optional.empty();
	}

	public Optional<ReservaResponse> cancelar(UUID id, Optional<String> moeda) {

		var model = repository.findById(id);

		if (model.isPresent()) {
			var reserva = model.get();

			reserva.setSituacao(SituacaoEnum.CANCELADA);

			repository.save(reserva);

			return Optional.of(this.createResponse(reserva, moeda));
		}

		return Optional.empty();
	}

	private Optional<Reserva> createReserva(final UUID quartoId, final LocalDate checkin, final Optional<LocalDate> checkout,
			final Optional<String> moeda) {

		final LocalDate dataCheckout = checkout.orElse(checkin.plusDays(1));

		if (checkin.isAfter(dataCheckout) || checkin.isEqual(dataCheckout)) {
			return Optional.empty();
		}

		var disponivel = checarDisponibilidade(quartoId, checkin, dataCheckout);

		if (disponivel) {

			QuartoResponse quartoResponse = quartoProxy.findById(quartoId, null);

			Reserva model = new Reserva(quartoId, checkin, SituacaoEnum.CONFIRMADA);

			model.setDataCheckOut(dataCheckout);

			if (checkout.isPresent()) {
				long dias = ChronoUnit.DAYS.between(model.getDataCheckIn(), model.getDataCheckOut());
				model.setPreco(quartoResponse.valorDiaria().multiply(BigDecimal.valueOf(dias)));
			} else {
				model.setPreco(quartoResponse.valorDiaria());
			}

			return Optional.of(model);
		}

		return Optional.empty();
	}

	private ReservaResponse createResponse(Reserva model, Optional<String> moeda) {

		final BigDecimal valorCotacao = this.getValorCotacao(moeda);

		QuartoResponse quartoResponse = quartoProxy.findById(model.getQuartoId(), moeda.orElse(null));

		return new ReservaResponse(model.getId(), quartoResponse, model.getDataCheckIn(), model.getDataCheckOut(),
				model.getPreco().multiply(valorCotacao).setScale(2, RoundingMode.CEILING),
				model.getSituacao().getDescricao());
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

	private boolean checarDisponibilidade(UUID quartoId, LocalDate checkIn, LocalDate checkOut) {

		var listaReservas = repository.findAllByQuartoIdAndSituacao(quartoId, SituacaoEnum.CONFIRMADA);

		AtomicBoolean atomicBoolean = new AtomicBoolean(true);

		checkIn.datesUntil(checkOut.plusDays(1)).forEach(datacheck -> {

			for (Reserva reserva : listaReservas) {

				boolean existe = reserva.getDataCheckIn().datesUntil(reserva.getDataCheckOut().plusDays(1))
						.anyMatch(data -> data.equals(datacheck));

				if (existe) {
					atomicBoolean.set(false);
					break;
				}
			}
		});

		return atomicBoolean.get();
	}
}
