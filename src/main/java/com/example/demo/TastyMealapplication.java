package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="Controle")
public class TastyMealapplication {

	public static void main(String[] args) {
		SpringApplication.run(TastyMealapplication.class, args);
	}

}
