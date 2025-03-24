package com.att.tdp.popcorn_palace.dto.response;

import java.time.OffsetDateTime;

public class ShowtimeResponse {

    final private Long id;
    final private Long movieId;


    final private Float price;


    final private String theater;


    final private OffsetDateTime startTime;


    final private OffsetDateTime endTime;


    public ShowtimeResponse(Long id,Long movieId, Float price, String theater, OffsetDateTime startTime, OffsetDateTime endTime) {
        this.id = id;
        this.movieId = movieId;
        this.price = price;
        this.theater = theater;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
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
