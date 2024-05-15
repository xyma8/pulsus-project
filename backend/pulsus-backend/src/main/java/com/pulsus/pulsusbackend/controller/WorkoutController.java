package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.FITFileDto;
import com.pulsus.pulsusbackend.dto.TypeSportDto;
import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/addNewWorkout")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> addNewWorkout(Authentication authentication, @RequestParam("file") MultipartFile file) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());
        WorkoutDto workoutDto = workoutService.createWorkout(file, userId);


        return ResponseEntity.ok(workoutDto);
    }

    @GetMapping("/{workoutId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> getInfoWorkout(Authentication authentication, @PathVariable Long workoutId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());
        WorkoutDto workoutDto = workoutService.getInfoWorkout(userId, workoutId);

        return ResponseEntity.ok(workoutDto);
    }

    @GetMapping("/{workoutId}/track")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<FITFileDto> getTrackWorkout(Authentication authentication, @PathVariable Long workoutId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());
        FITFileDto fitFileDto = workoutService.getTrackWorkout(userId, workoutId);

        return ResponseEntity.ok(fitFileDto);
    }

    @PostMapping("/{workoutId}/edit")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> editInfoWorkout(Authentication authentication, @PathVariable Long workoutId, @RequestBody WorkoutDto workoutData) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());
        WorkoutDto updatedWorkout = workoutService.editInfoWorkout(userId, workoutId, workoutData);

        return ResponseEntity.ok(updatedWorkout);//можно возвращать просто OK
    }

    @PostMapping("/{workoutId}/uploadPhotos")
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

    @GetMapping("/workouts/typesSport")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<TypeSportDto>> getTypesSport() {
        List<TypeSportDto> typeSportDtoList = workoutService.getTypesSport();

        return ResponseEntity.ok(typeSportDtoList);
    }
}
