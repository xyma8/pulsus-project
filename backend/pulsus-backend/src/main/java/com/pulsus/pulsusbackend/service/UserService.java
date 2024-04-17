package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.UserDto;
import com.pulsus.pulsusbackend.entity.FilesOnServer;
import com.pulsus.pulsusbackend.service.impl.UserServiceImpl;

public interface UserService {
    UserDto createUser(UserDto userDto);

    FilesOnServer getProfilePicture(String login);
}
