package com.att.tdp.popcorn_palace.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;



public class UpdateMovieDTO {

    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    final String title;

    final String genre;

    @Min(value = 1, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating must not exceed 10")
    final Float rating;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 51420, message = "Duration cannot exceed 51,420 minutes")
    final Integer duration;


    @Min(value = 1888, message = "releaseYear must higher or equal to 1888 minutes")
    final Integer releaseYear;


    public UpdateMovieDTO(String title, String genre, Float rating, Integer duration, Integer releaseYear) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public Float getRating() {
        return rating;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }
}
