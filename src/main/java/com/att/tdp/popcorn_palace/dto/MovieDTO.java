package com.att.tdp.popcorn_palace.dto;


import jakarta.validation.constraints.*;

public class MovieDTO {
    @NotBlank(message = "Name field cannot be empty or null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    final String title;

    @NotBlank(message = "genre field cannot be empty or null")
    final String genre;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must not exceed 10")
    final float rating;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 51420, message = "Duration cannot exceed 51,420 minutes")
    final int duration;

    @Min(value = 1888, message = "Release year must higher or equal to 1888 minutes")
    final int releaseYear;


    public MovieDTO(String title, String genre, float rating, int duration, int releaseYear) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.duration = duration;
        this.releaseYear = releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    public float getRating() {
        return rating;
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }
}
