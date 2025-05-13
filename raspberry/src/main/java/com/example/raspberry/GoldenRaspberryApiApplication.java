package com.example.raspberry;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.raspberry.service.MovieService;

@SpringBootApplication
public class GoldenRaspberryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenRaspberryApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDatabase(MovieService movieService) {
		return args -> {
			movieService.loadMoviesFromCsv("movielist.csv");
		};
	}
}