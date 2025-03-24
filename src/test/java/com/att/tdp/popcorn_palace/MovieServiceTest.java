package com.att.tdp.popcorn_palace;


import com.att.tdp.popcorn_palace.dto.MovieDTO;
import com.att.tdp.popcorn_palace.dto.UpdateMovieDTO;
import com.att.tdp.popcorn_palace.entitys.Genre;
import com.att.tdp.popcorn_palace.entitys.Movie;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.service.MovieService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private MovieDTO movieDTO;

    @BeforeEach
    void SetUp(){
        movieDTO = new MovieDTO("Shrek", "comedy", 8.8f, 120, 2010);
    }

    @Test
    void testCreateMovie_Success() {

        Movie movie = new Movie("Shrek", 120, Genre.COMEDY, 8.8f, 2010);
        when(movieRepository.findByTitle("Shrek")).thenReturn(Optional.empty());
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie createdMovie = movieService.createMovie(movieDTO);

        assertNotNull(createdMovie);
        assertEquals("Shrek", createdMovie.getTitle());
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testCreateMovieDupName_Fail() {
        // Mock the first call: Movie does NOT exist initially
        when(movieRepository.findByTitle("Shrek")).thenReturn(Optional.empty());

        // Mock the first save operation
        Movie movie = new Movie("Shrek", 120, Genre.COMEDY, 8.8f, 2010);
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        // First movie creation - should succeed
        Movie createdMovie = movieService.createMovie(movieDTO);

        // Mock the second call: Now the movie exists
        when(movieRepository.findByTitle("Shrek")).thenReturn(Optional.of(movie));

        // Second movie creation - should fail with an exception
        IllegalArgumentException thrownException = assertThrows(
                IllegalArgumentException.class,
                () -> movieService.createMovie(movieDTO)
        );

        // Verify the error message
        assertEquals("Movie with title 'Shrek' already exists", thrownException.getMessage());

        // Ensure save() was only called once
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovie_Success() {

        Movie movie = new Movie("Shrek", 120, Genre.COMEDY, 8.8f, 2010);
        when(movieRepository.findByTitle("Shrek")).thenReturn(Optional.empty());
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie createdMovie = movieService.createMovie(movieDTO);

        assertEquals(createdMovie.getTitle(),"Shrek");
        assertEquals(createdMovie.getGenre(),Genre.COMEDY);
        assertEquals(createdMovie.getDuration(),120);
        assertEquals(createdMovie.getRating(),8.8f);
        assertEquals(createdMovie.getReleaseYear(),2010);

        when(movieRepository.findByTitle("Shrek")).thenReturn(Optional.of(movie));

        UpdateMovieDTO updateMovieDTO =new UpdateMovieDTO("Shrek","Horror",null,null,null);
        movieService.updateMovie("Shrek",updateMovieDTO);

        Movie updatedMovie = movieRepository.findByTitle("Shrek").get();

        assertEquals(updatedMovie.getTitle(),"Shrek");
        assertEquals(updatedMovie.getGenre(),Genre.HORROR);
        assertEquals(updatedMovie.getDuration(),120);
        assertEquals(updatedMovie.getRating(),8.8f);
        assertEquals(updatedMovie.getReleaseYear(),2010);


        verify(movieRepository, times(2)).save(any(Movie.class));
    }

    @Test
    void testUpdateMovie_Fail() {

        Movie movie = new Movie("Shrek", 120, Genre.COMEDY, 8.8f, 2010);
        when(movieRepository.findByTitle("Shrek")).thenReturn(Optional.empty());
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie createdMovie = movieService.createMovie(movieDTO);

        assertEquals(createdMovie.getTitle(),"Shrek");
        assertEquals(createdMovie.getGenre(),Genre.COMEDY);
        assertEquals(createdMovie.getDuration(),120);
        assertEquals(createdMovie.getRating(),8.8f);
        assertEquals(createdMovie.getReleaseYear(),2010);

        when(movieRepository.findByTitle("Shrek")).thenReturn(Optional.of(movie));

        UpdateMovieDTO updateMovieDTO =new UpdateMovieDTO("Shrek","Horror",null,null,null);

        given(movieRepository.findByTitle("blank")).willReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> movieService.updateMovie("blank",updateMovieDTO)
        );


        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void getAllMoviesReturnList() {

        given(movieRepository.findAll()).willReturn(java.util.List.of(new Movie(), new Movie()));

        List<Movie> result = movieService.getAllMovies();

        assertNotNull(result);
        assertEquals(result.size(),2);

        verify(movieRepository).findAll();
    }

    @Test
    void deleteMovieSuccess() {
        String movieTitle = "Matrix";
        Movie existingMovie = new Movie();
        existingMovie.setTitle(movieTitle);

        given(movieRepository.findByTitle(movieTitle)).willReturn(Optional.of(existingMovie));

        movieService.deleteMovieByTitle(movieTitle);
        verify(movieRepository).delete(existingMovie);
    }

}
