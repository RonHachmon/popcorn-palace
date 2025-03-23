package com.att.tdp.popcorn_palace.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class BookingDTO {

    @NotNull(message = "Showtime ID is required")
    @Min(value = 1, message = "Showtime ID must be a positive number")
    private final Long showtimeId;

    @NotNull(message = "Seat number is required")
    @Min(value = 1, message = "Seat number must be at least 1")
    private final Integer seatNumber;

    @NotNull(message = "User ID is required")
    private final UUID userId;

    public BookingDTO(Long showtimeId, Integer seatNumber, UUID userId) {
        this.showtimeId = showtimeId;
        this.seatNumber = seatNumber;
        this.userId = userId;
    }

    public Long getShowtimeId() { return showtimeId; }
    public Integer getSeatNumber() { return seatNumber; }
    public UUID getUserId() { return userId; }
}
