package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.FileOnServerDto;
import com.pulsus.pulsusbackend.dto.UserDto;
import com.pulsus.pulsusbackend.entity.FilesOnServer;
import com.pulsus.pulsusbackend.entity.Role;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.exception.ConflictException;
import com.pulsus.pulsusbackend.exception.InternalServerException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.mapper.UserMapper;
import com.pulsus.pulsusbackend.repository.UserRepository;
import com.pulsus.pulsusbackend.service.FIleService;
import com.pulsus.pulsusbackend.service.UserService;
import com.pulsus.pulsusbackend.util.FilePaths;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FIleService fileService;

    private FilePaths filePaths;


    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Optional<User> findById(String userId) { return userRepository.findById(Long.parseLong(userId)); }

    @Override
    public Long getUserIdByLogin(String login) {
        User user = findByLogin(login)
                .orElseThrow(() -> new UnauthorizedException(String.format("User with login '%s' don't exists", login)));

        return user.getId();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        String login = userDto.getLogin();
        Optional<User> optionalUser = findByLogin(login);
        optionalUser.ifPresent(user -> {
            throw new ConflictException(String.format("Login '%s' is already taken", login));
        });

        User user = UserMapper.mapToUser(userDto);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        Role userRole = new Role();
        userRole.setId(1);
        user.getRoles().add(userRole);
        User savedUser = userRepository.save(user);
        fileService.CreateUserDirs(savedUser.getId());
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public FileOnServerDto getProfilePicture(String userId) {
        User user = findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error"));
        //проверка не нужна?

        String path = fileService.profilePic(Long.parseLong(userId));

        return new FileOnServerDto(path);
    }

    @Override
    public FileOnServerDto uploadProfilePicture(MultipartFile file, String userId) {
        try {
            fileService.AddUserProfilePic(Long.parseLong(userId), file);
        } catch (Exception e) {
            throw new InternalServerException("Internal server exception");
        }

        FileOnServerDto fileOnServerDto = getProfilePicture(userId);
        return fileOnServerDto;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findById(username)
                .orElseThrow(() -> new UnauthorizedException("Login error"));

         //User user = findByLogin(username)
         //       .orElseThrow(() -> new UnauthorizedException(String.format("User with login '%s' don't exists", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getId().toString(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );

    }
}
