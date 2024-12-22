package com.rentacar.rentacar.utils;
import com.rentacar.rentacar.dto.UserDto;
import com.rentacar.rentacar.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper

public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto userToUserDto(User user);
}