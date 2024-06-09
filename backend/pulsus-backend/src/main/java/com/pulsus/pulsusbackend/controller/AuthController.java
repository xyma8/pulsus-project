package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.AuthTokenDto;
import com.pulsus.pulsusbackend.entity.AuthRequest;
import com.pulsus.pulsusbackend.exception.ConflictException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.service.JwtService;
import com.pulsus.pulsusbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private final UserService userService;

    @PostMapping("/generateToken")
    public ResponseEntity<AuthTokenDto> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

        Long userId = userService.getUserIdByLogin(authRequest.getLogin());
        //try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId.toString(), authRequest.getPassword()));
        //System.out.println("authentication");
        //} catch (Exception e) {
        //    System.out.println(e);
        //}


        if (authentication != null && authentication.isAuthenticated()) {
            userId = userService.getUserIdByLogin(authRequest.getLogin());
            AuthTokenDto authTokenDto = new AuthTokenDto(jwtService.generateToken(userId.toString()));
            return ResponseEntity.ok(authTokenDto);
        } else {
            throw new UnauthorizedException("Login or password incorrect");
        }

    }

    @GetMapping("/checkToken")
    public ResponseEntity<Map<String, Boolean>> checkToken(Authentication authentication) {

        return ResponseEntity.ok(Map.of("validToken", true));
    }
}
