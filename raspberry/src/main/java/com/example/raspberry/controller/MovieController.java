package com.example.raspberry.controller;

import com.example.raspberry.Movie;
import com.example.raspberry.repository.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producers")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/intervals")
    public Map<String, List<AwardIntervalDTO>> getAwardIntervals() {
        // Pega todos os filmes vencedores
        List<Movie> winners = movieRepository.findAll().stream()
                .filter(Movie::isWinner)
                .collect(Collectors.toList());

        // Mapa para armazenar os anos em que os produtores ganharam
        Map<String, List<Integer>> producerWins = new HashMap<>();

        for (Movie movie : winners) {
            String[] producers = movie.getProducers().split(",| and ");
            for (String producer : producers) {
                producer = producer.trim();
                if (!producer.isEmpty()) {
                    producerWins
                            .computeIfAbsent(producer, k -> new ArrayList<>())
                            .add(movie.getReleaseYear());
                }
            }
        }

        // Lista para os intervalos de prêmios
        List<AwardIntervalDTO> intervals = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            List<Integer> years = entry.getValue();
            if (years.size() > 1) {
                Collections.sort(years);
                for (int i = 1; i < years.size(); i++) {
                    int interval = years.get(i) - years.get(i - 1);
                    intervals.add(new AwardIntervalDTO(entry.getKey(), interval, years.get(i - 1), years.get(i)));
                }
            }
        }

        // Encontrar o produtor com o maior intervalo entre prêmios
        Optional<AwardIntervalDTO> maxInterval = intervals.stream()
                .max(Comparator.comparingInt(AwardIntervalDTO::getInterval));

        // Encontrar o produtor com o menor intervalo entre prêmios
        Optional<AwardIntervalDTO> minInterval = intervals.stream()
                .min(Comparator.comparingInt(AwardIntervalDTO::getInterval));

        // Criando o resultado final para o maior e menor intervalo
        Map<String, List<AwardIntervalDTO>> result = new HashMap<>();

        maxInterval.ifPresent(dto -> {
            result.put("max", List.of(dto));
        });

        minInterval.ifPresent(dto -> {
            result.put("min", List.of(dto));
        });

        return result;
    }

    public static class AwardIntervalDTO {
        private String producer;
        private int interval;
        private int previousWin;
        private int followingWin;

        public AwardIntervalDTO(String producer, int interval, int previousWin, int followingWin) {
            this.producer = producer;
            this.interval = interval;
            this.previousWin = previousWin;
            this.followingWin = followingWin;
        }

        public String getProducer() {
            return producer;
        }

        public int getInterval() {
            return interval;
        }

        public int getPreviousWin() {
            return previousWin;
        }

        public int getFollowingWin() {
            return followingWin;
        }
    }
}
