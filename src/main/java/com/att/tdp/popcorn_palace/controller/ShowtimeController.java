package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.ShowtimeDTO;
import com.att.tdp.popcorn_palace.dto.ShowtimeResponse;
import com.att.tdp.popcorn_palace.dto.ShowtimeUpdateDTO;
import com.att.tdp.popcorn_palace.entitys.Showtime;
import com.att.tdp.popcorn_palace.service.ShowtimeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/showtimes")
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @Autowired
    public ShowtimeController(ShowtimeService showtimeService) {
        this.showtimeService = showtimeService;
    }

    @GetMapping
    public List<Showtime> getAllShowtimes() {
        return showtimeService.getAllShowtimes();
    }

    @GetMapping("/{showtimeId}")
    public ResponseEntity<ShowtimeResponse> getShowtimeById(@PathVariable Long showtimeId) {
        Showtime showtime = showtimeService.getShowTimeById(showtimeId);

        return ResponseEntity.ok(buildShowtimeResponse(showtime));
    }

    @PostMapping
    public ResponseEntity<ShowtimeResponse> createShowtime(@RequestBody @Valid ShowtimeDTO showtimeDTO) {
        Showtime showtime = showtimeService.createShowtime(showtimeDTO);

        return ResponseEntity.ok(buildShowtimeResponse(showtime));
    }


    @PostMapping("/update/{showtimeId}")
    public ResponseEntity<Void> updateShowtime(@PathVariable Long showtimeId, @RequestBody @Valid ShowtimeUpdateDTO showtimeUpdateDTO) {
        showtimeService.updateShowtime(showtimeId, showtimeUpdateDTO);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showtimeId) {
        showtimeService.deleteShowtime(showtimeId);
        return ResponseEntity.ok().build();
    }
    private ShowtimeResponse buildShowtimeResponse(Showtime showtime)
    {
        return  new ShowtimeResponse(showtime.getId(),
                    showtime.getMovie().getId(),
                    showtime.getPrice(),
                    showtime.getTheater().getName(),
                    showtime.getStartTime(),
                    showtime.getStartTime());
    }
}

