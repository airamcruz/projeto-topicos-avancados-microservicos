package br.com.airamcruz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class QuartoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuartoServiceApplication.class, args);
	}

}
