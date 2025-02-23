package com.jve.proyecto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.jve.proyecto.repository")
@ComponentScan(basePackages = "com.jve.proyecto")
public class ProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoApplication.class, args);
	}

}
