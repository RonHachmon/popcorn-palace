package com.att.tdp.popcorn_palace.exception;

public class SeatOutOfRangeException extends RuntimeException {
    public SeatOutOfRangeException(String message) {
        super(message);
    }
}
