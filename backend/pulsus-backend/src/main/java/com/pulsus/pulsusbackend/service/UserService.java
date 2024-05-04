package com.pulsus.pulsusbackend.service;

import com.pulsus.pulsusbackend.dto.FileOnServerDto;
import com.pulsus.pulsusbackend.dto.UserDto;
import com.pulsus.pulsusbackend.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto);

    FileOnServerDto getProfilePicture(String userId);

    FileOnServerDto uploadProfilePicture(MultipartFile file, String userId);

    Long getUserIdByLogin(String login);

    Optional<User> findById(Long userId);
}
