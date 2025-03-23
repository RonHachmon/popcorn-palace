package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.MovieDTO;
import com.att.tdp.popcorn_palace.dto.UpdateMovieDTO;
import com.att.tdp.popcorn_palace.entitys.Movie;
import com.att.tdp.popcorn_palace.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    MovieController(MovieService movieService)
    {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    ResponseEntity<List<Movie>> getMovies()
    {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody @Valid MovieDTO movieDTO) {
        Movie createdMovie = movieService.createMovie(movieDTO);
        return ResponseEntity.ok(createdMovie);
    }

    @PostMapping("/update/{movieTitle}")
    public ResponseEntity<Void> updateMovie(@PathVariable String movieTitle, @RequestBody @Valid UpdateMovieDTO updateMovieDTO) {
         movieService.updateMovie(movieTitle,updateMovieDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{movieTitle}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable String movieTitle) {
        movieService.deleteMovieByTitle(movieTitle);
        return ResponseEntity.ok().build();
    }
}
