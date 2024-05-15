package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.FileOnServerDto;
import com.pulsus.pulsusbackend.dto.UserDto;
import com.pulsus.pulsusbackend.entity.Role;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.exception.BadRequestException;
import com.pulsus.pulsusbackend.exception.ConflictException;
import com.pulsus.pulsusbackend.exception.InternalServerException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.mapper.UserMapper;
import com.pulsus.pulsusbackend.repository.UserRepository;
import com.pulsus.pulsusbackend.service.FileService;
import com.pulsus.pulsusbackend.service.UserService;
import com.pulsus.pulsusbackend.util.FilePaths;
import com.pulsus.pulsusbackend.validator.UserValidator;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    private FilePaths filePaths;


    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> findById(Long userId) { return userRepository.findById(userId); }

    @Override
    public Long getUserIdByLogin(String login) {
        User user = findByLogin(login)
                .orElseThrow(() -> new UnauthorizedException(String.format("User with login '%s' don't exists", login)));

        return user.getId();
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<User> checkLogin = findByLogin(userDto.getLogin());
        checkLogin.ifPresent(user -> {
            throw new ConflictException("Login or Email is already taken");
        });

        Optional<User> checkEmail = userRepository.findByEmail(userDto.getEmail());
        checkEmail.ifPresent(user -> {
            throw new ConflictException("Login or Email is already taken");
        });

        if(!UserValidator.isValidName(userDto.getName())) {
            throw new BadRequestException("Invalid name field");
        }

        if(!UserValidator.isValidSurname(userDto.getSurname())) {
            throw new BadRequestException("Invalid surname field");
        }

        if(!UserValidator.isValidEmail(userDto.getEmail())) {
            throw new BadRequestException("Invalid email field");
        }

        if(!UserValidator.isValidLogin(userDto.getLogin())) {
            throw new BadRequestException("Invalid login field");
        }

        if(!UserValidator.isValidPassword(userDto.getPassword())) {
            throw new BadRequestException("Invalid password field");
        }

        User user = UserMapper.mapToUser(userDto);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        Role userRole = new Role();
        userRole.setId(1);
        user.getRoles().add(userRole);
        User savedUser = userRepository.save(user);
        fileService.createUserDirs(savedUser.getId());

        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public FileOnServerDto getProfilePicture(Long userId) {
        User user = findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Login error")); //проверка не нужна?

        String path = fileService.profilePic(userId);

        return new FileOnServerDto(path);
    }

    @Override
    public FileOnServerDto uploadProfilePicture(Long userId, MultipartFile file) {
        try {
            fileService.uploadUserProfilePic(userId, file);
        } catch (Exception e) {
            throw new InternalServerException("Internal server exception");
        }

        FileOnServerDto fileOnServerDto = getProfilePicture(userId);

        return fileOnServerDto;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findById(Long.parseLong(username))
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
