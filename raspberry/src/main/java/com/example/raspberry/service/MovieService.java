package com.example.raspberry.service;

import com.example.raspberry.Movie;
import com.example.raspberry.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void loadMoviesFromCsv(String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(fileName), StandardCharsets.UTF_8))) {

            List<Movie> movies = reader.lines()
                    .skip(1) // pula cabeçalho
                    .map(this::parseLine)
                    .collect(Collectors.toList());

            movieRepository.saveAll(movies);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Movie parseLine(String line) {
        String[] fields = line.split(";");

        int releaseYear = Integer.parseInt(fields[0].trim());

        String title = fields[1].trim();
        String studios = fields[2].trim();
        String producers = fields[3].trim();
        boolean winner = fields.length > 4 && fields[4].trim().equalsIgnoreCase("yes");

        return new Movie(releaseYear, title, studios, producers, winner);
    }
}
