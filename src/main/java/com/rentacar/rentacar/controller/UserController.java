package com.rentacar.rentacar.controller;

import com.rentacar.rentacar.dto.AuthDto;
import com.rentacar.rentacar.dto.LoginDto;
import com.rentacar.rentacar.dto.UserDto;
import com.rentacar.rentacar.model.User;
import com.rentacar.rentacar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody User user){
        return  new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody AuthDto authDto){
        return new ResponseEntity<>(userService.login(authDto), HttpStatus.OK);
    }
}