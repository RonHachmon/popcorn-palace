package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.MovieDTO;
import com.att.tdp.popcorn_palace.dto.UpdateMovieDTO;
import com.att.tdp.popcorn_palace.entitys.Genre;
import com.att.tdp.popcorn_palace.entitys.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie createMovie(MovieDTO movieDTO) {
        Genre genre =Genre.findByString(movieDTO.getGenre());

        if(isMovieWithTitleAlreadyExist(movieDTO.getTitle()))
        {
            throw new IllegalArgumentException(String.format("Movie with title '%s' already exists", movieDTO.getTitle()));
        }

        Movie movie =new Movie(movieDTO.getTitle(),movieDTO.getDuration(),genre,
                                movieDTO.getRating(), movieDTO.getReleaseYear());
        return movieRepository.save(movie);
    }

    public void updateMovie(String title, UpdateMovieDTO updateMovieDTO) {

        Movie movie = getMovieByTitle(title);

        updateMovie(updateMovieDTO, movie);

        movieRepository.save(movie);
    }

    private void updateMovie(UpdateMovieDTO updateMovieDTO, Movie movie) {
        if(updateMovieDTO.getGenre() != null){
            Genre genre =Genre.findByString(updateMovieDTO.getGenre());
            movie.setGenre(genre);
        }

        if(updateMovieDTO.getTitle() != null) {
            if(!updateMovieDTO.getTitle().equals(movie.getTitle()))
            {
                if(isMovieWithTitleAlreadyExist(updateMovieDTO.getTitle()))
                {
                    throw new IllegalArgumentException(String.format("Movie with title '%s' already exists", updateMovieDTO.getTitle()));
                }

                movie.setTitle(updateMovieDTO.getTitle());
            }
        }

        if(updateMovieDTO.getDuration() != null){
            movie.setDuration(updateMovieDTO.getDuration());
        }

        if(updateMovieDTO.getRating() != null){
            movie.setRating(updateMovieDTO.getRating());
        }

        if(updateMovieDTO.getReleaseYear() != null){
            movie.setReleaseYear(updateMovieDTO.getReleaseYear());
        }
    }

    public void deleteMovieByTitle(String title) {
        Movie movie =  getMovieByTitle(title);
        movieRepository.delete(movie);
    }


    public Movie getMovieById(long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException( "movie not found with Id: " + id));
    }

    private Movie getMovieByTitle(String title) {
        return movieRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException( "movie not found with Title: " + title));

    }

    private boolean isMovieWithTitleAlreadyExist(String movieTitle) {
        return movieRepository.findByTitle(movieTitle).isPresent();
    }

}
