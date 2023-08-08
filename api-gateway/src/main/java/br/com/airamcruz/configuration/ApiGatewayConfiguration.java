package br.com.airamcruz.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f
								.addRequestHeader("Hello", "World")
								.addRequestParameter("Hello", "World"))
						.uri("http://httpbin.org:80"))
				.route(p -> p.path("/quarto/**")
						.uri("lb://quarto-service"))
				.route(p -> p.path("/tipo-quarto/**")
						.uri("lb://quarto-service"))
				.route(p -> p.path("/reserva/**")
						.uri("lb://reserva-service"))
				.build();
	}
}
