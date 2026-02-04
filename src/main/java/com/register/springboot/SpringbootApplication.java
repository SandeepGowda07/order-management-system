package com.register.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.register.springboot.repository.UserRepository;

/**
 * Main entry point for the Spring Boot application.
 * Configures JPA repositories and starts the application.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SpringbootApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
