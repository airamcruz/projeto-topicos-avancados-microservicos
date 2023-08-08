package br.com.airamcruz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReservaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservaServiceApplication.class, args);
	}

}
