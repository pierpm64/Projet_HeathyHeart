package fr.isika.cdi07.projet3demo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = "fr.isika.cdi07.projet3demo")
@EntityScan(basePackages = "fr.isika.cdi07.projet3demo.model")
@EnableJpaRepositories("fr.isika.cdi07.projet3demo.dao")
public class Projet3demoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Projet3demoApplication.class, args);
	}

}
