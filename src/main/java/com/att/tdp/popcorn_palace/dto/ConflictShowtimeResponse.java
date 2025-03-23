package com.att.tdp.popcorn_palace.dto;

import java.util.List;

public class ConflictShowtimeResponse {


    private final List<ShowtimeResponse> conflictShowtimes;

    public ConflictShowtimeResponse(List<ShowtimeResponse> conflictShowtimes) {
        this.conflictShowtimes = conflictShowtimes;
    }

    public List<ShowtimeResponse> getConflictShowtimes() {
        return conflictShowtimes;
    }
}
