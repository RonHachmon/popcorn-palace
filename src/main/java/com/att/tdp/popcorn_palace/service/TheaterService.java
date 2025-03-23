package com.att.tdp.popcorn_palace.service;



import com.att.tdp.popcorn_palace.dto.TheaterDTO;


import com.att.tdp.popcorn_palace.entitys.Theater;

import com.att.tdp.popcorn_palace.repository.TheaterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    @Autowired
    public TheaterService(TheaterRepository movieRepository){
        this.theaterRepository = movieRepository;
    }

    public Theater createTheater(TheaterDTO theaterDTO) {
        Theater newTheater = new Theater(theaterDTO.getName(), theaterDTO.getTotalSits());
        return theaterRepository.save(newTheater);
    }

    public List<Theater> getAllTheaters(){

        return theaterRepository.findAll();
    }

    public Theater getTheaterByName(String name) {
        return theaterRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException( "theater not found with name: " + name));
    }

}
