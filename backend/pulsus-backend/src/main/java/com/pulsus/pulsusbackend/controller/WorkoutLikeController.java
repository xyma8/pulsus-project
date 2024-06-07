package com.pulsus.pulsusbackend.controller;


import com.pulsus.pulsusbackend.dto.UserInfoDto;
import com.pulsus.pulsusbackend.dto.WorkoutLikeDto;
import com.pulsus.pulsusbackend.service.SubscriptionService;
import com.pulsus.pulsusbackend.service.WorkoutLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/workoutsLikes")
public class WorkoutLikeController {

    private final WorkoutLikeService workoutLikeService;

    @GetMapping("/{workoutId}/check")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutLikeDto> checkSubscription(Authentication authentication, @PathVariable Long workoutId) {
        WorkoutLikeDto checkLike = workoutLikeService.checkLike(getUserId(authentication), workoutId);

        return ResponseEntity.ok(checkLike);
    }

    @PostMapping("/{workoutId}/change")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<WorkoutLikeDto> changeSubscription(Authentication authentication, @PathVariable Long workoutId) {
        WorkoutLikeDto changeLike = workoutLikeService.changeLike(getUserId(authentication), workoutId);

        return ResponseEntity.ok(changeLike);
    }

    @GetMapping("/{workoutId}/likes/count")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Map<String, Long>> getFollowersByUserId(Authentication authentication, @PathVariable Long workoutId) {
        Long likesCount = workoutLikeService.getLikesCount(getUserId(authentication), workoutId);

        return ResponseEntity.ok(Map.of("likesCount", likesCount));
    }


    private Long getUserId(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = Long.parseLong(userDetails.getUsername());

        return userId;
    }
}
