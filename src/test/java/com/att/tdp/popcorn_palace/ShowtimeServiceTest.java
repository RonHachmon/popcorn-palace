package com.att.tdp.popcorn_palace;


import com.att.tdp.popcorn_palace.dto.MovieDTO;
import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.entitys.Movie;
import com.att.tdp.popcorn_palace.entitys.Showtime;
import com.att.tdp.popcorn_palace.entitys.Theater;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import com.att.tdp.popcorn_palace.repository.TheaterRepository;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import com.att.tdp.popcorn_palace.service.TheaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

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

    private ShowtimeDTO showtimeDTO;
    private Showtime showtime;
    private Movie movie;
    private Theater theater;

}
