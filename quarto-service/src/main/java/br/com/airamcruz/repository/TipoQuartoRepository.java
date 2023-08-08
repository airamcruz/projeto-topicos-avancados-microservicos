package br.com.airamcruz.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.airamcruz.model.TipoQuarto;

public interface TipoQuartoRepository extends JpaRepository<TipoQuarto, UUID> {
}
