package com.sbinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class SbInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbInventoryApplication.class, args);
		System.out.println((7 & 4)==4);
		System.out.println((8 & 4)==4);
		System.out.println((9 & 4)==4);
		System.out.println((15 & 4));
		System.out.println((7 & 4));
		System.out.println((3 & 4)==4);
		System.out.println((1 & 4)==4);
		System.out.println((1 | 2 | 4 | 8 ));
	}
}
