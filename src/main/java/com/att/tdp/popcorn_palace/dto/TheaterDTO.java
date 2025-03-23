package com.att.tdp.popcorn_palace.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TheaterDTO {
    @NotBlank(message = "Name field cannot be empty or null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    final String name;

    final Integer totalSits;
    public TheaterDTO(String name, Integer totalSits) {
        this.name = name;

        this.totalSits = totalSits;
    }

    public String getName() {
        return name;
    }


    public Integer getTotalSits() {
        return totalSits;
    }
}
