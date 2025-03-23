package com.att.tdp.popcorn_palace.dto;

import jakarta.validation.constraints.*;
import java.time.OffsetDateTime;

public class ShowtimeDTO {

    @NotNull(message = "Movie ID is required")
    @Positive(message = "Movie ID must be a positive number")
    final private Long movieId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    final private Float price;

    @NotBlank(message = "Theater name is required")
    @Size(max = 100, message = "Theater name must be at most 100 characters")
    final private String theater;

    @NotNull(message = "Start time is required")
    final private OffsetDateTime startTime;

    @NotNull(message = "End time is required")
    final private OffsetDateTime endTime;


    public ShowtimeDTO(Long movieId, Float price, String theater, OffsetDateTime startTime, OffsetDateTime endTime) {
        this.movieId = movieId;
        this.price = price;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getMovieId() {
        return movieId;
    }


    public Float getPrice() {
        return price;
    }



    public String getTheater() {
        return theater;
    }


    public OffsetDateTime getStartTime() {
        return startTime;
    }


    public OffsetDateTime getEndTime() {
        return endTime;
    }


}
