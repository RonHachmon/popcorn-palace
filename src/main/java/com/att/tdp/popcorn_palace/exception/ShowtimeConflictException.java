package com.att.tdp.popcorn_palace.exception;

import com.att.tdp.popcorn_palace.entitys.Showtime;

import java.time.OffsetDateTime;
import java.util.List;

public class ShowtimeConflictException extends RuntimeException {
    private final List<Showtime> collidingShowtimes;
    public ShowtimeConflictException(Long theaterId, OffsetDateTime startTime, OffsetDateTime endTime, List<Showtime> collidingShowtimes) {

        super(buildMessage(theaterId, startTime, endTime, collidingShowtimes));
        this.collidingShowtimes = collidingShowtimes;

    }

    private static String buildMessage(Long theaterId, OffsetDateTime startTime, OffsetDateTime endTime, List<Showtime> collidingShowtimes) {
        StringBuilder message = new StringBuilder();
        message.append("Showtime conflict detected for theater ID: ")
                .append(theaterId)
                .append(", Start: ").append(startTime)
                .append(", End: ").append(endTime)
                .append(". Conflicting showtimes: ");

        for (Showtime showtime : collidingShowtimes) {
            message.append("\n - Showtime ID: ").append(showtime.getId())
                    .append(", Start: ").append(showtime.getStartTime())
                    .append(", End: ").append(showtime.getEndTime());
        }

        return message.toString();
    }

    public List<Showtime> getCollidingShowtimes() {
        return collidingShowtimes;
    }
}
