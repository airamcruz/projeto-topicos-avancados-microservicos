﻿INSERT INTO quarto_service.tipo_quarto (id,descricao) VALUES
	 (0x2F024009A1AE4EFDA89D03141D828B8C,'Apartamento'),
	 (0x4248666E37EB429FB7F4750AF342127A,'Quarto Casal'),
	 (0x6F4B8DF1704648A598E63F55294EAEB9,'Quarto de Solteiro Premium'),
	 (0x77301070A160448595E6AE1BAE590B08,'Quarto de Solteiro Premium 2'),
	 (0x7C2C02D6F2524612A101A23EFA9A001E,'Dormitório'),
	 (0xC74415B50A2145A7A4ACB26518934659,'Quarto de Solteiro'),
	 (0xD6B89804E5CD429A85169D22FCADA999,'Duplo Solteiro');

INSERT INTO quarto_service.quarto (id,capacidade,valor_diaria,tipo_quarto_id) VALUES
	 (0x123EF1F699974667BDAD25FD0ED9DD48,4,135.45,0x2F024009A1AE4EFDA89D03141D828B8C),
	 (0x1A20839660C047B88BB550CEA9D96DA8,2,62.50,0x4248666E37EB429FB7F4750AF342127A),
	 (0x1A892344EAD84776B72337E056CF04C8,2,62.00,0x2F024009A1AE4EFDA89D03141D828B8C),
	 (0x2875CB2FBF074CAD8F80BEDE063B02D3,1,25.50,0xC74415B50A2145A7A4ACB26518934659),
	 (0x8A92D5C12AB741FB873DED3CE69081C7,2,40.00,0xD6B89804E5CD429A85169D22FCADA999),
	 (0xD611187E722741839986850407ECA0CE,1,12.75,0x7C2C02D6F2524612A101A23EFA9A001E),
	 (0xF2C2AF19C67B4E7C98E8389EEC7E50CE,2,81.50,0x2F024009A1AE4EFDA89D03141D828B8C);
