package com.att.tdp.popcorn_palace.repository;


import com.att.tdp.popcorn_palace.entitys.Movie;
import com.att.tdp.popcorn_palace.entitys.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    Optional<Movie> findByTitle(String title);
}
