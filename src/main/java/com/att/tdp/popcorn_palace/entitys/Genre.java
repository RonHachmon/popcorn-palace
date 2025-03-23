package com.att.tdp.popcorn_palace.entitys;

import com.att.tdp.popcorn_palace.exception.GenreNotFoundException;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Genre {
    HORROR,
    ACTION,
    DRAMA,
    COMEDY,
    THRILLER,
    SCI_FI,
    ROMANCE;

    public static Genre findByString(String genreStr)  {
        String formattedString = genreStr.toUpperCase().replace(" ", "_");

        try {
            return Genre.valueOf(formattedString);
        } catch (IllegalArgumentException e) {
            throw new GenreNotFoundException("Genre not found. Available genres: " + getAvailableGenres());
        }
    }

    private static String getAvailableGenres() {
        return Arrays.stream(Genre.values())
                .map(genre -> genre.name().toLowerCase().replace("_", " "))
                .collect(Collectors.joining(", "));
    }


}
