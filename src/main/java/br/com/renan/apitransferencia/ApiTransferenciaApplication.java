package br.com.renan.apitransferencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("br.com.renan.apitransferencia.repository")
@EntityScan("br.com.renan.apitransferencia.model")
@SpringBootApplication
public class ApiTransferenciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTransferenciaApplication.class, args);
	}

}
