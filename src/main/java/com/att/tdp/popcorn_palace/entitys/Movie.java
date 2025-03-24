package com.att.tdp.popcorn_palace.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;



@Entity(name = "movie")
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "movie_name", unique = true, nullable = false,length = 60)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Column(name = "rating",nullable = false)
    private Float rating ;

    @Column(name = "duration",nullable = false)
    private Integer duration;


    @Column(name = "releaseYear",nullable = false)
    private Integer releaseYear ;

    @JsonIgnore
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Showtime> showtimes=new HashSet<>();

    public Movie() {

    }

    public Movie(String title, Integer duration, Genre genre, Float rating, Integer releaseYear) {
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public long getId() {
        return id;
    }


    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }


    public Genre getGenre() {
        return genre;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public Integer getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }


}
