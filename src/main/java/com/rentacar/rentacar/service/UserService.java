package com.rentacar.rentacar.service;

import com.rentacar.rentacar.dto.AuthDto;
import com.rentacar.rentacar.dto.LoginDto;
import com.rentacar.rentacar.dto.UserDto;
import com.rentacar.rentacar.enums.RoleEnum;
import com.rentacar.rentacar.model.User;
import com.rentacar.rentacar.repository.UserRepository;
import com.rentacar.rentacar.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service

public class UserService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public UserDto createUser(User user){
        if (Objects.isNull(user.getRoles())){
            user.setRoles(RoleEnum.ROLE_USER.toString());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.INSTANCE.userToUserDto(userRepository.save(user));
    }

    public LoginDto login(AuthDto authDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(authentication);
        }
        throw new UsernameNotFoundException("invalid user.");
    }
}