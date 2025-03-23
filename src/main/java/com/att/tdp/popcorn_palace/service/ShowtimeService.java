package com.att.tdp.popcorn_palace.service;

import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.dto.ShowtimeUpdateDTO;
import com.att.tdp.popcorn_palace.entitys.Movie;
import com.att.tdp.popcorn_palace.entitys.Showtime;
import com.att.tdp.popcorn_palace.entitys.Theater;
import com.att.tdp.popcorn_palace.exception.ShowtimeConflictException;
import com.att.tdp.popcorn_palace.repository.ShowtimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieService movieService;
    private final TheaterService theaterService;

    public ShowtimeService(ShowtimeRepository showtimeRepository, MovieService movieService, TheaterService theaterService) {
        this.showtimeRepository = showtimeRepository;
        this.movieService = movieService;
        this.theaterService = theaterService;
    }

    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }


    public Showtime createShowtime(ShowtimeDTO showtimeDTO)
    {
        Theater theater = theaterService.getTheaterByName(showtimeDTO.getTheater());
        Movie movie = movieService.getMovieById(showtimeDTO.getMovieId());

        validateStartAndEndTime(showtimeDTO.getStartTime(), showtimeDTO.getEndTime());

        List<Showtime> collidingShowtimes = showtimeRepository.findCollidingShowtimes(
                theater.getId(),
                showtimeDTO.getStartTime(),
                showtimeDTO.getEndTime()
        );

        if (!collidingShowtimes.isEmpty()) {
            throw new ShowtimeConflictException(
                    theater.getId(),
                    showtimeDTO.getStartTime(),
                    showtimeDTO.getEndTime(),
                    collidingShowtimes);
        }


        Showtime showtime = new Showtime(showtimeDTO.getPrice(),showtimeDTO.getStartTime(), showtimeDTO.getEndTime(),movie,theater);
        return showtimeRepository.save(showtime);
    }

    public Showtime updateShowtime(Long id, ShowtimeUpdateDTO showtimeUpdateDTO) {
        Showtime showtime = getShowTimeById(id);

        OffsetDateTime startTime = showtimeUpdateDTO.getStartTime() != null ? showtimeUpdateDTO.getStartTime()  : showtime.getStartTime();
        OffsetDateTime endTime = showtimeUpdateDTO.getEndTime() != null ? showtimeUpdateDTO.getEndTime():showtime.getEndTime();

        Theater theater = showtimeUpdateDTO.getTheater() != null ? theaterService.getTheaterByName(showtimeUpdateDTO.getTheater()) : showtime.getTheater();

        validateStartAndEndTime(startTime, endTime);

        validateUpdateShowtimeConflict(showtime.getId(), startTime, endTime, theater.getId());


        updateShowtimeDetails(showtime, showtimeUpdateDTO, theater, startTime, endTime);

        return showtimeRepository.save(showtime);
    }


    private void validateUpdateShowtimeConflict(Long showtimeId, OffsetDateTime startTime, OffsetDateTime endTime, Long theaterId) {
        List<Showtime> collidingShowtimes = showtimeRepository.findCollidingShowtimes(theaterId, startTime, endTime);

        boolean isConflicting = collidingShowtimes.size() > 1 ||
                (collidingShowtimes.size() == 1 && (collidingShowtimes.get(0).getId() != showtimeId));

        if (isConflicting) {
            throw new ShowtimeConflictException(theaterId, startTime, endTime, collidingShowtimes);
        }
    }


    private void updateShowtimeDetails(Showtime showtime, ShowtimeUpdateDTO dto, Theater theater, OffsetDateTime startTime, OffsetDateTime endTime) {
        if (dto.getPrice() != null) {
            showtime.setPrice(dto.getPrice());
        }
        if (dto.getTheater() != null) {
            showtime.setTheater(theater);
        }
        if (dto.getMovieId() != null) {
            Movie movie = movieService.getMovieById(dto.getMovieId());
            showtime.setMovie(movie);
        }
        showtime.setStartTime(startTime);
        showtime.setEndTime(endTime);
    }

    public void deleteShowtime(Long id) {
        Showtime showtime =  getShowTimeById(id);
        showtimeRepository.delete(showtime);
    }

    public Showtime getShowTimeById(long id) {
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException( "showtime not found with ID: " + id));
    }

    private void validateStartAndEndTime(OffsetDateTime startTime, OffsetDateTime endTime) {
        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new IllegalArgumentException("Start time must be before and not equal to the end time.");
        }
    }



}
