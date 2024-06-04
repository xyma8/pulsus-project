package com.pulsus.pulsusbackend.mapper;

import com.pulsus.pulsusbackend.dto.UserInfoDto;
import com.pulsus.pulsusbackend.entity.User;

public class UserInfoDtoMapper {
    public static UserInfoDto mapToUserInfoDto(User user) {
        return new UserInfoDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getLogin()
        );
    }
}
