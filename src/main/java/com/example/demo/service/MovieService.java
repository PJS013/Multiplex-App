package com.example.demo.service;

import com.example.demo.entity.Booked;
import com.example.demo.entity.BookedSeat;
import com.example.demo.entity.Movie;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MovieService {
    public Booked seatReservation(Movie movie, BookedSeat[] seatsNr) {

        BigDecimal ticketPrice = BigDecimal.ZERO;
        Boolean[] seats = movie.getSeats();
        for(BookedSeat nr : seatsNr) {
            if (seats[nr.getSeatNr()].equals(Boolean.FALSE)) {
                seats[nr.getSeatNr()] = Boolean.TRUE;
                movie.setSeats(seats);
                int x = nr.getSeatNr() / movie.getTotalColumns();

                if (nr.getSeatNr() == x * movie.getTotalColumns()) { //first seat in a row from the left
                    if (seats[nr.getSeatNr() + 1].equals(Boolean.FALSE) && seats[nr.getSeatNr() + 2].equals(Boolean.TRUE)) {
                        return null;
                    }
                } else if (nr.getSeatNr() == x * movie.getTotalColumns() + 1) { //second seat in a row from the left
                    if ((seats[nr.getSeatNr() + 1].equals(Boolean.FALSE) &&
                            seats[nr.getSeatNr() + 2].equals(Boolean.TRUE)) ||
                            seats[nr.getSeatNr() - 1].equals(Boolean.FALSE)) {
                        return null;
                    }
                } else if (nr.getSeatNr() == (x + 1) * movie.getTotalColumns() - 1) { //first seat in a row from the right
                    if (seats[nr.getSeatNr() - 1].equals(Boolean.FALSE) &&
                            seats[nr.getSeatNr() - 2].equals(Boolean.TRUE)) {
                        return null;
                    }
                } else if (nr.getSeatNr() == (x + 1) * movie.getTotalColumns() - 2) { //second seat in a row from the right
                    if ((seats[nr.getSeatNr() - 1].equals(Boolean.FALSE) &&
                            seats[nr.getSeatNr() - 2].equals(Boolean.TRUE)) ||
                            seats[nr.getSeatNr() + 1].equals(Boolean.FALSE)) {
                        return null;
                    }
                } else { //rest of seats
                    if ((seats[nr.getSeatNr() - 1].equals(Boolean.FALSE) &&
                            seats[nr.getSeatNr() - 2].equals(Boolean.TRUE)) ||
                            (seats[nr.getSeatNr() + 1].equals(Boolean.FALSE) &&
                                    seats[nr.getSeatNr() + 2].equals(Boolean.TRUE))) {
                        return null;
                    }
                }
            } else {
                return null;
            }
            ticketPrice = ticketPrice.add(nr.getPrice().price);
        }
//        booked.setMovie(movie);
//        booked.setPrice(ticketPrice);
//        booked.setExpirationDate(movie.getScreeningDateTime().minusMinutes(15));
        Booked booked = new Booked(movie, ticketPrice, movie.getScreeningDateTime().minusMinutes(15));
        //System.out.println(ticketPrice);
        return booked;
    }

    public List<Movie> returnMovieByDate(List<Movie> list, LocalDate date) {
        List<Movie> listAtDate = new ArrayList<Movie>();
        for(int i = 0; i<list.size(); i++) {
            if(list.get(i).getScreeningDateTime().toLocalDate().equals(date)) {
                listAtDate.add(list.get(i));
            }
        }
        if(listAtDate.isEmpty() || listAtDate.size() == 0) {
            return null;
        }

        Comparator<Movie> compare = new Comparator<Movie>() {
            @Override
            public int compare(Movie o1, Movie o2) {
                int cmp = o1.getScreeningDateTime().compareTo(o2.getScreeningDateTime());
                if(cmp != 0)
                    return cmp;
                return o1.getMovieName().compareTo(o2.getMovieName());
            }
        };
        Collections.sort(listAtDate, compare);
        return listAtDate;
    }
}
