package com.att.tdp.popcorn_palace.service;


import com.att.tdp.popcorn_palace.dto.UserDTO;
import com.att.tdp.popcorn_palace.entitys.Users;
import com.att.tdp.popcorn_palace.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository userRepository;

    @Autowired
    public UsersService(UsersRepository userRepository){
        this.userRepository = userRepository;
    }

    public Users createUser(UserDTO userDTO) {
        Users newUsers = new Users(userDTO.getName());
        return userRepository.save(newUsers);
    }

    public List<Users> getAllUsers(){

        return userRepository.findAll();
    }
}
