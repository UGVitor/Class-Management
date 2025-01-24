package com.kvy.demogerenciamentoaulas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoGerenciamentoAulasApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoGerenciamentoAulasApplication.class, args);
	}

}
