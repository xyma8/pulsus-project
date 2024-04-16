package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.AuthTokenDto;
import com.pulsus.pulsusbackend.entity.AuthRequest;
import com.pulsus.pulsusbackend.exception.ConflictException;
import com.pulsus.pulsusbackend.exception.UnauthorizedException;
import com.pulsus.pulsusbackend.service.JwtService;
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

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/generateToken")
    public ResponseEntity<AuthTokenDto> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = null;
        //try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
            System.out.println(authentication);
        //} catch (AuthenticationException e) {
        //    System.out.println(e);
        //    throw new UnauthorizedException("Login or password incorrect");
        //} catch (BadCredentialsException e) {
        //
       // }

        if (authentication != null && authentication.isAuthenticated()) {
            AuthTokenDto authTokenDto = new AuthTokenDto(jwtService.generateToken(authRequest.getLogin()));
            return ResponseEntity.ok(authTokenDto);
        } else {
            throw new UnauthorizedException("Login or password incorrect");
        }
    }
}
