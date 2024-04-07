package com.pulsus.pulsusbackend.service.impl;

import com.pulsus.pulsusbackend.dto.UserDto;
import com.pulsus.pulsusbackend.entity.User;
import com.pulsus.pulsusbackend.exception.ConflictException;
import com.pulsus.pulsusbackend.mapper.UserMapper;
import com.pulsus.pulsusbackend.repository.UserRepository;
import com.pulsus.pulsusbackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        String login = userDto.getLogin();
        Optional<User> optionalUser = findByLogin(login);
        optionalUser.ifPresent(user -> {
            throw new ConflictException(String.format("Login '%s' is already taken", login));
        });

        User user = optionalUser.get();


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByLogin(username)
                .orElseThrow(() -> new ConflictException(String.format("User with login '%s' don't exists", username)));

        /**

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getRole().stre
        );

         **/
        
        return null;
    }
}
