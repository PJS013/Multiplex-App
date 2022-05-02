package com.example.demo.controller;

import com.example.demo.entity.Booked;
import com.example.demo.entity.BookedSeat;
import com.example.demo.entity.Movie;
import com.example.demo.repository.IMovieRepository;
import com.example.demo.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@RestController("movies")
@AllArgsConstructor
public class MovieController {

    final IMovieRepository movieRepository;

    final MovieService movieService;


    public static Boolean nameVerification(String name) {
        String regex = "[A-ZĄĆĘŁŃÓŻŹ][a-ząćęłńóżź][a-ząćęłńóżź]+ " +
                "[A-ZĄĆĘŁŃÓŻŹ][a-ząćęłńóżź][a-ząćęłńóżź]+(-[A-ZĄĆĘŁŃÓŻŹ][a-ząćęłńóżź][a-ząćęłńóżź]+)?";
        Pattern pattern = Pattern.compile(regex);
        if(pattern.matcher(name).matches()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Boolean requestValidation(String name, Movie movie) {
        if(movie.getScreeningDateTime().isAfter(LocalDateTime.now().plusMinutes(15))) {
            if (nameVerification(name).equals(Boolean.TRUE)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }
        return Boolean.FALSE;
    }


    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody Movie movie) {
        try {
            return new ResponseEntity<>(movieRepository.save(movie), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Movie> enterNewMovie(@RequestBody Movie movie) {
        try {
            Boolean[] seats = new Boolean[movie.getTotalColumns() * movie.getTotalRows()];
            Arrays.fill(seats, Boolean.FALSE);
            return new ResponseEntity<>(movieRepository.save(new Movie(movie.getId(),
                    movie.getMovieName(),
                    movie.getRoomNr(),
                    movie.getTotalRows(),
                    movie.getTotalColumns(),
                    seats,
                    movie.getScreeningDateTime())), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        try {
            List<Movie> list = movieRepository.findAll();
            if(list.isEmpty()) {
                return new ResponseEntity<List<Movie>>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Movie>>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/available/{id}")
    public ResponseEntity<List<Integer>> getAvailableSeats(@PathVariable Long id) {
        try {
            Optional<Movie> movie = movieRepository.findById(id);
            if(movie.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<Integer> availableSeats = new ArrayList<Integer>();
            Boolean[] seats = movie.get().getSeats();
            for(int seatNr = 0; seatNr < seats.length; seatNr++) {
                if(seats[seatNr].equals(Boolean.FALSE))
                    availableSeats.add(seatNr);
            }
            if(availableSeats.isEmpty())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Integer>>(availableSeats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{dateString}")
    public ResponseEntity<List<Movie>> getMoviesByDate(@PathVariable String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString);
            List<Movie> list = movieRepository.findAll();
            if(list.isEmpty()) {
                return new ResponseEntity<List<Movie>>(HttpStatus.NO_CONTENT);
            }
            List<Movie> listAtDate = movieService.returnMovieByDate(list, date);
            if(listAtDate==null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<List<Movie>>(listAtDate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
        try {
            if(movie.getSeats().length == movie.getTotalColumns() * movie.getTotalRows())
                return new ResponseEntity<Movie>(movieRepository.save(movie), HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/seats/{id}/{name}")
    public ResponseEntity<Booked> updateSeats(@PathVariable Long id, @RequestBody BookedSeat[] seatsNr,
                                              @PathVariable String name) {
        try {
            Movie movie = movieRepository.getById(id);

            if(requestValidation(name, movie).equals(Boolean.TRUE)) {
                Booked booked = movieService.seatReservation(movie, seatsNr);
                if(booked.getMovie()==null)
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                movieRepository.save(booked.getMovie());
                return new ResponseEntity<>(booked, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        try {
            Optional<Movie> movie = movieRepository.findById(id);
            movie.ifPresent(movieRepository::delete);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
