package com.att.tdp.popcorn_palace;



import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.dto.ShowtimeUpdateDTO;
import com.att.tdp.popcorn_palace.entitys.*;
import com.att.tdp.popcorn_palace.exception.ShowtimeConflictException;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import com.att.tdp.popcorn_palace.service.TheaterService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ShowtimeServiceTest {


    @Mock
    private ShowtimeRepository showtimeRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private TheaterService theaterService;

    @InjectMocks
    private ShowtimeService showtimeService;


    private Theater theater;
    private Movie movie;

    @BeforeEach
    void setUp() {
        theater = new Theater("Lev", 20);
        movie = new Movie("Shrek",120,Genre.COMEDY, 5.5F,2002);

    }


    @Test
    void testCreateShowtime_Success() {

        Showtime showtime = new Showtime();
        showtime.setTheater(theater);

        OffsetDateTime start = OffsetDateTime.now();
        OffsetDateTime end = start.plusHours(2);
        ShowtimeDTO showtimeDTO =new ShowtimeDTO(1L, 40F,"Lev",start,end);

        when(theaterService.getTheaterByName("Lev")).thenReturn(theater);
        when(movieService.getMovieById(1L)).thenReturn(movie);

        when(showtimeRepository.save(any(Showtime.class))).thenReturn(showtime);

        Showtime createdShowtime = showtimeService.createShowtime(showtimeDTO);

        assertNotNull(createdShowtime);
        verify(showtimeRepository, times(1)).save(any(Showtime.class));

    }

    @Test
    void testUpdateShowtime_Success() {

        Showtime showtime = new Showtime();
        showtime.setTheater(theater);
        showtime.setPrice(10F);
        OffsetDateTime start = OffsetDateTime.now();
        OffsetDateTime end = start.plusHours(2);

        showtime.setStartTime(start);
        showtime.setEndTime(end);

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(theaterService.getTheaterByName("Lev")).thenReturn(theater);
        when(movieService.getMovieById(1L)).thenReturn(movie);
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(showtime);

        ShowtimeUpdateDTO showtimeUpdateDTO =new ShowtimeUpdateDTO(1L, 20F,"Lev",start,end);

        Showtime updateShowtime = showtimeService.updateShowtime(1L,showtimeUpdateDTO);
        assertNotNull(updateShowtime);
        assertEquals(updateShowtime.getPrice(),20F);


        verify(showtimeRepository, times(1)).save(any(Showtime.class));

    }

    @Test
    void testUpdateShowtimePartialArgument_Success() {

        Showtime showtime = new Showtime();
        showtime.setTheater(theater);
        showtime.setPrice(10F);
        OffsetDateTime start = OffsetDateTime.now();
        OffsetDateTime end = start.plusHours(2);

        showtime.setStartTime(start);
        showtime.setEndTime(end);

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));
        when(theaterService.getTheaterByName("Lev")).thenReturn(theater);
        when(movieService.getMovieById(1L)).thenReturn(movie);
        when(showtimeRepository.save(any(Showtime.class))).thenReturn(showtime);

        end.plusHours(2);

        ShowtimeUpdateDTO showtimeUpdateDTO =new ShowtimeUpdateDTO(1L, null,"Lev",null,end);

        Showtime updateShowtime = showtimeService.updateShowtime(1L,showtimeUpdateDTO);

        assertNotNull(updateShowtime);
        assertEquals(updateShowtime.getPrice(),10F);
        assertEquals(updateShowtime.getStartTime(),start);
        assertEquals(updateShowtime.getEndTime(), end);


        verify(showtimeRepository, times(1)).save(any(Showtime.class));

    }

    @Test
    void testCreateShowtimeStartEqualEnd_Fail() {

        Showtime showtime = new Showtime();
        showtime.setTheater(theater);

        OffsetDateTime start = OffsetDateTime.now();
        OffsetDateTime end = start;

        ShowtimeDTO showtimeDTO =new ShowtimeDTO(1L, 40F,"Lev",start,end);

        when(theaterService.getTheaterByName("Lev")).thenReturn(theater);
        when(movieService.getMovieById(1L)).thenReturn(movie);

        assertThrows(
                IllegalArgumentException.class,
                () -> showtimeService.createShowtime(showtimeDTO)
        );


        verify(showtimeRepository, never()).save(any(Showtime.class));
    }

    @Test
    void testCreateShowtimeStartBiggerEnd_Fail() {

        Showtime showtime = new Showtime();
        showtime.setTheater(theater);

        OffsetDateTime start = OffsetDateTime.now().plusHours(1);
        OffsetDateTime end = OffsetDateTime.now();

        ShowtimeDTO showtimeDTO =new ShowtimeDTO(1L, 40F,"Lev",start,end);

        when(theaterService.getTheaterByName("Lev")).thenReturn(theater);
        when(movieService.getMovieById(1L)).thenReturn(movie);

        assertThrows(
                IllegalArgumentException.class,
                () -> showtimeService.createShowtime(showtimeDTO)
        );


        verify(showtimeRepository, never()).save(any(Showtime.class));
    }

    @Test
    void testCreateShowtimeConflict_Fail() {

        Showtime showtime = new Showtime();
        showtime.setTheater(theater);

        OffsetDateTime start = OffsetDateTime.now();
        OffsetDateTime end = OffsetDateTime.now().plusHours(1);

        ShowtimeDTO showtimeDTO =new ShowtimeDTO(1L, 40F,"Lev",start,end);

        when(theaterService.getTheaterByName("Lev")).thenReturn(theater);

        when(movieService.getMovieById(1L)).thenReturn(movie);
        when(showtimeRepository.findCollidingShowtimes(0L,showtimeDTO.getStartTime(),showtimeDTO.getEndTime()))
        .thenReturn(List.of(new Showtime(),new Showtime()));

        assertThrows(
                ShowtimeConflictException.class,
                () -> showtimeService.createShowtime(showtimeDTO)
        );


        verify(showtimeRepository, never()).save(any(Showtime.class));
    }

    @Test
    void deleteShowtime_Success() {
        Showtime existingShowtime = new Showtime();


        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(existingShowtime));

        showtimeService.deleteShowtime(1L);
        verify(showtimeRepository).delete(existingShowtime);
    }

    @Test
    void testGetShowTimeById_Success() {
        Showtime showtime = new Showtime();

        when(showtimeRepository.findById(1L)).thenReturn(Optional.of(showtime));

        Showtime result = showtimeService.getShowTimeById(1L);

        assertNotNull(result);

    }

    @Test
    void testGetShowTimeById_NotFound() {
        when(showtimeRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> showtimeService.getShowTimeById(1L)
        );
    }



}
