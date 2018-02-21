package com.sbinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SbInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbInventoryApplication.class, args);
	}
}
