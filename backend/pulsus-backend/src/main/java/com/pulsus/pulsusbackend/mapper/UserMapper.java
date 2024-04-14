package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.UserDto;
import com.pulsus.pulsusbackend.entity.User;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getEmail(),
                userDto.getLogin(),
                userDto.getPassword()
        );
    }
}
