package com.pulsus.pulsusbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/work")
public class WorkController {

    @GetMapping("/gg")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Integer> getWorkout(Authentication authentication) {
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //Long userId = Long.parseLong(userDetails.getUsername());
        //WorkoutDto workoutDto = workoutService.createWorkout(file, userId);

        System.out.println("getWorkout");
        //return ResponseEntity.ok(workoutDto);
        return ResponseEntity.ok(1);
    }
}
