package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.WorkoutDto;
import com.pulsus.pulsusbackend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/addWorkout")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> addWorkout(Authentication authentication, @RequestParam("file") MultipartFile file) {
        System.out.println("aaa");
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //Long userId = Long.parseLong(userDetails.getUsername());
        //WorkoutDto workoutDto = workoutService.createWorkout(file, userId);


        //return ResponseEntity.ok(workoutDto);
        return null;
    }
}
