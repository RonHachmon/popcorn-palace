package com.att.tdp.popcorn_palace.repository;

import com.att.tdp.popcorn_palace.entitys.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    List<Showtime> findByMovieId(Long movieId);

    List<Showtime> findByTheaterId(Long theaterId);


    @Query("""
        SELECT s FROM showtime s
        WHERE s.theater.id = :theaterId
        AND (
            (s.startTime BETWEEN :startTime AND :endTime)
            OR 
            (s.endTime BETWEEN :startTime AND :endTime)
        )
    """)
    List<Showtime> findCollidingShowtimes(
            @Param("theaterId") Long theaterId,
            @Param("startTime") OffsetDateTime startTime,
            @Param("endTime") OffsetDateTime endTime
    );

}
