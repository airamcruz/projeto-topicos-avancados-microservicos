package br.com.airamcruz.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.airamcruz.enums.SituacaoEnum;
import br.com.airamcruz.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, UUID> {
	
	public List<Reserva> findAllByQuartoId(UUID quartoId, Sort sort);
	
	public List<Reserva> findAllByQuartoIdAndSituacao(UUID quartoId, SituacaoEnum situacao);
	
}
