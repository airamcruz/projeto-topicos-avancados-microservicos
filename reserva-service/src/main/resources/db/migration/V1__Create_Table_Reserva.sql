-- reserva_service.reserva definition

CREATE TABLE `reserva` (
  `id` binary(16) NOT NULL,
  `data_check_in` date NOT NULL,
  `data_check_out` date NOT NULL,
  `preco` decimal(38,2) NOT NULL,
  `quarto_id` binary(16) NOT NULL,
  `situacao` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;