package com.example.demo;

import com.example.demo.entity.Movie;
import com.example.demo.repository.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

    private IMovieRepository movieRepository;

    @Autowired
    public DataLoader(IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        movieRepository.save(new Movie(1l, "Locke", 1, 6, 3,
                new Boolean[]{Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE}, LocalDateTime.of(2023,4,30,13,12,1)));
        movieRepository.save(new Movie(2l, "Suspiria", 2, 6, 3,
                new Boolean[]{Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE}, LocalDateTime.of(2022,4,29,13,12,1)));
        movieRepository.save(new Movie(3l, "Locke", 3, 6, 3,
                new Boolean[]{Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE}, LocalDateTime.of(2022,5,1,13,12,1)));
        movieRepository.save(new Movie(4l, "Bezdroża", 1, 6, 3,
                new Boolean[]{Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE}, LocalDateTime.of(2022,4,28,13,12,1)));
        movieRepository.save(new Movie(5l, "Suspiria", 2, 6, 3,
                new Boolean[]{Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE}, LocalDateTime.of(2022,4,28,13,12,1)));
        movieRepository.save(new Movie(6l, "Bezdroża", 3, 6, 3,
                new Boolean[]{Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,
                        Boolean.FALSE}, LocalDateTime.of(2022,4,28,13,12,1)));
    }
}
