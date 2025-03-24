package com.att.tdp.popcorn_palace.configuration;


import com.att.tdp.popcorn_palace.entitys.Genre;
import com.att.tdp.popcorn_palace.entitys.Movie;
import com.att.tdp.popcorn_palace.entitys.Theater;
import com.att.tdp.popcorn_palace.entitys.Users;
import com.att.tdp.popcorn_palace.repository.MovieRepository;
import com.att.tdp.popcorn_palace.repository.TheaterRepository;
import com.att.tdp.popcorn_palace.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;
import java.util.UUID;

@Configuration
public class PopcornConfig {

    @Bean
    @Order(1)
    CommandLineRunner addingTheaters(TheaterRepository theaterRepository) {

        return args -> {
            if (theaterRepository.findAll().size() == 0) {
                Theater planet = new Theater("Planet", 30);
                Theater hotCinema = new Theater("Hot cinema", 15);
                Theater lev = new Theater("Lev", 5);
                theaterRepository.saveAll(List.of(planet, hotCinema, lev));
            }
        };
    }

    @Bean
    @Order(2)
    CommandLineRunner addingMovies(MovieRepository movieRepository) {

        return args -> {
            if (movieRepository.findAll().size() == 0) {
                Movie avengers = new Movie("Avengers: Endgame", 180, Genre.ACTION, 8.4f, 2019);
                Movie joker = new Movie("Joker", 122, Genre.DRAMA, 8.5f, 2019);
                Movie inception = new Movie("Inception", 148, Genre.SCI_FI, 8.8f, 2010);
                Movie interstellar = new Movie("Interstellar", 169, Genre.SCI_FI, 8.6f, 2014);
                Movie batman = new Movie("The Dark Knight", 152, Genre.ACTION, 9.0f, 2008);

                movieRepository.saveAll(List.of(avengers, joker, inception, interstellar, batman));
            }
        };
    }

    @Bean
    @Order(3)
    CommandLineRunner addingUsers(UsersRepository usersRepository) {


        return args -> {
            if (usersRepository.findAll().size() == 0) {
                Users ron = new Users();
                ron.setUsername("Ron");

                Users at = new Users();
                at.setUsername("At");

                Users and = new Users();
                and.setUsername("And");

                Users t = new Users();
                t.setUsername("T");
                usersRepository.saveAll(List.of(ron, at, and, t));
            }
        };
    }
}
