package com.pulsus.pulsusbackend.controller;

import com.pulsus.pulsusbackend.dto.*;
import com.pulsus.pulsusbackend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/addNewWorkout")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> addNewWorkout(Authentication authentication, @RequestParam("file") MultipartFile file) {
        WorkoutDto workoutDto = workoutService.createWorkout(getUserId(authentication), file);

        return ResponseEntity.ok(workoutDto);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Long> getCountWorkouts(Authentication authentication, @PathVariable Long workoutId) {
        Long countWorkouts = workoutService.getCountWorkouts(getUserId(authentication));

        return new ResponseEntity<>(countWorkouts, HttpStatus.OK);
    }

    @GetMapping("/{workoutId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> getInfoWorkout(Authentication authentication, @PathVariable Long workoutId) {
        WorkoutDto workoutDto = workoutService.getInfoWorkout(getUserId(authentication), workoutId);

        return ResponseEntity.ok(workoutDto);
    }

    @GetMapping("/{workoutId}/summary")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutSummaryDto> getSummaryWorkout(Authentication authentication, @PathVariable Long workoutId) {
        WorkoutSummaryDto workoutSummaryDto = workoutService.getSummaryWorkout(getUserId(authentication), workoutId);

        return ResponseEntity.ok(workoutSummaryDto);
    }

    @GetMapping("/{workoutId}/trackSummary")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<TrackSummaryDto> getTrackSummaryWorkout(Authentication authentication, @PathVariable Long workoutId) {
        TrackSummaryDto trackSummaryDto = workoutService.getTrackSummaryWorkout(getUserId(authentication), workoutId);

        return ResponseEntity.ok(trackSummaryDto);
    }

    @GetMapping("/{workoutId}/track")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<FITFileDto> getTrackWorkout(Authentication authentication, @PathVariable Long workoutId) {
        FITFileDto fitFileDto = workoutService.getTrackWorkout(getUserId(authentication), workoutId);

        return ResponseEntity.ok(fitFileDto);
    }

    @GetMapping("/{workoutId}/accessEditPage")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Map<String, Boolean>> getInfoWorkoutForEdit(Authentication authentication, @PathVariable Long workoutId) {

        Boolean access = workoutService.checkAccessEditPage(getUserId(authentication), workoutId);

        return ResponseEntity.ok(Map.of("access", access));
    }

    @PostMapping("/{workoutId}/edit")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutDto> editInfoWorkout(Authentication authentication, @PathVariable Long workoutId, @RequestBody WorkoutDto workoutData) {
        WorkoutDto updatedWorkout = workoutService.editInfoWorkout(getUserId(authentication), workoutId, workoutData);

        return ResponseEntity.ok(updatedWorkout);//можно возвращать просто OK
    }

    @PostMapping("/{workoutId}/uploadPhotos")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> uploadWorkoutPhotos(Authentication authentication, @PathVariable Long workoutId, @RequestParam("images") MultipartFile[] files) {
        System.out.println(files.length);
        for (MultipartFile file : files) {
            // Делаем что-то с каждым файлом
            System.out.println(file.getSize());
        }

        return ResponseEntity.ok("uploads");//можно возвращать просто OK
    }

    @GetMapping("/typesSport")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<TypeSportDto>> getTypesSport(Authentication authentication) {
        List<TypeSportDto> typeSportDtoList = workoutService.getTypesSport();

        return ResponseEntity.ok(typeSportDtoList);
    }

    private Long getUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());

        return userId;
    }
}
