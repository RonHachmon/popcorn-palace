package com.att.tdp.popcorn_palace.controller;


import com.att.tdp.popcorn_palace.dto.UserDTO;
import com.att.tdp.popcorn_palace.entitys.Movie;
import com.att.tdp.popcorn_palace.entitys.Users;
import com.att.tdp.popcorn_palace.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    UsersController(UsersService usersService)
    {
        this.usersService = usersService;
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> createUser(@RequestBody @Valid UserDTO userDTO) {
        usersService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
