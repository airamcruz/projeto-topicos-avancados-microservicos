package br.com.airamcruz.response;

import java.math.BigDecimal;

public record CotacaoResponse(String code, String codein, BigDecimal high, BigDecimal low, BigDecimal varBid,
		BigDecimal pctChange, BigDecimal bid, BigDecimal ask, BigDecimal timestamp, String create_date) {

}
