package com.sims;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SIMS {

	private static final Logger LOGGER = Logger.getLogger(SIMS.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SIMS.class, args);
	}
}
