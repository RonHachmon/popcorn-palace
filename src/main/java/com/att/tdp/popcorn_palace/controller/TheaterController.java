package com.att.tdp.popcorn_palace.controller;

import com.att.tdp.popcorn_palace.dto.MovieDTO;
import com.att.tdp.popcorn_palace.dto.TheaterDTO;
import com.att.tdp.popcorn_palace.entitys.Movie;
import com.att.tdp.popcorn_palace.entitys.Theater;
import com.att.tdp.popcorn_palace.service.MovieService;
import com.att.tdp.popcorn_palace.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    @Autowired
    TheaterController(TheaterService theaterService)
    {
        this.theaterService = theaterService;
    }

    @GetMapping("/all")
    ResponseEntity<List<Theater>> getTheaters()
    {
        List<Theater> theaters = theaterService.getAllTheaters();
        return ResponseEntity.ok(theaters);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> createTheater(@RequestBody @Valid TheaterDTO theaterDTO) {
        theaterService.createTheater(theaterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
