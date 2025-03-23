package com.att.tdp.popcorn_palace.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank(message = "Name field cannot be empty or null")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    final String name;

    public UserDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
