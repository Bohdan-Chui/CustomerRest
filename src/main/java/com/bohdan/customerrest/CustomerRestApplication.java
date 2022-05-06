package com.bohdan.customerrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CustomerRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerRestApplication.class, args);
	}

}
