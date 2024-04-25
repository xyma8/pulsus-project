package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.AuthTokenDto;
import com.pulsus.pulsusbackend.dto.FileOnServerDto;
import com.pulsus.pulsusbackend.dto.GPXInfoDto;
import com.pulsus.pulsusbackend.dto.UserDto;
import com.pulsus.pulsusbackend.entity.AuthRequest;
import com.pulsus.pulsusbackend.exception.ConflictException;
import com.pulsus.pulsusbackend.service.FileOnServerService;
import com.pulsus.pulsusbackend.service.JwtService;
import com.pulsus.pulsusbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final FileOnServerService fileOnServerService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // @GetMapping("/data")
   // public ResponseEntity<UserDataDto> getUserData(@RequestBody AuthTokenDto authTokenDto) {
    //
   // }

    @GetMapping("/profilePicture")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<FileOnServerDto> getProfilePicture(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        FileOnServerDto fileOnServerDto = userService.getProfilePicture(userId);
        return ResponseEntity.ok(fileOnServerDto);
    }

    @PostMapping("/uploadProfilePicture")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<FileOnServerDto> uploadProfilePicture(Authentication authentication, @RequestParam("file") MultipartFile file) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        FileOnServerDto fileOnServerDto = userService.uploadProfilePicture(file, userDetails.getUsername());

        System.out.println("file size " + file.getSize());

        return ResponseEntity.ok(fileOnServerDto);
    }

    @PostMapping("/uploadGPXFile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<GPXInfoDto> uploadGPXFile(Authentication authentication, @RequestParam("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        GPXInfoDto gpxInfoDto = fileOnServerService.ReadGPX(file);
        return null;
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return "Welcome to User Profile " + userDetails.getUsername();
    }

}
