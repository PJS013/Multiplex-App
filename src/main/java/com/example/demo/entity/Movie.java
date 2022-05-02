package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name="tbl_movie")
@Entity
@Setter
@Getter

@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String movieName;
    private Integer roomNr;
    private Integer totalRows;
    private Integer totalColumns;
    private Boolean[] seats;
    private LocalDateTime screeningDateTime;


    public Movie() {
    }

    public Movie(Long id, String movieName, Integer roomNr, Integer totalRows, Integer totalColumns, Boolean[] seats,
                 LocalDateTime screeningDateTime) {
        this.id = id;
        this.movieName = movieName;
        this.roomNr = roomNr;
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.seats = seats;
        this.screeningDateTime = screeningDateTime;
    }
}
