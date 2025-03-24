package com.att.tdp.popcorn_palace.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.time.OffsetDateTime;
import java.util.List;


@Entity(name = "showtime")
@Table(name = "showtime")
public class Showtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "start_time", nullable = false)
    private OffsetDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private OffsetDateTime endTime;

    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "movie_id")
    private Movie movie;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "theater_id")
    private Theater theater;



    public Showtime() {

    }

    public Showtime(float price, OffsetDateTime start, OffsetDateTime end, Movie movie, Theater theater) {
        this.price = price;
        this.startTime = start;
        this.endTime = end;
        this.movie = movie;
        this.theater = theater;
    }

    public long getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public OffsetDateTime getStartTime() {
        return startTime;
    }

    public OffsetDateTime getEndTime() {
        return endTime;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public Movie getMovie() {
        return movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }
}
