package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.*;
import com.pulsus.pulsusbackend.entity.AuthRequest;
import com.pulsus.pulsusbackend.exception.ConflictException;
import com.pulsus.pulsusbackend.service.FileOnServerService;
import com.pulsus.pulsusbackend.service.JwtService;
import com.pulsus.pulsusbackend.service.UserService;
import com.pulsus.pulsusbackend.service.WorkoutService;
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

    private final FileOnServerService fileOnServerService;

    private final WorkoutService workoutService; //del

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
    public ResponseEntity<GPXFileDto> uploadGPXFile(Authentication authentication, @RequestParam("file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        GPXFileDto gpxInfoDto = fileOnServerService.readGPX(file);

        return null;
    }

    @PostMapping("/uploadFITFile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<FITFileDto> uploadFITFile(Authentication authentication, @RequestParam("file") MultipartFile file) {
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //FITFileDto fitFileDto = fileOnServerService.readFIT(file);

        //return ResponseEntity.ok(fitFileDto);
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //Long userId = Long.parseLong(userDetails.getUsername());
        //WorkoutDto workoutDto = workoutService.createWorkout(file, userId);
        return null;
    }

    @PostMapping("/addNewWorkout")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> addNewWorkout(Authentication authentication, @RequestParam("file") MultipartFile file) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());
        WorkoutDto workoutDto = workoutService.createWorkout(file, userId);


        return ResponseEntity.ok(workoutDto);
    }

    @GetMapping("/workouts/{workoutId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> getInfoWorkout(Authentication authentication, @PathVariable Long workoutId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());
        WorkoutDto workoutDto = workoutService.getInfoWorkout(userId, workoutId);

        return ResponseEntity.ok(workoutDto);
    }

    @GetMapping("/workouts/{workoutId}/track")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<FITFileDto> getTrackWorkoutController(Authentication authentication, @PathVariable Long workoutId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());
        FITFileDto fitFileDto = workoutService.getTrackWorkout(userId, workoutId);

        return ResponseEntity.ok(fitFileDto);
    }

    @PostMapping("/workouts/{workoutId}/edit")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> editInfoWorkout(Authentication authentication, @PathVariable Long workoutId, @RequestBody WorkoutDto workoutData) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());
        WorkoutDto updatedWorkout = workoutService.editInfoWorkout(userId, workoutId, workoutData);

        /*
        System.out.println(files.length);
        for (MultipartFile file : files) {
            // Делаем что-то с каждым файлом
            System.out.println(file.getSize());
        }
*/
        return ResponseEntity.ok(updatedWorkout);//можно возвращать просто OK
    }

    @PostMapping("workouts/{workoutId}/uploadPhotos")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> uploadWorkoutPhotos(Authentication authentication, @PathVariable Long workoutId, @RequestParam("images") MultipartFile[] files) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());


        System.out.println(files.length);
        for (MultipartFile file : files) {
            // Делаем что-то с каждым файлом
            System.out.println(file.getSize());
        }

        return ResponseEntity.ok("uploads");//можно возвращать просто OK
    }

    @GetMapping("/workouts/{workoutId}/track")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> getTrackWorkout(Authentication authentication, @PathVariable Long workoutId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());
        //WorkoutDto workoutDto = workoutService.getTrackWorkout(userId, workoutId);

        return null;
    }

    @GetMapping("/workouts/typesSport")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<TypeSportDto>> getTypesSport() {
        List<TypeSportDto> typeSportDtoList = workoutService.getTypesSport();

        return ResponseEntity.ok(typeSportDtoList);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return "Welcome to User Profile " + userDetails.getUsername();
    }

}
