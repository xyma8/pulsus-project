package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.FileOnServerDto;
import com.pulsus.pulsusbackend.dto.UserDto;
import com.pulsus.pulsusbackend.dto.UserInfoDto;
import com.pulsus.pulsusbackend.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto);

    UserInfoDto getUserInfoByLogin(String login);

    UserInfoDto getUserInfoById(Long userId);

    FileOnServerDto getProfilePicture(Long userId);

    FileOnServerDto uploadProfilePicture(Long userId, MultipartFile file);

    Long getUserIdByLogin(String login);

    Optional<User> findById(Long userId);
}
