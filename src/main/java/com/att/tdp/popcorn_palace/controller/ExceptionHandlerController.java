package com.att.tdp.popcorn_palace.controller;



import com.att.tdp.popcorn_palace.entitys.Showtime;
import com.att.tdp.popcorn_palace.exception.GenreNotFoundException;
import com.att.tdp.popcorn_palace.exception.SeatAlreadyBookedException;
import com.att.tdp.popcorn_palace.exception.SeatOutOfRangeException;
import com.att.tdp.popcorn_palace.exception.ShowtimeConflictException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Invalid arguments");
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {
        return createErrorResponse("Invalid request", ex.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GenreNotFoundException.class)
    public Map<String, String> handleGenreNotFound(GenreNotFoundException ex) {
        return createErrorResponse("Genre not found", ex.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SeatOutOfRangeException.class)
    public Map<String, String> handleSeatOutOfRange(SeatOutOfRangeException ex) {
        return createErrorResponse("Invalid seat number", ex.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SeatAlreadyBookedException.class)
    public Map<String, String> handleSeatAlreadyBooked(SeatAlreadyBookedException ex) {
        return createErrorResponse("Seat already booked", ex.getMessage());
    }


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ShowtimeConflictException.class)
    public Map<String, List<Showtime>> handleShowtimeConflict(ShowtimeConflictException ex) {

        Map<String, List<Showtime>> errorMap = new HashMap<>();
        errorMap.put("Showtime conflict", ex.getCollidingShowtimes());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleEntityNotFound(EntityNotFoundException ex) {
        return createErrorResponse("Entity not found", ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> generalException(RuntimeException ex) {
        return createErrorResponse("errorMessage", ex.getMessage());
    }


    private Map<String, String> createErrorResponse(String error, String message) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", error);
        errorMap.put("message", message);
        return errorMap;
    }

}
