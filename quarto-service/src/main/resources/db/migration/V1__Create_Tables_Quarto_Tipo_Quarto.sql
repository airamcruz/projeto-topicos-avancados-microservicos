-- quarto_service.tipo_quarto definition

CREATE TABLE `tipo_quarto` (
  `id` binary(16) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- quarto_service.quarto definition

CREATE TABLE `quarto` (
  `id` binary(16) NOT NULL,
  `capacidade` int NOT NULL,
  `valor_diaria` decimal(38,2) NOT NULL,
  `tipo_quarto_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK67cou8v3w87jg7p584wd5l12b` (`tipo_quarto_id`),
  CONSTRAINT `FK67cou8v3w87jg7p584wd5l12b` FOREIGN KEY (`tipo_quarto_id`) REFERENCES `tipo_quarto` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;