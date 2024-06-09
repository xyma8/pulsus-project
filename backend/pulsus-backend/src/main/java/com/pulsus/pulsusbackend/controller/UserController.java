package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.*;
import com.pulsus.pulsusbackend.entity.AuthRequest;
import com.pulsus.pulsusbackend.exception.ConflictException;
import com.pulsus.pulsusbackend.service.*;
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

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final UserProfileService userProfileService;

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
        FileOnServerDto fileOnServerDto = userService.getProfilePicture(getUserId(authentication));
        return ResponseEntity.ok(fileOnServerDto);
    }

    @GetMapping("/profilePicture/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<FileOnServerDto> getProfilePictureById(Authentication authentication, @PathVariable Long userId) {
        FileOnServerDto fileOnServerDto = userService.getProfilePicture(userId);
        return ResponseEntity.ok(fileOnServerDto);
    }

    @PostMapping("/uploadProfilePicture")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<FileOnServerDto> uploadProfilePicture(Authentication authentication, @RequestParam("file") MultipartFile file) {
        FileOnServerDto fileOnServerDto = userService.uploadProfilePicture(getUserId(authentication), file);

        System.out.println("file size " + file.getSize());

        return ResponseEntity.ok(fileOnServerDto);
    }

    @GetMapping("/profile/byLogin/{login}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserInfoDto> getUserInfoByLogin(Authentication authentication, @PathVariable String login) {
        UserInfoDto userInfoDto = userService.getUserInfoByLogin(login);

        return ResponseEntity.ok(userInfoDto);
    }

    @GetMapping("/profile/byId/{userId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserInfoDto> getUserInfoByUserId(Authentication authentication, @PathVariable Long userId) {
        UserInfoDto userInfoDto = userService.getUserInfoById(userId);

        return ResponseEntity.ok(userInfoDto);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserInfoDto> getUserInfo(Authentication authentication) {
        UserInfoDto userInfoDto = userService.getUserInfoById(getUserId(authentication));

        return ResponseEntity.ok(userInfoDto);
    }

    @GetMapping("/profileCard")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<UserCardInfoDto> getUserCardInfo(Authentication authentication) {
        UserCardInfoDto userCardInfoDto = userProfileService.getUserCardInfo(getUserId(authentication));

        return ResponseEntity.ok(userCardInfoDto);
    }

    private Long getUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());

        return userId;
    }

}
